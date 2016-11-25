package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.function.BiPredicate;

import com.github.moreaunicolas.test.UtilityClassAssert;
import org.junit.Test;

public class UncheckedBiPredicateTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void biPredicateWrapperShouldTestWrappedBiPredicate() {
        BiPredicate<String, String> wrapper = UncheckedBiPredicate.from((a, b) -> Objects.equals(a.length(), b.length()));

        boolean actual = wrapper.test("HELLO", "WORLD");

        assertThat(actual).isTrue();
    }

    @Test
    public void biPredicateWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        BiPredicate<Object, Object> wrapper = UncheckedBiPredicate.from((a, b) -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.test(null, null));

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void biPredicateWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        BiPredicate<Object, Object> wrapper = UncheckedBiPredicate.from((a, b) -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.test(null, null));

        assertThat(throwable)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(ioException);
    }

    @Test
    public void biPredicateWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        BiPredicate<Object, Object> wrapper = UncheckedBiPredicate.from((a, b) -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(() -> wrapper.test(null, null));

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(UncheckedBiPredicate.class)
                .isUtilityClass();
    }
}

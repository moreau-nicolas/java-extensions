package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;

import org.junit.Test;

public class UncheckedPredicateTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void predicateWrapperShouldTestWrappedPredicate() {
        Predicate<String> wrapper = UncheckedPredicate.from(String::isEmpty);

        boolean actual = wrapper.test("HELLO");

        assertThat(actual).isFalse();
    }

    @Test
    public void predicateWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        Predicate<Object> wrapper = UncheckedPredicate.from(any -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.test(null));

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void predicateWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        Predicate<Object> wrapper = UncheckedPredicate.from(any -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.test(null));

        assertThat(throwable)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(ioException);
    }

    @Test
    public void predicateWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        Predicate<Object> wrapper = UncheckedPredicate.from(any -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(() -> wrapper.test(null));

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }
}

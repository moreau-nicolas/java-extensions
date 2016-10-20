package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;

import org.junit.Test;

public class UncheckedBiFunctionTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void biFunctionWrapperShouldApplyWrappedBiFunction() {
        BiFunction<String, String, String> wrapper = UncheckedBiFunction.from((a, b) -> a + b);

        String actual = wrapper.apply("4", "2");

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void biFunctionWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        BiFunction<Object, Object, Object> wrapper = UncheckedBiFunction.from((a, b) -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.apply(null, null));

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void biFunctionWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        BiFunction<Object, Object, Object> wrapper = UncheckedBiFunction.from((a, b) -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.apply(null, null));

        assertThat(throwable).isInstanceOf(UncheckedIOException.class).hasCause(ioException);
    }

    @Test
    public void biFunctionWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        BiFunction<Object, Object, Object> wrapper = UncheckedBiFunction.from((a, b) -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(() -> wrapper.apply(null, null));

        assertThat(throwable).isInstanceOf(RuntimeException.class).hasCause(exception);
    }
}

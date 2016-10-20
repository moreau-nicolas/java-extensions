package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

import org.junit.Test;

public class UncheckedFunctionTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void functionWrapperShouldApplyWrappedFunction() {
        Function<String, String> wrapper = UncheckedFunction.from(x -> x + " WORLD!");

        String actual = wrapper.apply("HELLO");

        assertThat(actual).isEqualTo("HELLO WORLD!");
    }

    @Test
    public void functionWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        Function<Object, Object> wrapper = UncheckedFunction.from(any -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.apply(null));

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void functionWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        Function<Object, Object> wrapper = UncheckedFunction.from(any -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.apply(null));

        assertThat(throwable)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(ioException);
    }

    @Test
    public void functionWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        Function<Object, Object> wrapper = UncheckedFunction.from(any -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(() -> wrapper.apply(null));

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }
}

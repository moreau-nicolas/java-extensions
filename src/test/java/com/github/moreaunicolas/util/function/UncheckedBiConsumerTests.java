package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.junit.Test;

public class UncheckedBiConsumerTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void biConsumerWrapperShouldDelegateToWrappedBiConsumer() {
        List<String> arguments = new ArrayList<>();
        BiConsumer<String, String> wrapper = UncheckedBiConsumer.from((a, b) -> {
            arguments.add(a);
            arguments.add(b);
        });

        wrapper.accept("4", "2");

        assertThat(arguments).as("BiConsumer arguments").containsExactly("4", "2");
    }

    @Test
    public void biConsumerWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        BiConsumer<Object, Object> wrapper = UncheckedBiConsumer.from((a, b) -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.accept(null, null));

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void biConsumerWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        BiConsumer<Object, Object> wrapper = UncheckedBiConsumer.from((a, b) -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.accept(null, null));

        assertThat(throwable)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(ioException);
    }

    @Test
    public void biConsumerWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        BiConsumer<Object, Object> wrapper = UncheckedBiConsumer.from((a, b) -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(() -> wrapper.accept(null, null));

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }
}

package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.github.moreaunicolas.test.UtilityClassAssert;
import org.junit.Test;

public class UncheckedConsumerTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void consumerWrapperShouldDelegateToWrappedConsumer() {
        List<Object> arguments = new ArrayList<>();
        Consumer<String> wrapper = UncheckedConsumer.from(arguments::add);

        wrapper.accept("HELLO");

        assertThat(arguments).as("Consumer arguments")
                .containsExactly("HELLO");
    }

    @Test
    public void consumerWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        Consumer<Object> wrapper = UncheckedConsumer.from(any -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.accept(null));

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void consumerWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        Consumer<Object> wrapper = UncheckedConsumer.from(any -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(() -> wrapper.accept(null));

        assertThat(throwable).isInstanceOf(UncheckedIOException.class).hasCause(ioException);
    }

    @Test
    public void consumerWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        Consumer<Object> wrapper = UncheckedConsumer.from(any -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(() -> wrapper.accept(null));

        assertThat(throwable).isInstanceOf(RuntimeException.class).hasCause(exception);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(UncheckedConsumer.class)
                .isUtilityClass();
    }
}

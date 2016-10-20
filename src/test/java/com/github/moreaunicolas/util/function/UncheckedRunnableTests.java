package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class UncheckedRunnableTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void runnableWrapperShouldRunWrappedRunnable() {
        AtomicBoolean called = new AtomicBoolean(false);
        Runnable wrapper = UncheckedRunnable.from(() -> called.set(true));

        wrapper.run();

        assertThat(called.get())
                .withFailMessage("Runnable should have been called")
                .isTrue();
    }

    @Test
    public void runnableWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        Runnable wrapper = UncheckedRunnable.from(() -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(wrapper::run);

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void runnableWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        Runnable wrapper = UncheckedRunnable.from(() -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(wrapper::run);

        assertThat(throwable)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(ioException);
    }

    @Test
    public void runnableWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        Runnable wrapper = UncheckedRunnable.from(() -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(wrapper::run);

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }
}

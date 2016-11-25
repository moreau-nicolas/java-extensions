package com.github.moreaunicolas.util.function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

import com.github.moreaunicolas.test.UtilityClassAssert;
import org.junit.Test;

public class UncheckedSupplierTests {

    private static final String MESSAGE = "OOPS!";

    @Test
    public void supplierWrapperShouldDelegateToWrappedSupplier() {
        Supplier<String> wrapper = UncheckedSupplier.from(() -> "42");

        String actual = wrapper.get();

        assertThat(actual).isEqualTo("42");
    }

    @Test
    public void supplierWrapperShouldRethrowRuntimeExceptions() {
        RuntimeException runtimeException = new RuntimeException(MESSAGE);
        Supplier<Object> wrapper = UncheckedSupplier.from(() -> {
            throw runtimeException;
        });

        Throwable throwable = catchThrowable(wrapper::get);

        assertThat(throwable).isSameAs(runtimeException);
    }

    @Test
    public void supplierWrapperShouldWrapIOExceptions() {
        IOException ioException = new IOException(MESSAGE);
        Supplier<Object> wrapper = UncheckedSupplier.from(() -> {
            throw ioException;
        });

        Throwable throwable = catchThrowable(wrapper::get);

        assertThat(throwable)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(ioException);
    }

    @Test
    public void supplierWrapperShouldWrapCheckedExceptions() {
        Exception exception = new Exception(MESSAGE);
        Supplier<Object> wrapper = UncheckedSupplier.from(() -> {
            throw exception;
        });

        Throwable throwable = catchThrowable(wrapper::get);

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasCause(exception);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(UncheckedSupplier.class)
                .isUtilityClass();
    }
}

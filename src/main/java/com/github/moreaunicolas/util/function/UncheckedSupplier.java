package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

public final class UncheckedSupplier {

    @FunctionalInterface
    interface CheckedSupplier<T> {
        T get() throws Exception;
    }

    public static <T> Supplier<T> from(CheckedSupplier<T> supplier) {
        return () -> {
            try {
                return supplier.get();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private UncheckedSupplier() {
        throw new UnsupportedOperationException();
    }
}

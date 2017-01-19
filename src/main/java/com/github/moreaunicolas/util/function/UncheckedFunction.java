package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Function;

public final class UncheckedFunction {

    public static <T, R> Function<T, R> from(CheckedFunction<T, R> function) {
        return parameter -> {
            try {
                return function.apply(parameter);
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

    private UncheckedFunction() {
        throw new UnsupportedOperationException();
    }
}

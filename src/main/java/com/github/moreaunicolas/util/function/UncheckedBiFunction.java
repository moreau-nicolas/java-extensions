package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;

public final class UncheckedBiFunction {

    public static <T, U, R> BiFunction<T, U, R> from(CheckedBiFunction<T, U, R> biFunction) {
        return (first, second) -> {
            try {
                return biFunction.apply(first, second);
            } catch (RuntimeException e) {
                throw e;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private UncheckedBiFunction() {
        throw new UnsupportedOperationException();
    }
}

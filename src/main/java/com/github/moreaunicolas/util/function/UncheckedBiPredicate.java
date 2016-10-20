package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiPredicate;

public final class UncheckedBiPredicate {

    @FunctionalInterface
    interface CheckedBiPredicate<T, U> {
        boolean test(T first, U second) throws Exception;
    }

    public static <T, U> BiPredicate<T, U> from(CheckedBiPredicate<T, U> biPredicate) {
        return (first, second) -> {
            try {
                return biPredicate.test(first, second);
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

    private UncheckedBiPredicate() {
        throw new UnsupportedOperationException();
    }
}

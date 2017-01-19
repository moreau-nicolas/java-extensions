package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Predicate;

public final class UncheckedPredicate {

    public static <T> Predicate<T> from(CheckedPredicate<T> predicate) {
        return parameter -> {
            try {
                return predicate.test(parameter);
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

    private UncheckedPredicate() {
        throw new UnsupportedOperationException();
    }
}

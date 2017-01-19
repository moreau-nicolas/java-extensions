package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.BiConsumer;

public final class UncheckedBiConsumer {

    public static <T, U> BiConsumer<T, U> from(CheckedBiConsumer<T, U> biConsumer) {
        return (first, second) -> {
            try {
                biConsumer.accept(first, second);
            } catch (RuntimeException e) {
                throw e;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private UncheckedBiConsumer() {
        throw new UnsupportedOperationException();
    }
}

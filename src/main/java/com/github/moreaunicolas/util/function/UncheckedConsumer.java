package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.function.Consumer;

public final class UncheckedConsumer {

    public static <T> Consumer<T> from(CheckedConsumer<T> consumer) {
        return parameter -> {
            try {
                consumer.accept(parameter);
            } catch (RuntimeException e) {
                throw e;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private UncheckedConsumer() {
        throw new UnsupportedOperationException();
    }
}

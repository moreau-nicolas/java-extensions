package com.github.moreaunicolas.util.function;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class UncheckedRunnable {

    public static Runnable from(CheckedRunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (RuntimeException e) {
                throw e;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private UncheckedRunnable() {
        throw new UnsupportedOperationException();
    }
}

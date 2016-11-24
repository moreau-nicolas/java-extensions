package com.github.moreaunicolas.util.stream;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public final class IntCounters {

    @FunctionalInterface
    public interface IntCountFunction {
        long in(IntStream ints);

        default long in(int[] ints) {
            return in(IntStream.of(ints));
        }
    }

    public static IntCountFunction count(IntPredicate predicate) {
        return stream -> stream.filter(predicate).count();
    }

    private IntCounters() {
        throw new UnsupportedOperationException();
    }
}

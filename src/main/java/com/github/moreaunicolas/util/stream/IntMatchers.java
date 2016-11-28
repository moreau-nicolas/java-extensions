package com.github.moreaunicolas.util.stream;


import java.util.PrimitiveIterator;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.intStream;

public final class IntMatchers {

    @FunctionalInterface public interface IntMatchFunction {
        boolean in(IntStream ints);

        default boolean in(int[] ints) {
            return in(IntStream.of(ints));
        }

        default boolean in(PrimitiveIterator.OfInt iterator) {
            return in(intStream(iterator));
        }
    }

    public static IntMatchFunction allMatch(IntPredicate predicate) {
        return stream -> stream.allMatch(predicate);
    }

    public static IntMatchFunction anyMatch(IntPredicate predicate) {
        return stream -> stream.anyMatch(predicate);
    }

    public static IntMatchFunction noneMatch(IntPredicate predicate) {
        return stream -> stream.noneMatch(predicate);
    }

    private IntMatchers() {
        throw new UnsupportedOperationException();
    }
}

package com.github.moreaunicolas.util.stream;


import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.intStream;

public final class IntFilters {

    @FunctionalInterface
    public interface IntSearchFunction<R> {
        R in(IntStream ints);

        default R in(int[] ints) {
            return in(IntStream.of(ints));
        }

        default R in(PrimitiveIterator.OfInt iterator) {
            return in(intStream(iterator));
        }
    }

    public static IntSearchFunction<IntStream> findAll(IntPredicate predicate) {
        return stream -> stream.filter(predicate);
    }

    public static IntSearchFunction<OptionalInt> findFirst(IntPredicate predicate) {
        return stream -> stream.filter(predicate).findFirst();
    }

    public static IntSearchFunction<OptionalInt> findAny(IntPredicate predicate) {
        return stream -> stream.filter(predicate).findAny();
    }

    public static IntSearchFunction<OptionalInt> minInt() {
        return IntStream::min;
    }

    public static IntSearchFunction<OptionalInt> maxInt() {
        return IntStream::max;
    }

    private IntFilters() {
        throw new UnsupportedOperationException();
    }
}

package com.github.moreaunicolas.util.stream;

import java.util.PrimitiveIterator;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.longStream;

public final class LongCounters {

    @FunctionalInterface
    public interface LongCountFunction {
        long in(LongStream longs);

        default long in(long[] longs) {
            return in(LongStream.of(longs));
        }

        default long in(PrimitiveIterator.OfLong iterator) {
            return in(longStream(iterator));
        }
    }

    public static LongCountFunction count(LongPredicate predicate) {
        return stream -> stream.filter(predicate).count();
    }

    private LongCounters() {
        throw new UnsupportedOperationException();
    }
}

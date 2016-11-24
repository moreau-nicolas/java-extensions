package com.github.moreaunicolas.util.stream;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public final class LongCounters {

    @FunctionalInterface
    public interface LongCountFunction {
        long in(LongStream longs);

        default long in(long[] longs) {
            return in(LongStream.of(longs));
        }
    }

    public static LongCountFunction count(LongPredicate predicate) {
        return stream -> stream.filter(predicate).count();
    }

    private LongCounters() {
        throw new UnsupportedOperationException();
    }
}

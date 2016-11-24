package com.github.moreaunicolas.util.stream;

import java.util.OptionalLong;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public final class LongFilters {

    @FunctionalInterface
    public interface LongSearchFunction<R> {
        R in(LongStream longs);

        default R in(long[] longs) {
            return in(LongStream.of(longs));
        }
    }

    public static LongSearchFunction<LongStream> findAll(LongPredicate predicate) {
        return stream -> stream.filter(predicate);
    }

    public static LongSearchFunction<OptionalLong> findFirst(LongPredicate predicate) {
        return stream -> stream.filter(predicate).findFirst();
    }

    public static LongSearchFunction<OptionalLong> findAny(LongPredicate predicate) {
        return stream -> stream.filter(predicate).findAny();
    }

    public static LongSearchFunction<OptionalLong> minLong() {
        return LongStream::min;
    }

    public static LongSearchFunction<OptionalLong> maxLong() {
        return LongStream::max;
    }

    private LongFilters() {
        throw new UnsupportedOperationException();
    }
}

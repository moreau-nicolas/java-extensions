package com.github.moreaunicolas.util.stream;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public final class LongMatchers {

    @FunctionalInterface
    public interface LongMatchFunction {
        boolean in(LongStream longs);

        default boolean in(long[] longs) {
            return in(LongStream.of(longs));
        }
    }

    public static LongMatchFunction allMatch(LongPredicate predicate) {
        return stream -> stream.allMatch(predicate);
    }

    public static LongMatchFunction anyMatch(LongPredicate predicate) {
        return stream -> stream.anyMatch(predicate);
    }

    public static LongMatchFunction noneMatch(LongPredicate predicate) {
        return stream -> stream.noneMatch(predicate);
    }

    private LongMatchers() {
        throw new UnsupportedOperationException();
    }
}
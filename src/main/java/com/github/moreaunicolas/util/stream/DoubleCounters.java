package com.github.moreaunicolas.util.stream;

import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

public final class DoubleCounters {

    @FunctionalInterface
    public interface DoubleCountFunction {
        long in(DoubleStream doubles);

        default long in(double[] doubles) {
            return in(DoubleStream.of(doubles));
        }
    }

    public static DoubleCountFunction count(DoublePredicate predicate) {
        return stream -> stream.filter(predicate).count();
    }

    private DoubleCounters() {
        throw new UnsupportedOperationException();
    }
}

package com.github.moreaunicolas.util.stream;

import java.util.PrimitiveIterator;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.doubleStream;

public final class DoubleCounters {

    @FunctionalInterface
    public interface DoubleCountFunction {
        long in(DoubleStream doubles);

        default long in(double[] doubles) {
            return in(DoubleStream.of(doubles));
        }

        default long in(PrimitiveIterator.OfDouble iterator) {
            return in(doubleStream(iterator));
        }
    }

    public static DoubleCountFunction count(DoublePredicate predicate) {
        return stream -> stream.filter(predicate).count();
    }

    private DoubleCounters() {
        throw new UnsupportedOperationException();
    }
}

package com.github.moreaunicolas.util.stream;

import java.util.PrimitiveIterator;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.doubleStream;

public final class DoubleMatchers {

    @FunctionalInterface
    public interface DoubleMatchFunction {
        boolean in(DoubleStream doubles);

        default boolean in(double[] doubles) {
            return in(DoubleStream.of(doubles));
        }

        default boolean in(PrimitiveIterator.OfDouble iterator) {
            return in(doubleStream(iterator));
        }
    }

    public static DoubleMatchFunction allMatch(DoublePredicate predicate) {
        return stream -> stream.allMatch(predicate);
    }

    public static DoubleMatchFunction anyMatch(DoublePredicate predicate) {
        return stream -> stream.anyMatch(predicate);
    }

    public static DoubleMatchFunction noneMatch(DoublePredicate predicate) {
        return stream -> stream.noneMatch(predicate);
    }

    private DoubleMatchers() {
        throw new UnsupportedOperationException();
    }
}

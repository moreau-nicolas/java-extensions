package com.github.moreaunicolas.util.stream;

import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.doubleStream;

public final class DoubleFilters {

    @FunctionalInterface
    public interface DoubleSearchFunction<R> {
        R in(DoubleStream doubles);

        default R in(double[] doubles) {
            return in(DoubleStream.of(doubles));
        }

        default R in(PrimitiveIterator.OfDouble iterator) {
            return in(doubleStream(iterator));
        }
    }

    public static DoubleSearchFunction<DoubleStream> findAll(DoublePredicate predicate) {
        return stream -> stream.filter(predicate);
    }

    public static DoubleSearchFunction<OptionalDouble> findFirst(DoublePredicate predicate) {
        return stream -> stream.filter(predicate).findFirst();
    }

    public static DoubleSearchFunction<OptionalDouble> findAny(DoublePredicate predicate) {
        return stream -> stream.filter(predicate).findAny();
    }

    public static DoubleSearchFunction<OptionalDouble> minDouble() {
        return DoubleStream::min;
    }

    public static DoubleSearchFunction<OptionalDouble> maxDouble() {
        return DoubleStream::max;
    }

    private DoubleFilters() {
        throw new UnsupportedOperationException();
    }
}

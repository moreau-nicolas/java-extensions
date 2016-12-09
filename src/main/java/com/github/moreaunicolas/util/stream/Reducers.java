package com.github.moreaunicolas.util.stream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.stream;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

public final class Reducers {

    @FunctionalInterface
    public interface ReduceToLongFunction<T> {
        long in(Stream<T> objects);

        default long in(Iterable<T> objects) {
            return in(stream(objects));
        }

        default long in(T[] objects) {
            return in(Stream.of(objects));
        }

        default long in(Iterator<T> iterator) {
            return in(stream(iterator));
        }

        default long in(Enumeration<T> enumeration) {
            return in(stream(enumeration));
        }
    }

    public static <T> ReduceToLongFunction<T> sum(ToLongFunction<T> transformer) {
        return stream -> stream.mapToLong(transformer).sum();
    }

    @FunctionalInterface
    public interface ReduceToIntFunction<T> {
        int in(Stream<T> objects);

        default int in(Iterable<T> objects) {
            return in(stream(objects));
        }

        default int in(T[] objects) {
            return in(Stream.of(objects));
        }

        default int in(Iterator<T> iterator) {
            return in(stream(iterator));
        }

        default int in(Enumeration<T> enumeration) {
            return in(stream(enumeration));
        }
    }

    public static <T> ReduceToIntFunction<T> sum(ToIntFunction<T> transformer) {
        return stream -> stream.mapToInt(transformer).sum();
    }

    @FunctionalInterface public interface ReduceToDoubleFunction<T> {
        double in(Stream<T> objects);

        default double in(Iterable<T> objects) {
            return in(stream(objects));
        }

        default double in(T[] objects) {
            return in(Stream.of(objects));
        }

        default double in(Iterator<T> iterator) {
            return in(stream(iterator));
        }

        default double in(Enumeration<T> enumeration) {
            return in(stream(enumeration));
        }
    }

    public static <T> ReduceToDoubleFunction<T> sum(ToDoubleFunction<T> transformer) {
        return stream -> stream.mapToDouble(transformer).sum();
    }

    public static <T> ReduceToDoubleFunction<T> average(ToIntFunction<T> transformer) {
        return stream -> stream.mapToInt(transformer).summaryStatistics().getAverage();
    }

    public static <T> ReduceToDoubleFunction<T> average(ToLongFunction<T> transformer) {
        return stream -> stream.mapToLong(transformer).summaryStatistics().getAverage();
    }

    public static <T> ReduceToDoubleFunction<T> average(ToDoubleFunction<T> transformer) {
        return stream -> stream.mapToDouble(transformer).summaryStatistics().getAverage();
    }

    private Reducers() {
        throw new UnsupportedOperationException();
    }
}

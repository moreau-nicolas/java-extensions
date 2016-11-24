package com.github.moreaunicolas.util.stream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.stream;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Counters {

    @FunctionalInterface
    public interface CountFunction<T> {
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

    public static <T> CountFunction<T> count(Predicate<? super T> predicate) {
        return stream -> stream.filter(predicate).count();
    }

    private Counters() {
        throw new UnsupportedOperationException();
    }
}

package com.github.moreaunicolas.util.stream;

import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.stream;

public final class Filters {

    @FunctionalInterface
    public interface SearchFunction<T, R> {
        R in(Stream<T> objects);

        default R in(Iterable<T> objects) {
            return in(stream(objects));
        }

        default R in(T[] objects) {
            return in(Stream.of(objects));
        }

        default R in(Iterator<T> iterator) {
            return in(stream(iterator));
        }

        default R in(Enumeration<T> enumeration) {
            return in(stream(enumeration));
        }
    }

    public static <T> SearchFunction<T, Stream<T>> findAll(Predicate<? super T> predicate) {
        return stream -> stream.filter(predicate);
    }

    public static <T> SearchFunction<T, Optional<T>> findFirst(Predicate<? super T> predicate) {
        return stream -> stream.filter(predicate).findFirst();
    }

    public static <T> SearchFunction<T, Optional<T>> findAny(Predicate<? super T> predicate) {
        return stream -> stream.filter(predicate).findAny();
    }

    public static <T> SearchFunction<T, Optional<T>> min(Comparator<? super T> comparator) {
        return stream -> stream.min(comparator);
    }

    public static <T> SearchFunction<T, Optional<T>> max(Comparator<? super T> comparator) {
        return stream -> stream.max(comparator);
    }

    private Filters() {
        throw new UnsupportedOperationException();
    }
}

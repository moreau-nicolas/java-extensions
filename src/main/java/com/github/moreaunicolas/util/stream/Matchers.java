package com.github.moreaunicolas.util.stream;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.stream;

public final class Matchers {

    @FunctionalInterface
    public interface MatchFunction<T> {
        boolean in(Stream<T> objects);

        default boolean in(Iterable<T> objects) {
            return in(stream(objects));
        }

        default boolean in(T[] objects) {
            return in(Stream.of(objects));
        }

        default boolean in(Iterator<T> iterator) {
            return in(stream(iterator));
        }

        default boolean in(Enumeration<T> enumeration) {
            return in(stream(enumeration));
        }
    }

    public static <T> MatchFunction<T> allMatch(Predicate<? super T> predicate) {
        return stream -> stream.allMatch(predicate);
    }

    public static <T> MatchFunction<T> anyMatch(Predicate<? super T> predicate) {
        return stream -> stream.anyMatch(predicate);
    }

    public static <T> MatchFunction<T> noneMatch(Predicate<? super T> predicate) {
        return stream -> stream.noneMatch(predicate);
    }

    private Matchers() {
        throw new UnsupportedOperationException();
    }
}
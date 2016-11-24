package com.github.moreaunicolas.util;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ExtendedPredicates {

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }

    public static <T, U> BiPredicate<T, U> not(BiPredicate<T, U> predicate) {
        return predicate.negate();
    }

    @SafeVarargs
    @SuppressWarnings("OptionalGetWithoutIsPresent") // there are at least two predicates
    public static <T> Predicate<T> or(Predicate<T> first, Predicate<T> second, Predicate<T>... rest) {
        return stream(first, second, rest).reduce(Predicate::or).get();
    }

    @SafeVarargs
    @SuppressWarnings("OptionalGetWithoutIsPresent") // there are at least two predicates
    public static <T, U> BiPredicate<T, U> or(BiPredicate<T, U> first, BiPredicate<T, U> second, BiPredicate<T, U>... rest) {
        return stream(first, second, rest).reduce(BiPredicate::or).get();
    }

    @SafeVarargs
    @SuppressWarnings("OptionalGetWithoutIsPresent") // there are at least two predicates
    public static <T> Predicate<T> and(Predicate<T> first, Predicate<T> second, Predicate<T>... rest) {
        return stream(first, second, rest).reduce(Predicate::and).get();
    }

    @SafeVarargs
    @SuppressWarnings("OptionalGetWithoutIsPresent") // there are at least two predicates
    public static <T, U> BiPredicate<T, U> and(BiPredicate<T, U> first, BiPredicate<T, U> second, BiPredicate<T, U>... rest) {
        return stream(first, second, rest).reduce(BiPredicate::and).get();
    }

    @SafeVarargs
    public static <T> Predicate<T> xor(Predicate<T> first, Predicate<T> second, Predicate<T>... rest) {
        return value -> stream(first, second, rest)
                .mapToInt(predicate -> predicate.test(value) ? 1 : 0)
                .sum() == 1;
    }

    @SafeVarargs
    public static <T, U> BiPredicate<T, U> xor(BiPredicate<T, U> first, BiPredicate<T, U> second, BiPredicate<T, U>... rest) {
        return (a, b) -> stream(first, second, rest)
                .mapToInt(predicate -> predicate.test(a, b) ? 1 : 0)
                .sum() == 1;
    }

    private static <E> Stream<E> stream(E first, E second, E[] rest) {
        return Stream.concat(Stream.of(first, second), Stream.of(rest));
    }

    private ExtendedPredicates() {
        throw new UnsupportedOperationException();
    }
}

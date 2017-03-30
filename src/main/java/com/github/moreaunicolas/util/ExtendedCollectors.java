package com.github.moreaunicolas.util;

import static java.util.Collections.emptySet;
import static java.util.Map.Entry;

import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class ExtendedCollectors {

    public static <K, V, T extends Entry<K, V>>
    Collector<T, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Entry::getKey, Entry::getValue);
    }

    public static <K, V, T extends Entry<K, V>>
    Collector<T, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, mergeFunction);
    }

    public static <K, V, T extends Entry<K, V>, M extends Map<K, V>>
    Collector<T, ?, M> toMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, mergeFunction, mapSupplier);
    }

    public static <K, V, T extends Entry<K, V>, M extends Map<K, V>>
    Collector<T, ?, M> toMap(Supplier<M> mapSupplier) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, throwingMerger(), mapSupplier);
    }

    public static <K, V, T extends Entry<K, V>>
    Collector<T, ?, ConcurrentMap<K, V>> toConcurrentMap() {
        return Collectors.toConcurrentMap(Entry::getKey, Entry::getValue);
    }

    public static <K, V, T extends Entry<K, V>>
    Collector<T, ?, ConcurrentMap<K, V>> toConcurrentMap(BinaryOperator<V> mergeFunction) {
        return Collectors.toConcurrentMap(Entry::getKey, Entry::getValue, mergeFunction);
    }

    public static <K, V, T extends Entry<K, V>, M extends ConcurrentMap<K, V>>
    Collector<T, ?, M> toConcurrentMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier) {
        return Collectors.toConcurrentMap(Entry::getKey, Entry::getValue, mergeFunction, mapSupplier);
    }

    public static <K, V, T extends Entry<K, V>, M extends ConcurrentMap<K, V>>
    Collector<T, ?, M> toConcurrentMap(Supplier<M> mapSupplier) {
        return Collectors.toConcurrentMap(Entry::getKey, Entry::getValue, throwingMerger(), mapSupplier);
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); };
    }

    public static <E> Collector<E, StringJoiner, String> joining() {
        return joining("", "", "");
    }

    public static <E> Collector<E, StringJoiner, String> joining(String delimiter) {
        return joining(delimiter, "", "");
    }

    public static <E> Collector<E, StringJoiner, String> joining(String delimiter, String prefix, String suffix) {
        return new Collector<E, StringJoiner, String>() {
            @Override
            public Supplier<StringJoiner> supplier() {
                return () -> new StringJoiner(delimiter, prefix, suffix);
            }

            @Override
            public BiConsumer<StringJoiner, E> accumulator() {
                return (accumulator, element) -> accumulator.add(
                        element != null ? element.toString() : null
                );
            }

            @Override
            public BinaryOperator<StringJoiner> combiner() {
                return StringJoiner::merge;
            }

            @Override
            public Function<StringJoiner, String> finisher() {
                return StringJoiner::toString;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return emptySet();
            }
        };
    }

    private ExtendedCollectors() {
        throw new UnsupportedOperationException();
    }
}

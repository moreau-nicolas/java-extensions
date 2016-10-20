package com.github.moreaunicolas.util;

import static java.util.Map.Entry;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
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

    private ExtendedCollectors() {
        throw new UnsupportedOperationException();
    }
}

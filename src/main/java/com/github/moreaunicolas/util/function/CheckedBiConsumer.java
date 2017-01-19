package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedBiConsumer<T, U> {
    void accept(T first, U second) throws Exception;
}

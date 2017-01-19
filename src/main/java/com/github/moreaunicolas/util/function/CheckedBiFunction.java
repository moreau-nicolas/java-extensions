package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedBiFunction<T, U, R> {
    R apply(T first, U second) throws Exception;
}

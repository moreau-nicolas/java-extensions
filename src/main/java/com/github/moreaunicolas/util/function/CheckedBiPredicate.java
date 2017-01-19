package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedBiPredicate<T, U> {
    boolean test(T first, U second) throws Exception;
}

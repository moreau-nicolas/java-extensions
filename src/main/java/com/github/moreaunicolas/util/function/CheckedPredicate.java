package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedPredicate<T> {
    boolean test(T parameter) throws Exception;
}

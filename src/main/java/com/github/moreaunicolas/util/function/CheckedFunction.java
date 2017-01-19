package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T parameter) throws Exception;
}

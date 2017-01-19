package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedConsumer<T> {
    void accept(T parameter) throws Exception;
}

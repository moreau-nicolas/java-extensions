package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedSupplier<T> {
    T get() throws Exception;
}

package com.github.moreaunicolas.util.function;

@FunctionalInterface
public interface CheckedRunnable {
    void run() throws Exception;
}

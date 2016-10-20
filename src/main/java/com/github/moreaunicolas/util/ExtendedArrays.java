package com.github.moreaunicolas.util;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class ExtendedArrays {

    @SafeVarargs
    public static <T> Set<T> asSet(T... objects) {
        return Arrays.stream(objects)
            .collect(toSet());
    }

    private ExtendedArrays() {
        throw new UnsupportedOperationException();
    }
}

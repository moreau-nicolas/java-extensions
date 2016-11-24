package com.github.moreaunicolas.test;

import com.github.moreaunicolas.util.IteratorAsEnumeration;
import com.google.common.collect.Iterators;

import java.util.Enumeration;

public class Enumerations {

    @SafeVarargs
    public static <E> Enumeration<E> forArray(E... elements) {
        return new IteratorAsEnumeration<>(Iterators.forArray(elements));
    }
}

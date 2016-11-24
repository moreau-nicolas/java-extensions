package com.github.moreaunicolas.util;

import java.util.Enumeration;
import java.util.Iterator;

import static java.util.Objects.requireNonNull;

public class IteratorAsEnumeration<E> implements Enumeration<E> {

    private final Iterator<E> iterator;

    public IteratorAsEnumeration(Iterator<E> iterator) {
        this.iterator = requireNonNull(iterator);
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public E nextElement() {
        return iterator.next();
    }
}

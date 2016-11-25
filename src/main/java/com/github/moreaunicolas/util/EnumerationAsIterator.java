package com.github.moreaunicolas.util;

import java.util.Enumeration;
import java.util.Iterator;

import static java.util.Objects.requireNonNull;

public final class EnumerationAsIterator<E> implements Iterator<E> {

    private final Enumeration<E> enumeration;

    public EnumerationAsIterator(Enumeration<E> enumeration) {
        this.enumeration = requireNonNull(enumeration);
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public E next() {
        return enumeration.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

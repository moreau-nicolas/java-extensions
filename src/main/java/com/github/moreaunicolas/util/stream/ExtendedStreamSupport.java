package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.util.EnumerationAsIterator;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ExtendedStreamSupport {

    public static <T> Stream<T> stream(Iterable<T> objects) {
        return StreamSupport.stream(objects.spliterator(), false);
    }

    public static <T> Stream<T> stream(Iterator<T> iterator) {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        return StreamSupport.stream(spliterator, false);
    }

    public static <T> Stream<T> stream(Enumeration<T> enumeration) {
        return stream(new EnumerationAsIterator<>(enumeration));
    }

    private ExtendedStreamSupport() {
        throw new UnsupportedOperationException();
    }
}

package com.github.moreaunicolas.util.stream;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ExtendedStreamSupport {

    public static <T> Stream<T> stream(Iterable<T> objects) {
        return StreamSupport.stream(objects.spliterator(), false);
    }
}

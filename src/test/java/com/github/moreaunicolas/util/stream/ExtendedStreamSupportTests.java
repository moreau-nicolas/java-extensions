package com.github.moreaunicolas.util.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtendedStreamSupportTests {

    @Test
    public void wrappingAnIterableReturnsTheSameElementsInOrder() {
        Iterable<String> letters = Arrays.asList("a", "b", "c");

        Stream<String> result = ExtendedStreamSupport.stream(letters);

        assertThat(result).containsExactly("a", "b", "c");
    }

    @Test
    public void wrappingAnIterableReturnsANonParallelStream() {
        Iterable<String> letters = Arrays.asList("a", "b", "c");

        Stream<String> result = ExtendedStreamSupport.stream(letters);

        assertThat(result.isParallel()).isFalse();
    }

    @Test
    public void wrappingAnIteratorReturnsTheSameElementsInOrder() {
        Iterator<String> letters = Arrays.asList("a", "b", "c").iterator();

        Stream<String> result = ExtendedStreamSupport.stream(letters);

        assertThat(result).containsExactly("a", "b", "c");
    }

    @Test
    public void wrappingAnIteratorReturnsANonParallelStream() {
        Iterator<String> letters = Arrays.asList("a", "b", "c").iterator();

        Stream<String> result = ExtendedStreamSupport.stream(letters);

        assertThat(result.isParallel()).isFalse();
    }
}
package com.github.moreaunicolas.util;

import org.junit.Test;

import java.util.NoSuchElementException;

import static com.google.common.collect.Iterators.singletonIterator;
import static java.util.Collections.emptyEnumeration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EnumerationAsIteratorTests {

    @Test
    public void constructorThrowsWhenCalledWithNullEnumeration() {
        Throwable caught = catchThrowable(() -> new EnumerationAsIterator<>(null));

        assertThat(caught).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void hasNextReturnsFalseWhenEnumerationHasNoMoreElements() {
        EnumerationAsIterator<Object> iterator = new EnumerationAsIterator<>(emptyEnumeration());

        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void hasNextReturnsTrueWhenEnumerationHasMoreElements() {
        EnumerationAsIterator<Object> iterator = new EnumerationAsIterator<>(singletonEnumeration());

        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    public void nextReturnsTheNextElementWhenThereIsOne() {
        EnumerationAsIterator<Object> iterator = new EnumerationAsIterator<>(singletonEnumeration());

        assertThat(iterator.next()).isEqualTo("test");
    }

    @Test
    public void nextThrowsWhenThereIsNoMoreElements() {
        EnumerationAsIterator<Object> iterator = new EnumerationAsIterator<>(emptyEnumeration());

        Throwable caught = catchThrowable(iterator::next);

        assertThat(caught).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void removeThrowsUnsupportedOperationException() {
        EnumerationAsIterator<Object> iterator = new EnumerationAsIterator<>(singletonEnumeration());

        Throwable caught = catchThrowable(iterator::remove);

        assertThat(caught).isInstanceOf(UnsupportedOperationException.class);
    }

    private static IteratorAsEnumeration<Object> singletonEnumeration() {
        return new IteratorAsEnumeration<>(singletonIterator("test"));
    }
}
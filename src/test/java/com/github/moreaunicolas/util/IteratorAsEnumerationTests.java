package com.github.moreaunicolas.util;

import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Collections.emptyIterator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class IteratorAsEnumerationTests {

    @Test
    public void constructorThrowsWhenCalledWithNullIterator() {
        Throwable caught = catchThrowable(() -> new IteratorAsEnumeration<>(null));

        assertThat(caught).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void hasMoreElementsReturnsFalseWhenIteratorHasNoNextElement() {
        IteratorAsEnumeration<Object> enumeration = new IteratorAsEnumeration<>(emptyIterator());

        assertThat(enumeration.hasMoreElements()).isFalse();
    }

    @Test
    public void hasMoreElementsReturnsTrueWhenIteratorHasANextElement() {
        IteratorAsEnumeration<Object> enumeration = new IteratorAsEnumeration<>(singletonIterator());

        assertThat(enumeration.hasMoreElements()).isTrue();
    }

    @Test
    public void nextElementsReturnsTheNextElementWhenThereIsOne() {
        IteratorAsEnumeration<Object> enumeration = new IteratorAsEnumeration<>(singletonIterator());

        assertThat(enumeration.nextElement()).isEqualTo("test");
    }

    @Test
    public void nextElementsThrowsWhenThereIsNoNextElement() {
        IteratorAsEnumeration<Object> enumeration = new IteratorAsEnumeration<>(emptyIterator());

        Throwable caught = catchThrowable(enumeration::nextElement);

        assertThat(caught).isInstanceOf(NoSuchElementException.class);
    }

    private static Iterator<Object> singletonIterator() {
        return Iterators.singletonIterator("test");
    }
}
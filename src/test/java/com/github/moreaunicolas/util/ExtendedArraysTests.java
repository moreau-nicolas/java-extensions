package com.github.moreaunicolas.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.Test;

public class ExtendedArraysTests {

    @Test
    public void asSet_empty() {
        Set<String> empty = ExtendedArrays.asSet();

        assertThat(empty).isEmpty();
    }

    @Test
    public void asSet_singleElement() {
        int fortyTwo = 42;

        Set<Integer> numbers = ExtendedArrays.asSet(fortyTwo);

        assertThat(numbers).containsOnly(fortyTwo);
    }

    @Test
    public void asSet_duplicates() {
        int fortyTwo = 42;

        Set<Integer> numbers = ExtendedArrays.asSet(fortyTwo, fortyTwo);

        assertThat(numbers).hasSize(1);
    }

    @Test
    public void asSet_severalElements() {
        String holmes = "Sherlock Holmes";
        String watson = "John Watson";

        Set<String> detectives = ExtendedArrays.asSet(holmes, watson);

        assertThat(detectives).containsOnly(watson, holmes);
    }
}

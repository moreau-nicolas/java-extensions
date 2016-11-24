package com.github.moreaunicolas.util.stream;

import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.moreaunicolas.util.stream.Filters.*;
import static java.util.Comparator.naturalOrder;
import static org.assertj.core.api.Assertions.assertThat;

public class FiltersTests {

    private static final String[] LETTERS = { "a", "b", "c", "d", "e" };
    private static final List<String> VOWELS = Arrays.asList("a", "e", "i", "o", "u");

    @Test
    public void findAllReturnsSatisfyingElements() {
        Predicate<String> isVowel = VOWELS::contains;

        SearchFunction<String, Stream<String>> findAllVowels = findAll(isVowel);

        assertThat(findAllVowels.in(LETTERS)).contains("a", "e");
        assertThat(findAllVowels.in(Stream.of(LETTERS))).contains("a", "e");
        assertThat(findAllVowels.in(Arrays.asList(LETTERS))).contains("a", "e");
        assertThat(findAllVowels.in(Iterators.forArray(LETTERS))).contains("a", "e");
    }

    @Test
    public void findAllReturnsEmptyWhenThereAreNoSatisfyingElements() {
        Predicate<String> equalToZ = "z"::equals;

        SearchFunction<String, Stream<String>> findAllEqualToZ = findAll(equalToZ);

        assertThat(findAllEqualToZ.in(LETTERS)).isEmpty();
        assertThat(findAllEqualToZ.in(Stream.of(LETTERS))).isEmpty();
        assertThat(findAllEqualToZ.in(Arrays.asList(LETTERS))).isEmpty();
        assertThat(findAllEqualToZ.in(Iterators.forArray(LETTERS))).isEmpty();
    }

    @Test
    public void findFirstReturnsTheFirstSatisfyingElement() {
        Predicate<String> isVowel = VOWELS::contains;

        SearchFunction<String, Optional<String>> findFirstVowel = findFirst(isVowel);

        assertThat(findFirstVowel.in(LETTERS)).contains("a");
        assertThat(findFirstVowel.in(Stream.of(LETTERS))).contains("a");
        assertThat(findFirstVowel.in(Arrays.asList(LETTERS))).contains("a");
        assertThat(findFirstVowel.in(Iterators.forArray(LETTERS))).contains("a");
    }

    @Test
    public void findFirstReturnsEmptyWhenThereAreNoSatisfyingElements() {
        Predicate<String> equalToZ = "z"::equals;

        SearchFunction<String, Optional<String>> findFirstEqualToZ = findFirst(equalToZ);

        assertThat(findFirstEqualToZ.in(LETTERS)).isEmpty();
        assertThat(findFirstEqualToZ.in(Stream.of(LETTERS))).isEmpty();
        assertThat(findFirstEqualToZ.in(Arrays.asList(LETTERS))).isEmpty();
        assertThat(findFirstEqualToZ.in(Iterators.forArray(LETTERS))).isEmpty();
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent") // get() will not fail
    public void findAnyReturnsAnySatisfyingElement() {
        Predicate<String> isVowel = VOWELS::contains;

        SearchFunction<String, Optional<String>> findAnyVowel = findAny(isVowel);

        assertThat(findAnyVowel.in(LETTERS).get()).isIn("a", "e");
        assertThat(findAnyVowel.in(Stream.of(LETTERS)).get()).isIn("a", "e");
        assertThat(findAnyVowel.in(Arrays.asList(LETTERS)).get()).isIn("a", "e");
        assertThat(findAnyVowel.in(Iterators.forArray(LETTERS)).get()).isIn("a", "e");
    }

    @Test
    public void findAnyReturnsEmptyWhenThereAreNoSatisfyingElements() {
        Predicate<String> equalToZ = "z"::equals;

        SearchFunction<String, Optional<String>> findAnyEqualToZ = findAny(equalToZ);

        assertThat(findAnyEqualToZ.in(LETTERS)).isEmpty();
        assertThat(findAnyEqualToZ.in(Stream.of(LETTERS))).isEmpty();
        assertThat(findAnyEqualToZ.in(Arrays.asList(LETTERS))).isEmpty();
        assertThat(findAnyEqualToZ.in(Iterators.forArray(LETTERS))).isEmpty();
    }

    @Test
    public void minReturnsMinimumElement() {
        Comparator<String> order = naturalOrder();

        SearchFunction<String, Optional<String>> min = min(order);

        assertThat(min.in(LETTERS)).contains("a");
        assertThat(min.in(Stream.of(LETTERS))).contains("a");
        assertThat(min.in(Arrays.asList(LETTERS))).contains("a");
        assertThat(min.in(Iterators.forArray(LETTERS))).contains("a");
    }

    @Test
    public void maxReturnsMaximumElement() {
        Comparator<String> order = naturalOrder();

        SearchFunction<String, Optional<String>> max = max(order);

        assertThat(max.in(LETTERS)).contains("e");
        assertThat(max.in(Stream.of(LETTERS))).contains("e");
        assertThat(max.in(Arrays.asList(LETTERS))).contains("e");
        assertThat(max.in(Iterators.forArray(LETTERS))).contains("e");
    }
}

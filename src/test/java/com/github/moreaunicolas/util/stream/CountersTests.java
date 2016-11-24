package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.Enumerations;
import com.github.moreaunicolas.util.stream.Counters.CountFunction;
import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.moreaunicolas.util.stream.Counters.count;
import static org.assertj.core.api.Assertions.assertThat;

public class CountersTests {

    private static final String[] LETTERS = { "a", "b", "c", "d", "e" };
    private static final List<String> VOWELS = Arrays.asList("a", "e", "i", "o", "u");

    @Test
    public void countReturnsNumberOfSatisfyingElement() {
        Predicate<String> isVowel = VOWELS::contains;

        CountFunction<String> countVowels = count(isVowel);

        assertThat(countVowels.in(LETTERS)).isEqualTo(2);
        assertThat(countVowels.in(Stream.of(LETTERS))).isEqualTo(2);
        assertThat(countVowels.in(Arrays.asList(LETTERS))).isEqualTo(2);
        assertThat(countVowels.in(Iterators.forArray(LETTERS))).isEqualTo(2);
        assertThat(countVowels.in(Enumerations.forArray(LETTERS))).isEqualTo(2);
    }

    @Test
    public void countReturnsZeroWhenThereAreNoSatisfyingElements() {
        Predicate<String> equalToZ = "z"::equals;

        CountFunction<String> countZs = count(equalToZ);

        assertThat(countZs.in(LETTERS)).isEqualTo(0);
        assertThat(countZs.in(Stream.of(LETTERS))).isEqualTo(0);
        assertThat(countZs.in(Arrays.asList(LETTERS))).isEqualTo(0);
        assertThat(countZs.in(Iterators.forArray(LETTERS))).isEqualTo(0);
        assertThat(countZs.in(Enumerations.forArray(LETTERS))).isEqualTo(0);
    }
}

package com.github.moreaunicolas.util.stream;

import static com.github.moreaunicolas.util.stream.Matchers.allMatch;
import static com.github.moreaunicolas.util.stream.Matchers.anyMatch;
import static com.github.moreaunicolas.util.stream.Matchers.noneMatch;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.moreaunicolas.util.stream.Matchers.MatchFunction;
import org.junit.Test;

public class MatchersTests {

    private static final String[] LETTERS = { "a", "b", "c", "d" };

    @Test
    public void allMatchReturnsTrueWhenAllElementsMatch() {
        Predicate<String> singleLetter = s -> s.length() == 1;

        MatchFunction<String> allMatchSingleLetter = allMatch(singleLetter);

        assertThat(allMatchSingleLetter.in(LETTERS)).isTrue();
        assertThat(allMatchSingleLetter.in(Stream.of(LETTERS))).isTrue();
        assertThat(allMatchSingleLetter.in(Arrays.asList(LETTERS))).isTrue();
    }

    @Test
    public void allMatchReturnsFalseWhenAnyElementDoesNotMatch() {
        Predicate<String> equalToB = "b"::equals;

        MatchFunction<String> allMatchEqualToB = allMatch(equalToB);

        assertThat(allMatchEqualToB.in(LETTERS)).isFalse();
        assertThat(allMatchEqualToB.in(Stream.of(LETTERS))).isFalse();
        assertThat(allMatchEqualToB.in(Arrays.asList(LETTERS))).isFalse();
    }

    @Test
    public void anyMatchReturnsTrueWhenAnyElementMatches() {
        Predicate<String> equalToC = "c"::equals;

        MatchFunction<String> anyMatchEqualToC = anyMatch(equalToC);

        assertThat(anyMatchEqualToC.in(LETTERS)).isTrue();
        assertThat(anyMatchEqualToC.in(Stream.of(LETTERS))).isTrue();
        assertThat(anyMatchEqualToC.in(Arrays.asList(LETTERS))).isTrue();
    }

    @Test
    public void anyMatchReturnsFalseWhenNoElementsMatch() {
        Predicate<String> equalToZ = "z"::equals;

        MatchFunction<String> anyMatchEqualToZ = anyMatch(equalToZ);

        assertThat(anyMatchEqualToZ.in(LETTERS)).isFalse();
        assertThat(anyMatchEqualToZ.in(Stream.of(LETTERS))).isFalse();
        assertThat(anyMatchEqualToZ.in(Arrays.asList(LETTERS))).isFalse();
    }

    @Test
    public void noneMatchReturnsTrueWhenNoElementsMatch() {
        Predicate<String> equalToZ = "z"::equals;

        MatchFunction<String> noneMatchEqualToZ = noneMatch(equalToZ);

        assertThat(noneMatchEqualToZ.in(LETTERS)).isTrue();
        assertThat(noneMatchEqualToZ.in(Stream.of(LETTERS))).isTrue();
        assertThat(noneMatchEqualToZ.in(Arrays.asList(LETTERS))).isTrue();
    }

    @Test
    public void noneMatchReturnsFalseWhenAnyElementMatches() {
        Predicate<String> equalToB = "b"::equals;

        MatchFunction<String> noneMatchEqualToB = noneMatch(equalToB);

        assertThat(noneMatchEqualToB.in(LETTERS)).isFalse();
        assertThat(noneMatchEqualToB.in(Stream.of(LETTERS))).isFalse();
        assertThat(noneMatchEqualToB.in(Arrays.asList(LETTERS))).isFalse();
    }
}

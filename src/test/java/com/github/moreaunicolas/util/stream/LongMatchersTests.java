package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.util.stream.LongMatchers.*;
import org.junit.Test;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

import static com.github.moreaunicolas.util.stream.LongMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LongMatchersTests {

    private static final long[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final LongPredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final LongPredicate IS_NEGATIVE = number -> number <= 0;
    private static final LongPredicate IS_POSITIVE = number -> number >= 0;

    @Test
    public void allMatchReturnsTrueWhenAllElementsMatch() {
        LongMatchFunction allMatchPositive = allMatch(IS_POSITIVE);

        assertThat(allMatchPositive.in(NUMBERS)).isTrue();
        assertThat(allMatchPositive.in(LongStream.of(NUMBERS))).isTrue();
    }

    @Test
    public void allMatchReturnsFalseWhenAnyElementDoesNotMatch() {
        LongMatchFunction allMatchMultipleOf4 = allMatch(IS_MULTIPLE_OF_4);

        assertThat(allMatchMultipleOf4.in(NUMBERS)).isFalse();
        assertThat(allMatchMultipleOf4.in(LongStream.of(NUMBERS))).isFalse();
    }

    @Test
    public void anyMatchReturnsTrueWhenAnyElementMatches() {
        LongMatchFunction anyMatchMultipleOf4 = anyMatch(IS_MULTIPLE_OF_4);

        assertThat(anyMatchMultipleOf4.in(NUMBERS)).isTrue();
        assertThat(anyMatchMultipleOf4.in(LongStream.of(NUMBERS))).isTrue();
    }

    @Test
    public void anyMatchReturnsFalseWhenNoElementsMatch() {
        LongMatchFunction anyMatchNegative = anyMatch(IS_NEGATIVE);

        assertThat(anyMatchNegative.in(NUMBERS)).isFalse();
        assertThat(anyMatchNegative.in(LongStream.of(NUMBERS))).isFalse();
    }

    @Test
    public void noneMatchReturnsTrueWhenNoElementsMatch() {
        LongMatchFunction noneMatchNegative = noneMatch(IS_NEGATIVE);

        assertThat(noneMatchNegative.in(NUMBERS)).isTrue();
        assertThat(noneMatchNegative.in(LongStream.of(NUMBERS))).isTrue();
    }

    @Test
    public void noneMatchReturnsFalseWhenAnyElementMatches() {
        LongMatchFunction noneMatchMultipleOf4 = noneMatch(IS_MULTIPLE_OF_4);

        assertThat(noneMatchMultipleOf4.in(NUMBERS)).isFalse();
        assertThat(noneMatchMultipleOf4.in(LongStream.of(NUMBERS))).isFalse();
    }
}

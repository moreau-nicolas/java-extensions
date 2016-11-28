package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.stream.IntMatchers.*;
import org.junit.Test;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.github.moreaunicolas.util.stream.IntMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class IntMatchersTests {

    private static final int[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final IntPredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final IntPredicate IS_NEGATIVE = number -> number <= 0;
    private static final IntPredicate IS_POSITIVE = number -> number >= 0;

    @Test
    public void allMatchReturnsTrueWhenAllElementsMatch() {
        IntMatchFunction allMatchPositive = allMatch(IS_POSITIVE);

        assertThat(allMatchPositive.in(NUMBERS)).isTrue();
        assertThat(allMatchPositive.in(IntStream.of(NUMBERS))).isTrue();
        assertThat(allMatchPositive.in(IntStream.of(NUMBERS).iterator())).isTrue();
    }

    @Test
    public void allMatchReturnsFalseWhenAnyElementDoesNotMatch() {
        IntMatchFunction allMatchMultipleOf4 = allMatch(IS_MULTIPLE_OF_4);

        assertThat(allMatchMultipleOf4.in(NUMBERS)).isFalse();
        assertThat(allMatchMultipleOf4.in(IntStream.of(NUMBERS))).isFalse();
        assertThat(allMatchMultipleOf4.in(IntStream.of(NUMBERS).iterator())).isFalse();
    }

    @Test
    public void anyMatchReturnsTrueWhenAnyElementMatches() {
        IntMatchFunction anyMatchMultipleOf4 = anyMatch(IS_MULTIPLE_OF_4);

        assertThat(anyMatchMultipleOf4.in(NUMBERS)).isTrue();
        assertThat(anyMatchMultipleOf4.in(IntStream.of(NUMBERS))).isTrue();
        assertThat(anyMatchMultipleOf4.in(IntStream.of(NUMBERS).iterator())).isTrue();
    }

    @Test
    public void anyMatchReturnsFalseWhenNoElementsMatch() {
        IntMatchFunction anyMatchNegative = anyMatch(IS_NEGATIVE);

        assertThat(anyMatchNegative.in(NUMBERS)).isFalse();
        assertThat(anyMatchNegative.in(IntStream.of(NUMBERS))).isFalse();
        assertThat(anyMatchNegative.in(IntStream.of(NUMBERS).iterator())).isFalse();
    }

    @Test
    public void noneMatchReturnsTrueWhenNoElementsMatch() {
        IntMatchFunction noneMatchNegative = noneMatch(IS_NEGATIVE);

        assertThat(noneMatchNegative.in(NUMBERS)).isTrue();
        assertThat(noneMatchNegative.in(IntStream.of(NUMBERS))).isTrue();
        assertThat(noneMatchNegative.in(IntStream.of(NUMBERS).iterator())).isTrue();
    }

    @Test
    public void noneMatchReturnsFalseWhenAnyElementMatches() {
        IntMatchFunction noneMatchMultipleOf4 = noneMatch(IS_MULTIPLE_OF_4);

        assertThat(noneMatchMultipleOf4.in(NUMBERS)).isFalse();
        assertThat(noneMatchMultipleOf4.in(IntStream.of(NUMBERS))).isFalse();
        assertThat(noneMatchMultipleOf4.in(IntStream.of(NUMBERS).iterator())).isFalse();
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(IntMatchers.class)
                .isUtilityClass();
    }
}

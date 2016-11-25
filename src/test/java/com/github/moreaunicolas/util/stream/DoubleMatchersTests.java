package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.ExtendedArrays;
import com.github.moreaunicolas.util.stream.DoubleMatchers.DoubleMatchFunction;
import org.junit.Test;

import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import static com.github.moreaunicolas.util.stream.DoubleMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DoubleMatchersTests {

    private static final double[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final DoublePredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final DoublePredicate IS_NEGATIVE = number -> number <= 0;
    private static final DoublePredicate IS_POSITIVE = number -> number >= 0;

    @Test
    public void allMatchReturnsTrueWhenAllElementsMatch() {
        DoubleMatchFunction allMatchPositive = allMatch(IS_POSITIVE);

        assertThat(allMatchPositive.in(NUMBERS)).isTrue();
        assertThat(allMatchPositive.in(DoubleStream.of(NUMBERS))).isTrue();
    }

    @Test
    public void allMatchReturnsFalseWhenAnyElementDoesNotMatch() {
        DoubleMatchFunction allMatchMultipleOf4 = allMatch(IS_MULTIPLE_OF_4);

        assertThat(allMatchMultipleOf4.in(NUMBERS)).isFalse();
        assertThat(allMatchMultipleOf4.in(DoubleStream.of(NUMBERS))).isFalse();
    }

    @Test
    public void anyMatchReturnsTrueWhenAnyElementMatches() {
        DoubleMatchFunction anyMatchMultipleOf4 = anyMatch(IS_MULTIPLE_OF_4);

        assertThat(anyMatchMultipleOf4.in(NUMBERS)).isTrue();
        assertThat(anyMatchMultipleOf4.in(DoubleStream.of(NUMBERS))).isTrue();
    }

    @Test
    public void anyMatchReturnsFalseWhenNoElementsMatch() {
        DoubleMatchFunction anyMatchNegative = anyMatch(IS_NEGATIVE);

        assertThat(anyMatchNegative.in(NUMBERS)).isFalse();
        assertThat(anyMatchNegative.in(DoubleStream.of(NUMBERS))).isFalse();
    }

    @Test
    public void noneMatchReturnsTrueWhenNoElementsMatch() {
        DoubleMatchFunction noneMatchNegative = noneMatch(IS_NEGATIVE);

        assertThat(noneMatchNegative.in(NUMBERS)).isTrue();
        assertThat(noneMatchNegative.in(DoubleStream.of(NUMBERS))).isTrue();
    }

    @Test
    public void noneMatchReturnsFalseWhenAnyElementMatches() {
        DoubleMatchFunction noneMatchMultipleOf4 = noneMatch(IS_MULTIPLE_OF_4);

        assertThat(noneMatchMultipleOf4.in(NUMBERS)).isFalse();
        assertThat(noneMatchMultipleOf4.in(DoubleStream.of(NUMBERS))).isFalse();
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(DoubleMatchers.class)
                .isUtilityClass();
    }
}

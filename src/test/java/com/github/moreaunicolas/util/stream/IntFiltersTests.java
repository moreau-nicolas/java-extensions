package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import org.junit.Test;

import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.github.moreaunicolas.util.stream.IntFilters.*;
import static org.assertj.core.api.Assertions.assertThat;

public class IntFiltersTests {

    private static final int[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final IntPredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final IntPredicate IS_NEGATIVE = number -> number <= 0;

    @Test
    public void findAllReturnsSatisfyingElements() {
        IntSearchFunction<IntStream> findAllMultiplesOf4 = findAll(IS_MULTIPLE_OF_4);

        assertThat(findAllMultiplesOf4.in(NUMBERS).boxed()).contains(20, 40);
        assertThat(findAllMultiplesOf4.in(IntStream.of(NUMBERS)).boxed()).contains(20, 40);
        assertThat(findAllMultiplesOf4.in(IntStream.of(NUMBERS).iterator()).boxed()).contains(20, 40);
    }

    @Test
    public void findAllReturnsEmptyWhenThereAreNoSatisfyingElements() {
        IntSearchFunction<IntStream> findAllMultiplesOf4 = findAll(IS_NEGATIVE);

        assertThat(findAllMultiplesOf4.in(NUMBERS).boxed()).isEmpty();
        assertThat(findAllMultiplesOf4.in(IntStream.of(NUMBERS)).boxed()).isEmpty();
        assertThat(findAllMultiplesOf4.in(IntStream.of(NUMBERS).iterator()).boxed()).isEmpty();
    }

    @Test
    public void findFirstReturnsTheFirstSatisfyingElement() {
        IntSearchFunction<OptionalInt> findFirstMultipleOf4 = findFirst(IS_MULTIPLE_OF_4);

        assertThat(findFirstMultipleOf4.in(NUMBERS)).hasValue(20);
        assertThat(findFirstMultipleOf4.in(IntStream.of(NUMBERS))).hasValue(20);
        assertThat(findFirstMultipleOf4.in(IntStream.of(NUMBERS).iterator())).hasValue(20);
    }

    @Test
    public void findFirstReturnsEmptyWhenThereAreNoSatisfyingElements() {
        IntSearchFunction<OptionalInt> findFirstMultipleOf4 = findFirst(IS_NEGATIVE);

        assertThat(findFirstMultipleOf4.in(NUMBERS)).isEmpty();
        assertThat(findFirstMultipleOf4.in(IntStream.of(NUMBERS))).isEmpty();
        assertThat(findFirstMultipleOf4.in(IntStream.of(NUMBERS).iterator())).isEmpty();
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void findAnyReturnsAnySatisfyingElement() {
        IntSearchFunction<OptionalInt> findAnyMultipleOf4 = findAny(IS_MULTIPLE_OF_4);

        assertThat(findAnyMultipleOf4.in(NUMBERS).getAsInt()).isIn(20, 40);
        assertThat(findAnyMultipleOf4.in(IntStream.of(NUMBERS)).getAsInt()).isIn(20, 40);
        assertThat(findAnyMultipleOf4.in(IntStream.of(NUMBERS).iterator()).getAsInt()).isIn(20, 40);
    }

    @Test
    public void findAnyReturnsEmptyWhenThereAreNoSatisfyingElements() {
        IntSearchFunction<OptionalInt> findAnyMultipleOf4 = findAny(IS_NEGATIVE);

        assertThat(findAnyMultipleOf4.in(NUMBERS)).isEmpty();
        assertThat(findAnyMultipleOf4.in(IntStream.of(NUMBERS))).isEmpty();
        assertThat(findAnyMultipleOf4.in(IntStream.of(NUMBERS).iterator())).isEmpty();
    }

    @Test
    public void minReturnsMinimumElement() {

        assertThat(minInt().in(NUMBERS)).hasValue(10);
        assertThat(minInt().in(IntStream.of(NUMBERS))).hasValue(10);
        assertThat(minInt().in(IntStream.of(NUMBERS).iterator())).hasValue(10);
    }

    @Test
    public void maxReturnsMaximumElement() {

        assertThat(maxInt().in(NUMBERS)).hasValue(50);
        assertThat(maxInt().in(IntStream.of(NUMBERS))).hasValue(50);
        assertThat(maxInt().in(IntStream.of(NUMBERS).iterator())).hasValue(50);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(IntFilters.class)
                .isUtilityClass();
    }
}

package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.ExtendedArrays;
import org.junit.Test;

import java.util.OptionalLong;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

import static com.github.moreaunicolas.util.stream.LongFilters.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LongFiltersTests {

    private static final long[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final LongPredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final LongPredicate IS_NEGATIVE = number -> number <= 0;

    @Test
    public void findAllReturnsSatisfyingElements() {
        LongSearchFunction<LongStream> findAllMultiplesOf4 = findAll(IS_MULTIPLE_OF_4);

        assertThat(findAllMultiplesOf4.in(NUMBERS).boxed()).contains(20L, 40L);
        assertThat(findAllMultiplesOf4.in(LongStream.of(NUMBERS)).boxed()).contains(20L, 40L);
    }

    @Test
    public void findAllReturnsEmptyWhenThereAreNoSatisfyingElements() {
        LongSearchFunction<LongStream> findAllMultiplesOf4 = findAll(IS_NEGATIVE);

        assertThat(findAllMultiplesOf4.in(NUMBERS).boxed()).isEmpty();
        assertThat(findAllMultiplesOf4.in(LongStream.of(NUMBERS)).boxed()).isEmpty();
    }

    @Test
    public void findFirstReturnsTheFirstSatisfyingElement() {
        LongSearchFunction<OptionalLong> findFirstMultipleOf4 = findFirst(IS_MULTIPLE_OF_4);

        assertThat(findFirstMultipleOf4.in(NUMBERS)).hasValue(20);
        assertThat(findFirstMultipleOf4.in(LongStream.of(NUMBERS))).hasValue(20);
    }

    @Test
    public void findFirstReturnsEmptyWhenThereAreNoSatisfyingElements() {
        LongSearchFunction<OptionalLong> findFirstMultipleOf4 = findFirst(IS_NEGATIVE);

        assertThat(findFirstMultipleOf4.in(NUMBERS)).isEmpty();
        assertThat(findFirstMultipleOf4.in(LongStream.of(NUMBERS))).isEmpty();
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void findAnyReturnsAnySatisfyingElement() {
        LongSearchFunction<OptionalLong> findAnyMultipleOf4 = findAny(IS_MULTIPLE_OF_4);

        assertThat(findAnyMultipleOf4.in(NUMBERS).getAsLong()).isIn(20L, 40L);
        assertThat(findAnyMultipleOf4.in(LongStream.of(NUMBERS)).getAsLong()).isIn(20L, 40L);
    }

    @Test
    public void findAnyReturnsEmptyWhenThereAreNoSatisfyingElements() {
        LongSearchFunction<OptionalLong> findAnyMultipleOf4 = findAny(IS_NEGATIVE);

        assertThat(findAnyMultipleOf4.in(NUMBERS)).isEmpty();
        assertThat(findAnyMultipleOf4.in(LongStream.of(NUMBERS))).isEmpty();
    }

    @Test
    public void minReturnsMinimumElement() {

        assertThat(minLong().in(NUMBERS)).hasValue(10);
        assertThat(minLong().in(LongStream.of(NUMBERS))).hasValue(10);
    }

    @Test
    public void maxReturnsMaximumElement() {

        assertThat(maxLong().in(NUMBERS)).hasValue(50);
        assertThat(maxLong().in(LongStream.of(NUMBERS))).hasValue(50);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(LongFilters.class)
                .isUtilityClass();
    }
}

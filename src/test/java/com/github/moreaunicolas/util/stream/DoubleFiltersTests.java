package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.ExtendedArrays;
import com.github.moreaunicolas.util.stream.DoubleFilters.*;
import org.junit.Test;

import java.util.OptionalDouble;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import static com.github.moreaunicolas.util.stream.DoubleFilters.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DoubleFiltersTests {

    private static final double[] NUMBERS = {10, 20, 30, 40, 50};

    private static final DoublePredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final DoublePredicate IS_NEGATIVE = number -> number <= 0;

    @Test
    public void findAllReturnsSatisfyingElements() {
        DoubleSearchFunction<DoubleStream> findAllMultiplesOf4 = findAll(IS_MULTIPLE_OF_4);

        assertThat(findAllMultiplesOf4.in(NUMBERS).boxed()).contains(20., 40.);
        assertThat(findAllMultiplesOf4.in(DoubleStream.of(NUMBERS)).boxed()).contains(20., 40.);
    }

    @Test
    public void findAllReturnsEmptyWhenThereAreNoSatisfyingElements() {
        DoubleSearchFunction<DoubleStream> findAllMultiplesOf4 = findAll(IS_NEGATIVE);

        assertThat(findAllMultiplesOf4.in(NUMBERS).boxed()).isEmpty();
        assertThat(findAllMultiplesOf4.in(DoubleStream.of(NUMBERS)).boxed()).isEmpty();
    }

    @Test
    public void findFirstReturnsTheFirstSatisfyingElement() {
        DoubleSearchFunction<OptionalDouble> findFirstMultipleOf4 = findFirst(IS_MULTIPLE_OF_4);

        assertThat(findFirstMultipleOf4.in(NUMBERS)).hasValue(20);
        assertThat(findFirstMultipleOf4.in(DoubleStream.of(NUMBERS))).hasValue(20);
    }

    @Test
    public void findFirstReturnsEmptyWhenThereAreNoSatisfyingElements() {
        DoubleSearchFunction<OptionalDouble> findFirstMultipleOf4 = findFirst(IS_NEGATIVE);

        assertThat(findFirstMultipleOf4.in(NUMBERS)).isEmpty();
        assertThat(findFirstMultipleOf4.in(DoubleStream.of(NUMBERS))).isEmpty();
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void findAnyReturnsAnySatisfyingElement() {
        DoubleSearchFunction<OptionalDouble> findAnyMultipleOf4 = findAny(IS_MULTIPLE_OF_4);

        assertThat(findAnyMultipleOf4.in(NUMBERS).getAsDouble()).isIn(20., 40.);
        assertThat(findAnyMultipleOf4.in(DoubleStream.of(NUMBERS)).getAsDouble()).isIn(20., 40.);
    }

    @Test
    public void findAnyReturnsEmptyWhenThereAreNoSatisfyingElements() {
        DoubleSearchFunction<OptionalDouble> findAnyMultipleOf4 = findAny(IS_NEGATIVE);

        assertThat(findAnyMultipleOf4.in(NUMBERS)).isEmpty();
        assertThat(findAnyMultipleOf4.in(DoubleStream.of(NUMBERS))).isEmpty();
    }

    @Test
    public void minReturnsMinimumElement() {

        assertThat(minDouble().in(NUMBERS)).hasValue(10);
        assertThat(minDouble().in(DoubleStream.of(NUMBERS))).hasValue(10);
    }

    @Test
    public void maxReturnsMaximumElement() {

        assertThat(maxDouble().in(NUMBERS)).hasValue(50);
        assertThat(maxDouble().in(DoubleStream.of(NUMBERS))).hasValue(50);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(DoubleFilters.class)
                .isUtilityClass();
    }
}

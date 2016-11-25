package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.ExtendedArrays;
import com.github.moreaunicolas.util.stream.DoubleCounters.DoubleCountFunction;
import org.junit.Test;

import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;

import static com.github.moreaunicolas.util.stream.DoubleCounters.count;
import static org.assertj.core.api.Assertions.assertThat;

public class DoubleCountersTests {

    private static final double[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final DoublePredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final DoublePredicate IS_NEGATIVE = number -> number <= 0;

    @Test
    public void countReturnsNumberOfSatisfyingElement() {
        DoubleCountFunction countMultiplesOf4 = count(IS_MULTIPLE_OF_4);

        assertThat(countMultiplesOf4.in(NUMBERS)).isEqualTo(2);
        assertThat(countMultiplesOf4.in(DoubleStream.of(NUMBERS))).isEqualTo(2);
    }

    @Test
    public void countReturnsZeroWhenThereAreNoSatisfyingElements() {
        DoubleCountFunction countNegatives = count(IS_NEGATIVE);

        assertThat(countNegatives.in(NUMBERS)).isEqualTo(0);
        assertThat(countNegatives.in(DoubleStream.of(NUMBERS))).isEqualTo(0);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(DoubleCounters.class)
                .isUtilityClass();
    }
}

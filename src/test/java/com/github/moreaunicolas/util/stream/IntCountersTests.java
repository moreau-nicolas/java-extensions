package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.ExtendedArrays;
import com.github.moreaunicolas.util.stream.IntCounters.IntCountFunction;
import org.junit.Test;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.github.moreaunicolas.util.stream.IntCounters.count;
import static org.assertj.core.api.Assertions.assertThat;

public class IntCountersTests {

    private static final int[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final IntPredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final IntPredicate IS_NEGATIVE = number -> number <= 0;

    @Test
    public void countReturnsNumberOfSatisfyingElement() {
        IntCountFunction countMultiplesOf4 = count(IS_MULTIPLE_OF_4);

        assertThat(countMultiplesOf4.in(NUMBERS)).isEqualTo(2);
        assertThat(countMultiplesOf4.in(IntStream.of(NUMBERS))).isEqualTo(2);
    }

    @Test
    public void countReturnsZeroWhenThereAreNoSatisfyingElements() {
        IntCountFunction countNegatives = count(IS_NEGATIVE);

        assertThat(countNegatives.in(NUMBERS)).isEqualTo(0);
        assertThat(countNegatives.in(IntStream.of(NUMBERS))).isEqualTo(0);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(IntCounters.class)
                .isUtilityClass();
    }
}

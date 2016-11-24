package com.github.moreaunicolas.util.stream;

import com.github.moreaunicolas.util.stream.LongCounters.LongCountFunction;
import org.junit.Test;

import java.util.function.LongPredicate;
import java.util.stream.LongStream;

import static com.github.moreaunicolas.util.stream.LongCounters.count;
import static org.assertj.core.api.Assertions.assertThat;

public class LongCountersTests {

    private static final long[] NUMBERS = { 10, 20, 30, 40, 50 };

    private static final LongPredicate IS_MULTIPLE_OF_4 = number -> number % 4 == 0;
    private static final LongPredicate IS_NEGATIVE = number -> number <= 0;

    @Test
    public void countReturnsNumberOfSatisfyingElement() {
        LongCountFunction countMultiplesOf4 = count(IS_MULTIPLE_OF_4);

        assertThat(countMultiplesOf4.in(NUMBERS)).isEqualTo(2);
        assertThat(countMultiplesOf4.in(LongStream.of(NUMBERS))).isEqualTo(2);
    }

    @Test
    public void countReturnsZeroWhenThereAreNoSatisfyingElements() {
        LongCountFunction countNegatives = count(IS_NEGATIVE);

        assertThat(countNegatives.in(NUMBERS)).isEqualTo(0);
        assertThat(countNegatives.in(LongStream.of(NUMBERS))).isEqualTo(0);
    }
}

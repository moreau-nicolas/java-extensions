package com.github.moreaunicolas.util.stream;

import java.util.Arrays;

import com.github.moreaunicolas.test.Enumerations;
import com.github.moreaunicolas.test.UtilityClassAssert;
import com.github.moreaunicolas.util.stream.Reducers.ReduceToDoubleFunction;
import com.github.moreaunicolas.util.stream.Reducers.ReduceToIntFunction;
import com.github.moreaunicolas.util.stream.Reducers.ReduceToLongFunction;
import org.junit.Test;

import static com.github.moreaunicolas.util.stream.Reducers.average;
import static com.github.moreaunicolas.util.stream.Reducers.sum;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

public class ReducersTests {

    private enum Track {
        FIRST(245),
        SECOND(303),
        THIRD(147),
        FOURTH(289),
        FIFTH(251),
        SIXTH(249),
        SEVENTH(302),
        EIGHTH(198);

        private final int length;

        Track(int lengthInSeconds) {
            this.length = lengthInSeconds;
        }

        public int lengthAsInt() {
            return length;
        }

        public long lengthAsLong() {
            return length;
        }

        public double lengthAsDouble() {
            return length;
        }
    }

    private static final Track[] TRACKS = Track.values();
    private static final Double AVERAGE_LENGTH = 248.;
    private static final Double TOTAL_LENGTH = 1984.;

    @Test
    public void sumOfIntTransformerReturnsTotalAsInt() {
        ReduceToIntFunction<Track> totalLength = sum(Track::lengthAsInt);

        int expectedTotal = TOTAL_LENGTH.intValue();
        assertThat(totalLength.in(TRACKS)).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Arrays.asList(TRACKS))).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Arrays.asList(TRACKS).iterator())).isEqualTo(expectedTotal);
        assertThat(totalLength.in(stream(TRACKS))).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Enumerations.forArray(TRACKS))).isEqualTo(expectedTotal);
    }

    @Test
    public void sumOfLongTransformerReturnsTotalAsLong() {
        ReduceToLongFunction<Track> totalLength = sum(Track::lengthAsLong);

        long expectedTotal = TOTAL_LENGTH.longValue();
        assertThat(totalLength.in(TRACKS)).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Arrays.asList(TRACKS))).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Arrays.asList(TRACKS).iterator())).isEqualTo(expectedTotal);
        assertThat(totalLength.in(stream(TRACKS))).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Enumerations.forArray(TRACKS))).isEqualTo(expectedTotal);
    }

    @Test
    public void sumOfDoubleTransformerReturnsTotalAsDouble() {
        ReduceToDoubleFunction<Track> totalLength = sum(Track::lengthAsDouble);

        double expectedTotal = TOTAL_LENGTH;
        assertThat(totalLength.in(TRACKS)).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Arrays.asList(TRACKS))).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Arrays.asList(TRACKS).iterator())).isEqualTo(expectedTotal);
        assertThat(totalLength.in(stream(TRACKS))).isEqualTo(expectedTotal);
        assertThat(totalLength.in(Enumerations.forArray(TRACKS))).isEqualTo(expectedTotal);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void averageOfIntTransformerReturnsAverageAsDouble() {
        ReduceToDoubleFunction<Track> averageLength = average(Track::lengthAsInt);

        assertThat(averageLength.in(TRACKS)).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Arrays.asList(TRACKS))).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Arrays.asList(TRACKS).iterator())).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(stream(TRACKS))).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Enumerations.forArray(TRACKS))).isEqualTo(AVERAGE_LENGTH);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void averageOfLongTransformerReturnsAverageAsDouble() {
        ReduceToDoubleFunction<Track> averageLength = average(Track::lengthAsLong);

        assertThat(averageLength.in(TRACKS)).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Arrays.asList(TRACKS))).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Arrays.asList(TRACKS).iterator())).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(stream(TRACKS))).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Enumerations.forArray(TRACKS))).isEqualTo(AVERAGE_LENGTH);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void averageOfDoubleTransformerReturnsAverageAsDouble() {
        ReduceToDoubleFunction<Track> averageLength = average(Track::lengthAsDouble);

        assertThat(averageLength.in(TRACKS)).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Arrays.asList(TRACKS))).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Arrays.asList(TRACKS).iterator())).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(stream(TRACKS))).isEqualTo(AVERAGE_LENGTH);
        assertThat(averageLength.in(Enumerations.forArray(TRACKS))).isEqualTo(AVERAGE_LENGTH);
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(Reducers.class)
                .isUtilityClass();
    }
}

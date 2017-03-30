package com.github.moreaunicolas.util;

import com.github.moreaunicolas.test.UtilityClassAssert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class ExtendedCollectorsTests {

    private static final Map.Entry<String, String> ENTRY_1 = entry("key1", "value1");
    private static final Map.Entry<String, String> ENTRY_2 = entry("key2", "value2");
    private static final Map.Entry<String, String> ENTRY_2_DUPLICATE = entry("key2", "duplicate");

    private Map<String, String> actual;

    @Test
    public void toMap_withoutArgument() {
        actual = Stream.of(ENTRY_1, ENTRY_2)
                .collect(ExtendedCollectors.toMap());

        assertThat(actual)
                .containsOnly(ENTRY_1, ENTRY_2)
                ;
    }

    @Test
    public void toMap_withoutArgument_withDuplicates() {
        @SuppressWarnings("ResultOfMethodCallIgnored") // collector will throw
        Throwable caughtThrowable = catchThrowable(() -> {
            Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                    .collect(ExtendedCollectors.toMap());
        });

        assertThat(caughtThrowable)
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Duplicate key %s", "value2")
                ;
    }

    @Test
    public void toMap_withSupplier() {
        actual = Stream.of(ENTRY_1)
                .collect(ExtendedCollectors.toMap(TreeMap::new));

        assertThat(actual)
                .isExactlyInstanceOf(TreeMap.class)
                .containsOnly(ENTRY_1)
                ;
    }

    @Test
    public void toMap_withSupplier_withDuplicates() {
        @SuppressWarnings("ResultOfMethodCallIgnored") // collector will throw
        Throwable caughtThrowable = catchThrowable(() -> {
            Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                    .collect(ExtendedCollectors.toMap(TreeMap::new));
        });

        assertThat(caughtThrowable)
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Duplicate key %s", "value2")
                ;
    }

    @Test
    public void toMap_withMergeFunction() {
        BinaryOperator<String> useFirstValue = (oldest, newest) -> oldest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toMap(useFirstValue));

        assertThat(actual)
                .containsOnly(ENTRY_2)
                ;
    }

    @Test
    public void toMap_withMergeFunctionAndSupplier() {
        BinaryOperator<String> useFirstValue = (oldest, newest) -> oldest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toMap(useFirstValue, TreeMap::new));

        assertThat(actual)
                .isExactlyInstanceOf(TreeMap.class)
                .containsOnly(ENTRY_2)
                ;
    }

    @Test
    public void toConcurrentMap_withoutArgument() {
        actual = Stream.of(ENTRY_1, ENTRY_2)
                .collect(ExtendedCollectors.toConcurrentMap());

        assertThat(actual)
                .isInstanceOf(ConcurrentMap.class)
                .containsOnly(ENTRY_1, ENTRY_2)
                ;
    }

    @Test
    public void toConcurrentMap_withoutArgument_withDuplicates() {
        @SuppressWarnings("ResultOfMethodCallIgnored") // collector will throw
        Throwable caughtThrowable = catchThrowable(() -> {
            Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                    .collect(ExtendedCollectors.toConcurrentMap());
        });

        assertThat(caughtThrowable)
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Duplicate key %s", "value2")
                ;
    }

    @Test
    public void toConcurrentMap_withSupplier() {
        actual = Stream.of(ENTRY_1)
                .collect(ExtendedCollectors.toConcurrentMap(ConcurrentSkipListMap::new));

        assertThat(actual)
                .isExactlyInstanceOf(ConcurrentSkipListMap.class)
                .containsOnly(ENTRY_1)
                ;
    }

    @Test
    public void toConcurrentMap_withSupplier_withDuplicates() {
        @SuppressWarnings("ResultOfMethodCallIgnored") // collector will throw
        Throwable caughtThrowable = catchThrowable(() -> {
            Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                    .collect(ExtendedCollectors.toConcurrentMap(ConcurrentSkipListMap::new));
        });

        assertThat(caughtThrowable)
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Duplicate key %s", "value2")
                ;
    }

    @Test
    public void toConcurrentMap_withMergeFunction() {
        BinaryOperator<String> useLastValue = (oldest, newest) -> newest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toConcurrentMap(useLastValue));

        assertThat(actual)
                .isInstanceOf(ConcurrentMap.class)
                .containsOnly(ENTRY_2_DUPLICATE)
                ;
    }

    @Test
    public void toConcurrentMap_withMergeFunctionAndSupplier() {
        BinaryOperator<String> useLastValue = (oldest, newest) -> newest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toConcurrentMap(useLastValue, ConcurrentSkipListMap::new));

        assertThat(actual)
                .isExactlyInstanceOf(ConcurrentSkipListMap.class)
                .containsOnly(ENTRY_2_DUPLICATE)
                ;
    }

    @Test
    public void joining() {
        Stream<Integer> numbers = Stream.of(0, 1, 2, 3, 4, 5, 6, 7 , 8, 9);

        String actual = numbers
                .collect(ExtendedCollectors.joining());

        assertThat(actual)
                .isEqualTo("0123456789");
    }

    @Test
    public void joining_null() {
        Stream<Integer> numbers = Stream.of(0, 1, 2, null, 4, 5);

        String actual = numbers
                .collect(ExtendedCollectors.joining());

        assertThat(actual)
                .isEqualTo("012null45");
    }

    @Test
    public void joining_withDelimiter() {
        Stream<Integer> fibonacciNumbers = Stream.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55);

        String actual = fibonacciNumbers
                .collect(ExtendedCollectors.joining(", "));

        assertThat(actual)
                .isEqualTo("0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55");
    }

    @Test
    public void joining_withDelimiterAndPrefixAndSuffix() {
        Stream<Integer> empty = Stream.empty();

        String actual = empty
                .collect(ExtendedCollectors.joining("not important", "[", "...]"));

        assertThat(actual)
                .isEqualTo("[...]");
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(ExtendedCollectors.class)
                .isUtilityClass();
    }
}

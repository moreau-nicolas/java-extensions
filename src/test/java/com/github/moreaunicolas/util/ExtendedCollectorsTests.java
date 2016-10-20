package com.github.moreaunicolas.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.entry;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import org.junit.Test;

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
        final Throwable caughtThrowable = catchThrowable(() -> {
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
        final Throwable caughtThrowable = catchThrowable(() -> {
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
        final BinaryOperator<String> useFirstValue = (oldest, newest) -> oldest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toMap(useFirstValue));

        assertThat(actual)
                .containsOnly(ENTRY_2)
                ;
    }

    @Test
    public void toMap_withMergeFunctionAndSupplier() {
        final BinaryOperator<String> useFirstValue = (oldest, newest) -> oldest;

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
        final Throwable caughtThrowable = catchThrowable(() -> {
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
        final Throwable caughtThrowable = catchThrowable(() -> {
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
        final BinaryOperator<String> useLastValue = (oldest, newest) -> newest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toConcurrentMap(useLastValue));

        assertThat(actual)
                .isInstanceOf(ConcurrentMap.class)
                .containsOnly(ENTRY_2_DUPLICATE)
                ;
    }

    @Test
    public void toConcurrentMap_withMergeFunctionAndSupplier() {
        final BinaryOperator<String> useLastValue = (oldest, newest) -> newest;

        actual = Stream.of(ENTRY_2, ENTRY_2_DUPLICATE)
                .collect(ExtendedCollectors.toConcurrentMap(useLastValue, ConcurrentSkipListMap::new));

        assertThat(actual)
                .isExactlyInstanceOf(ConcurrentSkipListMap.class)
                .containsOnly(ENTRY_2_DUPLICATE)
                ;
    }
}

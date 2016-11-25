package com.github.moreaunicolas.util;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.github.moreaunicolas.test.UtilityClassAssert;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;

public class ExtendedPredicatesTests {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void notNegatesItsArgument_Predicate() {
         Predicate<Boolean> not = ExtendedPredicates.not(b -> b);

        softly.assertThat(not.test(TRUE)).isFalse();
        softly.assertThat(not.test(FALSE)).isTrue();
    }

    @Test
    public void notNegatesItsArgument_BiPredicate() {
        BiPredicate<Boolean, Boolean> nand = ExtendedPredicates.not(Boolean::logicalAnd);

        softly.assertThat(nand.test(TRUE, TRUE)).isFalse();
        softly.assertThat(nand.test(TRUE, FALSE)).isTrue();
        softly.assertThat(nand.test(FALSE, TRUE)).isTrue();
        softly.assertThat(nand.test(FALSE, FALSE)).isTrue();
    }

    @Test
    public void orCombinesItsArguments_Predicate() {
        Predicate<String> predicate = ExtendedPredicates.or(
                s -> s.startsWith("4"),
                s -> s.contains("3"),
                s -> s.endsWith("2")
        );

        softly.assertThat(predicate.test("")).isFalse();
        softly.assertThat(predicate.test("2")).isTrue();
        softly.assertThat(predicate.test("4")).isTrue();
        softly.assertThat(predicate.test("42")).isTrue();
        softly.assertThat(predicate.test("432")).isTrue();
    }

    @Test
    public void orShortCircuits_Predicate() {
        Predicate<?> shouldNeverBeCalled = mock(Predicate.class);
        Predicate<?> predicate = ExtendedPredicates.or(
                s -> false,
                s -> true,
                shouldNeverBeCalled
        );

        predicate.test(null);

        verify(shouldNeverBeCalled, never()).test(eq(null));
    }

    @Test
    public void andCombinesItsArguments_Predicate() {
        Predicate<String> predicate = ExtendedPredicates.and(
                s -> s.startsWith("4"),
                s -> s.contains("3"),
                s -> s.endsWith("2")
        );

        softly.assertThat(predicate.test("")).isFalse();
        softly.assertThat(predicate.test("2")).isFalse();
        softly.assertThat(predicate.test("4")).isFalse();
        softly.assertThat(predicate.test("42")).isFalse();
        softly.assertThat(predicate.test("432")).isTrue();
    }

    @Test
    public void andShortCircuits_Predicate() {
        Predicate<?> shouldNeverBeCalled = mock(Predicate.class);
        Predicate<?> predicate = ExtendedPredicates.and(
                s -> true,
                s -> false,
                shouldNeverBeCalled
        );

        predicate.test(null);

        verify(shouldNeverBeCalled, never()).test(eq(null));
    }

    @Test
    public void xorCombinesItsArguments_Predicate() {
        Predicate<String> predicate = ExtendedPredicates.xor(
                s -> s.startsWith("4"),
                s -> s.endsWith("2")
        );

        softly.assertThat(predicate.test("")).isFalse();
        softly.assertThat(predicate.test("2")).isTrue();
        softly.assertThat(predicate.test("4")).isTrue();
        softly.assertThat(predicate.test("42")).isFalse();
    }


    @Test
    public void xorEvaluatesAllItsArguments_Predicate() {
        @SuppressWarnings("unchecked")
        Predicate<String> first = mock(Predicate.class);
        @SuppressWarnings("unchecked")
        Predicate<String> second = mock(Predicate.class);
        @SuppressWarnings("unchecked")
        Predicate<String> third = mock(Predicate.class);
        Predicate<String> predicate = ExtendedPredicates.xor(
                first,
                second,
                third
        );

        predicate.test(null);

        verify(first).test(eq(null));
        verify(second).test(eq(null));
        verify(third).test(eq(null));
    }

    @Test
    public void orCombinesItsArguments_BiPredicate() {
        BiPredicate<String, Integer> predicate = ExtendedPredicates.or(
                (a, b) -> a.startsWith("4"),
                (a, b) -> a.contains("3"),
                (a, b) -> a.endsWith("2")
        );

        softly.assertThat(predicate.test("", 42)).isFalse();
        softly.assertThat(predicate.test("2", 42)).isTrue();
        softly.assertThat(predicate.test("4", 42)).isTrue();
        softly.assertThat(predicate.test("42", 42)).isTrue();
        softly.assertThat(predicate.test("432", 42)).isTrue();
    }

    @Test
    public void orShortCircuits_BiPredicate() {
        BiPredicate<?,?> shouldNeverBeCalled = mock(BiPredicate.class);
        BiPredicate<?,?> predicate = ExtendedPredicates.or(
                (a, b) -> false,
                (a, b) -> true,
                shouldNeverBeCalled
        );

        predicate.test(null, null);

        verify(shouldNeverBeCalled, never()).test(eq(null), eq(null));
    }

    @Test
    public void andCombinesItsArguments_BiPredicate() {
        BiPredicate<String, Integer> predicate = ExtendedPredicates.and(
                (a, b) -> a.startsWith("4"),
                (a, b) -> a.contains("3"),
                (a, b) -> a.endsWith("2")
        );

        softly.assertThat(predicate.test("", 42)).isFalse();
        softly.assertThat(predicate.test("2", 42)).isFalse();
        softly.assertThat(predicate.test("4", 42)).isFalse();
        softly.assertThat(predicate.test("42", 42)).isFalse();
        softly.assertThat(predicate.test("432", 42)).isTrue();
    }

    @Test
    public void andShortCircuits_BiPredicate() {
        BiPredicate<?,?> shouldNeverBeCalled = mock(BiPredicate.class);
        BiPredicate<?,?> predicate = ExtendedPredicates.and(
                (a, b) -> true,
                (a, b) -> false,
                shouldNeverBeCalled
        );

        predicate.test(null, null);

        verify(shouldNeverBeCalled, never()).test(eq(null), eq(null));
    }

    @Test
    public void xorCombinesItsArguments_BiPredicate() {
        BiPredicate<String, Integer> predicate = ExtendedPredicates.xor(
                (a, b) -> a.startsWith("4"),
                (a, b) -> a.endsWith("2")
        );

        softly.assertThat(predicate.test("", 42)).isFalse();
        softly.assertThat(predicate.test("2", 42)).isTrue();
        softly.assertThat(predicate.test("4", 42)).isTrue();
        softly.assertThat(predicate.test("42", 42)).isFalse();
    }


    @Test
    public void xorEvaluatesAllItsArguments_BiPredicate() {
        @SuppressWarnings("unchecked")
        BiPredicate<String, Integer> first = mock(BiPredicate.class);
        @SuppressWarnings("unchecked")
        BiPredicate<String, Integer> second = mock(BiPredicate.class);
        @SuppressWarnings("unchecked")
        BiPredicate<String, Integer> third = mock(BiPredicate.class);
        BiPredicate<?,?> predicate = ExtendedPredicates.xor(
                first,
                second,
                third
        );

        predicate.test(null, null);

        verify(first).test(eq(null), eq(null));
        verify(second).test(eq(null), eq(null));
        verify(third).test(eq(null), eq(null));
    }

    @Test
    public void isUtilityClass() throws NoSuchMethodException {
        UtilityClassAssert.assertThat(ExtendedPredicates.class)
                .isUtilityClass();
    }
}

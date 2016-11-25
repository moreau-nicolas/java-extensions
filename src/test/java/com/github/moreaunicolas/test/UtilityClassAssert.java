package com.github.moreaunicolas.test;

import com.github.moreaunicolas.util.ExtendedCollectors;
import org.assertj.core.api.AbstractClassAssert;
import org.assertj.core.api.Assertions;

public class UtilityClassAssert extends AbstractClassAssert<UtilityClassAssert> {

    public static UtilityClassAssert assertThat(Class<?> actual) {

        return new UtilityClassAssert(actual);
    }

    private UtilityClassAssert(Class<?> actual) {
        super(actual, UtilityClassAssert.class);
    }

    public UtilityClassAssert isUtilityClass() {
        assertThat(ExtendedCollectors.class).isFinal();
        Assertions.assertThat(actual.getDeclaredConstructors()).hasSize(1);
        try {
            ConstructorAssert.assertThat(actual.getDeclaredConstructor())
                    .isPrivate()
                    .isThrowing();
        } catch (NoSuchMethodException e) {
            failWithMessage("no default constructor found in %s", actual.getName());
        }
        return this;
    }
}

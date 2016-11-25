package com.github.moreaunicolas.test;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ConstructorAssert extends AbstractAssert<ConstructorAssert, Constructor> {

    public static ConstructorAssert assertThat(Constructor<?> actual) {
        return new ConstructorAssert(actual);
    }

    private ConstructorAssert(Constructor<?> actual) {
        super(actual, ConstructorAssert.class);
        actual.setAccessible(true);
    }

    public ConstructorAssert isPublic() {
        Assertions.assertThat(actual.getModifiers() & Modifier.PUBLIC)
                .withFailMessage("default constructor should be public")
                .isEqualTo(Modifier.PUBLIC);
        return this;
    }

    public ConstructorAssert isProtected() {
        Assertions.assertThat(actual.getModifiers() & Modifier.PROTECTED)
                .withFailMessage("default constructor should be protected")
                .isEqualTo(Modifier.PROTECTED);
        return this;
    }

    public ConstructorAssert isPrivate() {
        Assertions.assertThat(actual.getModifiers() & Modifier.PRIVATE)
                .withFailMessage("default constructor should be private")
                .isEqualTo(Modifier.PRIVATE);
        return this;
    }

    public ConstructorAssert isThrowing() {
        return isThrowingOneOf(Throwable.class);
    }

    @SafeVarargs
    public final ConstructorAssert isThrowingOneOf(Class<? extends Throwable>... expected) {
        Throwable caught = Assertions.catchThrowable(((Constructor<?>) actual)::newInstance);
        Assertions.assertThat(caught).isInstanceOfAny(expected);
        return this;
    }
}

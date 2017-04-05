# java-extensions

[![Build Status][build-status-icon]][build-status-url]
[![Coverage Status][coverage-status-icon]][coverage-status-url]

A collection of helpful utility classes for Java 8 and above.

## Features

- Smoother `Predicate` and `BiPredicate` interaction with method references
- Simpler `Set` creation
- Safe and lambda-friendly `Exception` wrapping
- Reduce boilerplate
- Uniform intention-revealing interface to manipulate non-scalar values
 (array, `Stream`, `Iterable`, `Iterator`, `Enumeration`)


## Motivation & examples

### 1. Smoother `Predicate` and `BiPredicate` interaction with method references

#### 1.1 Negation

Java 8 introduced the `Stream` API, which enables a developer to filter a stream of values with the following syntax:

```java
import java.util.stream.Stream;

public class PredicateExample {
    public static void main(String... args){
        Stream.of(args)
            .filter(value -> value.length() > 0)
            .forEach(System.out::println);
    }
}
```

This is a nice API but it does not always play well with another Java 8 feature: **method references**.
 
In the above example the developer wanted to print only the non-empty arguments on the standard output. In order to do
so she used a lambda expression: `value -> value.length() > 0`. It gets the job done but I find it lacks
**expressiveness**: the intention is not as clear as it could be and requires **mental mapping**: a value is *not empty*
if and only if *its length is strictly greater than 0*. This is a small effort, but one that can be avoided.

The `String` class provides an `isEmpty()` method that does express the *opposite* of the intention we'd like to
express. It would be nice if we could write `String::isEmpty.negate()`. Unfortunately we can't, the compiler won't let
us, lest we add a *cast*: `((Predicate<String>) String::isEmpty).negate()`. This is even worse than our initial
lambda in terms of expressiveness.

Introducing a local variable would work too:

```java
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PredicateNegationWithLocalVariable {
    public static void main(String... args){
        Predicate<String> isEmpty = String::isEmpty;
        Stream.of(args)
            .filter(isEmpty.negate())
            .forEach(System.out::println);
    }
}
```

I find that the local variable introduces quite a bit of duplication, which I'm not fond of.

It turns out we can do better by using **type inference** to cast our method reference as a `Predicate` and negate it :

```java
import java.util.stream.Stream;

public class PredicateNegationWithHelperMethod {
    
    private static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
    
    public static void main(String... args){
        Stream.of(args)
            .filter(not(String::isEmpty))
            .forEach(System.out::println);
    }
}
```

The `ExtendedPredicates` class provides such a `not()` method for both `Predicate` and `BiPredicate` variants:

```java
import java.util.stream.Stream;

import static com.github.moreaunicolas.util.ExtendedPredicates.not;

public class PredicateNegationWithStaticImport {
    public static void main(String... args){
        Stream.of(args)
            .filter(not(String::isEmpty))
            .forEach(System.out::println);
    }
}
```

#### 1.2 Boolean operations

The `ExtendedPredicates` class also supports common boolean operations for both `Predicate` and `BiPredicate`:
- `and()`
- `or()`
- `xor()`

```java
import java.util.stream.Stream;
import java.util.Objects;

import static com.github.moreaunicolas.util.ExtendedPredicates.not;
import static com.github.moreaunicolas.util.ExtendedPredicates.and;
import static com.github.moreaunicolas.util.ExtendedPredicates.or;
import static com.github.moreaunicolas.util.ExtendedPredicates.xor;

public class PredicateExample {
    public static void main(String... args){
        Stream.of(args)
            .filter(and(Objects::nonNull, not(String::isEmpty)))
            .filter(or(s -> s.startsWith("-"), s -> s.startsWith("+")))
            .forEach(System.out::println);
    }
}
```

> Note: the `and()` and `or()` methods return predicates with the **short-circuit** behaviour.

> Note: The predicate returned by the `xor()` method will only evaluate to `true`
 if and only if **exactly** one of its arguments evaluates to `true`.


### 2. Simpler `Set` creation

The JDK provides the `Arrays.asList()` method. There is however no `Set` equivalent.

The library provides an `ExtendedArrays.asSet()` method which enables a developer to create a set from an array.

```java
import com.github.moreaunicolas.util.ExtendedArrays;

public class SetCreationExample {
    public static void main(String... args){
        ExtendedArrays.asSet(args)
            .forEach(System.out::println);
    }
}
```

### 3. Safe and lambda-friendly `Exception` wrapping

Lambdas don't play well with checked exceptions. Functional interfaces usually don't declare any checked exceptions
in the method signature, which prevent their use with references to otherwise-conforming methods.

This library builds on the foundation laid by Yohan Legat's [uncheck](https://github.com/ylegat/uncheck) library and
provides exception-wrappers to often-used functional interfaces in the `java.util.function` package.

```java
import java.io.File;
import java.io.IOException;

public class ExceptionWrappingExample {
    
    private static boolean isValidFile(File file) throws IOException {
        // ...
    }
    
    public static void main(String... args){
        Stream.of(args)
            .map(File::new)
            .filter(ExceptionWrappingExample::isValidFile) // Does not compile
            .map(File::getName)
            .forEach(System.out::println);
    }
}
```

With an exception wrapper:

```java
import com.github.moreaunicolas.util.function.UncheckedPredicate;
import java.io.File;
import java.io.IOException;

public class ExceptionWrappingExample {
    
    private static boolean isValidFile(File file) throws IOException {
        // ...
    }
    
    public static void main(String... args){
        Stream.of(args)
            .map(File::new)
            .filter(UncheckedPredicate.from(ExceptionWrappingExample::isValidFile)) // OK
            .map(File::getName)
            .forEach(System.out::println);
    }
}
```

### 4. Reduce boilerplate
 
#### 4.1. Creating `Stream` instances

The `StreamSupport` class provides a few methods to help create `Stream` instances from `Iterator`s and `Spliterator`s.

The library provides an `ExtendedStreamSupport` class which adds a few other methods with the same intent and fewer
parameters:
- `Stream<T> stream(Iterable<T> objects)`
- `Stream<T> stream(Iterator<T> iterator)`
- `Stream<T> stream(Enumeration<T> enumeration)`

Creating a primitive type stream from a primitive iterator is also supported:
- `DoubleStream doubleStream(PrimitiveIterator.OfDouble iterator)`
- `LongStream longStream(PrimitiveIterator.OfLong iterator)`
- `IntStream intStream(PrimitiveIterator.OfInt iterator)`

#### 4.2. Creating `Collector` instances

The `Collectors` class provides a few methods to help create `Collector` instances.

The library provides an `ExtendedCollectors` class which adds a few other methods with the same intent and fewer
parameters:
- `Collector<...> toMap()`
- `Collector<...> toMap(BinaryOperator<V> mergeFunction)`
- `Collector<...> toMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier)`
- `Collector<...> toMap(Supplier<M> mapSupplier)`
- `Collector<...> toConcurrentMap()`
- `Collector<...> toConcurrentMap(BinaryOperator<V> mergeFunction)`
- `Collector<...> toConcurrentMap(BinaryOperator<V> mergeFunction, Supplier<M> mapSupplier)`
- `Collector<...> toConcurrentMap(Supplier<M> mapSupplier)`
- `Collector<...> joining()`
- `Collector<...> joining(String delimiter)`
- `Collector<...> joining(String delimiter, String prefix, String suffix)`

The `toMap()` and `toConcurrentMap()` methods use `Map.Entry::getKey` and `Map.Entry::getValue` as mapping functions.
The `joining()` method enables one to join the string representations of objects in a `Stream` without explicitly making
a call to `.map(Object::toString)` first.

### 5. Uniform intention-revealing interface to manipulate non-scalar values

The Java 8 Stream API is really powerful and I really like its fluent interface. It sometimes is a bit verbose though.

```java
import java.util.stream.Stream;

public class StreamExample {
    public static void main(String... args){
        Stream.of(args)
            .filter(not(String::isEmpty))
            .findFirst()
            .ifPresent(System.out::println);
    }
}
```

Another caveat is that it introduces yet another way of searching, filtering, counting, in a word manipulating a group
of objects. Both `Stream` and `Enumeration` do not implement `Iterable`. Arrays don't either.

`Stream`s are better at expressing the *what* than a `for`-loop, which focuses more on the *how*.

I wanted to see if it was possible to combine several languages features in Java to create a uniform interface to
manipulating non-scalar values in a way that maximizes **expressiveness**.

It turns out it **is** possible:

```java
import com.github.moreaunicolas.test.Enumerations;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.moreaunicolas.util.stream.Counters.count;
import static com.github.moreaunicolas.util.stream.ExtendedStreamSupport.stream;
import static com.github.moreaunicolas.util.stream.Filters.*;
import static com.github.moreaunicolas.util.stream.Matchers.*;
import static com.github.moreaunicolas.util.stream.Reducers.average;
import static com.github.moreaunicolas.util.stream.Reducers.sum;
import static java.util.Comparator.comparing;

public class UniformInterfaceExamples {

    @SuppressWarnings({"UnusedAssignment", "unused"})
    public static void main(String... args) {
        Predicate<String> startsWithA = movie -> movie.startsWith("a");
        String[] movieArray = {"a beautiful mind", "american history X", "american psycho", "gladiator", "titanic" };
        Iterable<String> movieIterable = Arrays.asList(movieArray);

        Optional<String> firstMovieStartingWithA;
        firstMovieStartingWithA = findFirst(startsWithA).in(movieArray);
        firstMovieStartingWithA = findFirst(startsWithA).in(movieIterable);
        firstMovieStartingWithA = findFirst(startsWithA).in(movieIterable.iterator());
        firstMovieStartingWithA = findFirst(startsWithA).in(stream(movieIterable));
        firstMovieStartingWithA = findFirst(startsWithA).in(Enumerations.forArray(movieArray));

        Optional<String> anyMovieStartingWithA;
        anyMovieStartingWithA = findAny(startsWithA).in(movieArray);
        anyMovieStartingWithA = findAny(startsWithA).in(movieIterable);
        anyMovieStartingWithA = findAny(startsWithA).in(movieIterable.iterator());
        anyMovieStartingWithA = findAny(startsWithA).in(stream(movieIterable));
        anyMovieStartingWithA = findAny(startsWithA).in(Enumerations.forArray(movieArray));

        Optional<String> shortestMovie;
        shortestMovie = min(comparing(String::length)).in(movieArray);
        shortestMovie = min(comparing(String::length)).in(movieIterable);
        shortestMovie = min(comparing(String::length)).in(movieIterable.iterator());
        shortestMovie = min(comparing(String::length)).in(stream(movieIterable));
        shortestMovie = min(comparing(String::length)).in(Enumerations.forArray(movieArray));

        Optional<String> longestMovie;
        longestMovie = max(comparing(String::length)).in(movieArray);
        longestMovie = max(comparing(String::length)).in(movieIterable);
        longestMovie = max(comparing(String::length)).in(movieIterable.iterator());
        longestMovie = max(comparing(String::length)).in(stream(movieIterable));
        longestMovie = max(comparing(String::length)).in(Enumerations.forArray(movieArray));

        Stream<String> moviesStartingWithA;
        moviesStartingWithA = findAll(startsWithA).in(movieArray);
        moviesStartingWithA = findAll(startsWithA).in(movieIterable);
        moviesStartingWithA = findAll(startsWithA).in(movieIterable.iterator());
        moviesStartingWithA = findAll(startsWithA).in(stream(movieIterable));
        moviesStartingWithA = findAll(startsWithA).in(Enumerations.forArray(movieArray));

        boolean allMoviesStartWithA;
        allMoviesStartWithA = allMatch(startsWithA).in(movieArray);
        allMoviesStartWithA = allMatch(startsWithA).in(movieIterable);
        allMoviesStartWithA = allMatch(startsWithA).in(movieIterable.iterator());
        allMoviesStartWithA = allMatch(startsWithA).in(stream(movieIterable));
        allMoviesStartWithA = allMatch(startsWithA).in(Enumerations.forArray(movieArray));

        boolean anyMovieStartsWithA;
        anyMovieStartsWithA = anyMatch(startsWithA).in(movieArray);
        anyMovieStartsWithA = anyMatch(startsWithA).in(movieIterable);
        anyMovieStartsWithA = anyMatch(startsWithA).in(movieIterable.iterator());
        anyMovieStartsWithA = anyMatch(startsWithA).in(stream(movieIterable));
        anyMovieStartsWithA = anyMatch(startsWithA).in(Enumerations.forArray(movieArray));

        boolean noMoviesStartWithA;
        noMoviesStartWithA = noneMatch(startsWithA).in(movieArray);
        noMoviesStartWithA = noneMatch(startsWithA).in(movieIterable);
        noMoviesStartWithA = noneMatch(startsWithA).in(movieIterable.iterator());
        noMoviesStartWithA = noneMatch(startsWithA).in(stream(movieIterable));
        noMoviesStartWithA = noneMatch(startsWithA).in(Enumerations.forArray(movieArray));

        long numberOfMoviesStartingWithA;
        numberOfMoviesStartingWithA = count(startsWithA).in(movieArray);
        numberOfMoviesStartingWithA = count(startsWithA).in(movieIterable);
        numberOfMoviesStartingWithA = count(startsWithA).in(movieIterable.iterator());
        numberOfMoviesStartingWithA = count(startsWithA).in(stream(movieIterable));
        numberOfMoviesStartingWithA = count(startsWithA).in(Enumerations.forArray(movieArray));

        int sumOfMovieLengths;
        sumOfMovieLengths = sum(String::length).in(movieArray);
        sumOfMovieLengths = sum(String::length).in(movieIterable);
        sumOfMovieLengths = sum(String::length).in(movieIterable.iterator());
        sumOfMovieLengths = sum(String::length).in(stream(movieIterable));
        sumOfMovieLengths = sum(String::length).in(Enumerations.forArray(movieArray));
        
        double averageOfMovieLengths;
        averageOfMovieLengths = average(String::length).in(movieArray);
        averageOfMovieLengths = average(String::length).in(movieIterable);
        averageOfMovieLengths = average(String::length).in(movieIterable.iterator());
        averageOfMovieLengths = average(String::length).in(stream(movieIterable));
        averageOfMovieLengths = average(String::length).in(Enumerations.forArray(movieArray));
    }
}
```

> Note: because of some Java generics limitations, it might be necessary to store the
 filtering / counting / matching `Predicate` in a **local variable** (or perform a *cast*).
 Storing the predicate in a variable isn't necessarily a bad thing because it can help better express the developer's
 intent by giving a name to the `Predicate`.

> Example: if `message` is of type `String`, `message::equals` is of type `Predicate<Object>` and
 not of type `Predicate<String>`. This is because the signature of the `equals()` method is:
 `boolean equals(Object other)` and not `boolean equals(String other)`. We can still write
 `Predicate<String> isEqualToMessage = message::equals` though, but the conversion must be explicit.

> Some `Collection` methods also use `Object` as parameter type and require explicit `Predicate` conversion
 for the same reasons.

The library provides facilities to deal with primitive streams and iterators in the same way as the example above.
Please refer to the unit tests for more concrete examples.

## Dependencies

Production code depends only on JDK 8 or above.

Test code uses JUnit, AssertJ and Guava.

[build-status-icon]: https://travis-ci.org/moreau-nicolas/java-extensions.svg?branch=master
[build-status-url]: https://travis-ci.org/moreau-nicolas/java-extensions

[coverage-status-icon]: https://coveralls.io/repos/github/moreau-nicolas/java-extensions/badge.svg?branch=master
[coverage-status-url]: https://coveralls.io/github/moreau-nicolas/java-extensions?branch=master
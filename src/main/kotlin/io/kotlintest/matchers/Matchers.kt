package io.kotlintest.matchers

import io.kotlintest.Inspectors

interface Keyword

abstract class Matchers : StringMatchers,
    CollectionMatchers,
    DoubleMatchers,
    IntMatchers,
    LongMatchers,
    FileMatchers,
    MapMatchers,
    TypeMatchers,
    Inspectors {

  protected val builders = mutableListOf<Assertion<out Any>>()

  fun <T> equalityMatcher(expected: T?) = object : Matcher<T?> {
    override fun test(value: T?): Result {
      if (value == null && expected != null)
        return Result(false, "$expected should equal $value")
      if (value != null && expected == null)
        return Result(false, value.toString() + "$expected should equal $value")
      if (value != expected)
        return Result(false, value.toString() + "$expected should equal $value")
      else
        return Result(true, "$expected should equal $value")
    }
  }

  fun fail(msg: String): Nothing = throw AssertionError(msg)

  infix fun Double.shouldBe(other: Double): Assertion<Double> = should(ToleranceMatcher(other, 0.0))

  infix fun String.shouldBe(other: String) {
    if (this != other) {
      var msg = "String $this should be equal to $other"
      for (k in 0..Math.min(this.length, other.length) - 1) {
        if (this[k] != other[k]) {
          msg = "$msg (diverged at index $k)"
          break
        }
      }
      throw AssertionError(msg)
    }
  }

  infix fun BooleanArray.shouldBe(other: BooleanArray): Unit {
    if (this.toList() != other.toList())
      throw AssertionError("Array not equal: $this != $other")
  }

  infix fun IntArray.shouldBe(other: IntArray): Unit {
    if (this.toList() != other.toList())
      throw AssertionError("Array not equal: $this != $other")
  }

  infix fun DoubleArray.shouldBe(other: DoubleArray): Unit {
    if (this.toList() != other.toList())
      throw AssertionError("Array not equal: $this != $other")
  }

  infix fun LongArray.shouldBe(other: LongArray): Unit {
    if (this.toList() != other.toList())
      throw AssertionError("Array not equal: $this != $other")
  }

  infix fun <T> Array<T>.shouldBe(other: Array<T>): Unit {
    if (this.toList() != other.toList())
      throw AssertionError("Array not equal: $this != $other")
  }

  fun <T> nullMatcher() = object : Matcher<T> {
    override fun test(value: T): Result = Result(value == null, "$value should be null")
  }

  infix fun <T> T.should(matcher: (T) -> Unit): Unit = matcher(this)

  infix fun <T> T.shouldBe(matcher: Matcher<T>?): Assertion<T> = should(matcher)
  infix fun <T> T.should(matcher: Matcher<T>?): Assertion<T> {
    val builder = Assertion(this, matcher ?: nullMatcher())
    builders.plus(builder)
    return builder
  }

  infix fun <T> T.shouldNotBe(matcher: Matcher<T>): Assertion<T> = shouldNot(matcher)
  infix fun <T> T.shouldNot(matcher: Matcher<T>): Assertion<T> {
    val builder = Assertion(this, matcher)
    builders.plus(builder)
    return builder
  }

  infix fun <T> T.shouldBe(any: T?): Assertion<T> = shouldEqual(any)
  infix fun <T> T.shouldEqual(any: T?): Assertion<T> {
    return when (any) {
      is Matcher<*> -> should(any as Matcher<T>)
      else -> should(equalityMatcher(any))
    }
  }

  infix fun <T> T.shouldNotBe(any: T?): Assertion<T> {
    return when (any) {
      is Matcher<*> -> shouldNot(any as Matcher<T>)
      else -> shouldNot(equalityMatcher(this))
    }
  }
}
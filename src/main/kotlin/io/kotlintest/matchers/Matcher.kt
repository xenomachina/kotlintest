package io.kotlintest.matchers

class Assertion<T>(val value: T, initial: Matcher<T>) {

  private val matchers = mutableListOf(initial)

  infix fun and(other: Matcher<T>): Assertion<T> {
    matchers + other
    return this
  }

  infix fun or(other: Matcher<T>): Assertion<T> {
    matchers + other
    return this
  }

  fun test(): Unit {
    matchers.forEach {
      val result = it.test(value)
      if (!result.passed)
        throw AssertionError(result.message)
    }
  }
}

interface Matcher<in T> {
  fun test(value: T): Result
  fun invert(): Matcher<T> = object : Matcher<T> {
    override fun test(value: T): Result {
      val result = this@Matcher.test(value)
      return Result(!result.passed, result.message)
    }
  }
}

data class Result(val passed: Boolean, val message: String)
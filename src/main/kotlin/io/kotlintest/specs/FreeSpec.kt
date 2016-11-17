package io.kotlintest.specs

import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestSuite

abstract class FreeSpec(body: FreeSpec.() -> Unit = {}) : Spec() {
  init { body() }

  var current = root

  infix operator fun String.minus(init: () -> Unit): Unit {
    val suite = TestSuite.empty(sanitizeSpecName(this))
    current.nestedSuites.add(suite)
    val temp = current
    current = suite
    init()
    current = temp
  }

  infix operator fun String.invoke(test: () -> Unit): TestCase {
    val tc = TestCase(suite = current, name = sanitizeSpecName(this), test = test, config = defaultTestCaseConfig)
    current.cases.add(tc)
    return tc
  }
}
package io.kotlintest.specs

import io.kotlintest.KTestJUnitRunner
import io.kotlintest.TestBase
import io.kotlintest.TestCase
import org.junit.runner.RunWith

@RunWith(KTestJUnitRunner::class)
abstract class FunSpec : TestBase() {

  fun test(name: String, test: () -> Unit): TestCase {
    val tc = TestCase(suite = root, name = name, test = test, config = defaultTestCaseConfig)
    root.cases.add(tc)
    return tc
  }
}
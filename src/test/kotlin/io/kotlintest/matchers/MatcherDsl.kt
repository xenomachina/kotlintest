package io.kotlintest.matchers

import io.kotlintest.specs.StringSpec

class MatcherDsl : StringSpec() {
  init {

    1 shouldBe beGreaterThan(0) and beLessThan(2)

    "hello world" should startWith("hello") and endWith("world") and include("")

    "hello world" shouldNot startWith("hello") and endWith("world") and include("!")

    listOf(1, 3, 5) should contain(1) and contain(2) and contain(5) and haveSize(3)

    listOf(1, 3, 5) shouldNot contain(1) or contain(2) or contain(5) or haveSize(3)
  }
}

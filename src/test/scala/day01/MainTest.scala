package day01

import org.scalatest.flatspec.AnyFlatSpec
import fs2.Stream
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import testutils.TestHelpers

class MainTest extends AnyFlatSpec {
  private def verifyIncreases(input: Stream[IO, Long], expectedIncreases: Long) =
    assert(
      Main
        .calcPart1(input)
        .compile
        .toList
        .unsafeRunSync()
        .head == expectedIncreases
    )

  "Day 01 part 1" should "determine the correct number of increases in the example" in {

    val input = Stream.emits[IO, Long](List(199, 200, 208, 210, 200, 207, 240, 269, 260, 263))
    verifyIncreases(input, 7)
  }

  it should "determine the correct number of increases in a decreasing list" in {

    val input = Stream.emits[IO, Long](List(10, 9, 8, 7))
    verifyIncreases(input, 0)
  }

  it should "determine the correct number of increases in an empty list" in {

    val input = Stream.emits[IO, Long](List.empty)
    verifyIncreases(input, 0)
  }

  it should "determine the correct answer to the day 01 puzzle" in {
    val input = TestHelpers
      .readResourceLines("/day_01.txt")
      .map(_.toLong)

    verifyIncreases(input, 1624)
  }
}

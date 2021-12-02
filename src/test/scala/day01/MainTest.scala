package day01

import org.scalatest.flatspec.AnyFlatSpec
import fs2.Stream
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import testutils.TestHelpers

class MainTest extends AnyFlatSpec {
  private def verifyIncreases(
      f: Stream[IO, Long] => Stream[IO, Long]
    )(
      input: Stream[IO, Long],
      expectedIncreases: Long,
    ) =
    assert(
      f(input)
        .compile
        .toList
        .unsafeRunSync()
        .head == expectedIncreases
    )

  private val verifyIncreasesPart1 = verifyIncreases(Main.calcPart1)
  private val verifyIncreasesPart2 = verifyIncreases(Main.calcPart2)

  "Day 01 part 1" should "determine the correct number of increases in the example" in {

    val input = Stream.emits[IO, Long](List(199, 200, 208, 210, 200, 207, 240, 269, 260, 263))
    verifyIncreasesPart1(input, 7)
  }

  it should "determine the correct number of increases in a decreasing list" in {

    val input = Stream.emits[IO, Long](List(10, 9, 8, 7))
    verifyIncreasesPart1(input, 0)
  }

  it should "determine the correct number of increases in an empty list" in {

    val input = Stream.emits[IO, Long](List.empty)
    verifyIncreasesPart1(input, 0)
  }

  it should "determine the correct answer to the day 01 puzzle part 1" in {
    val input = TestHelpers
      .readResourceLines("/day_01.txt")
      .map(_.toLong)

    verifyIncreasesPart1(input, 1624)
  }

  "Day 01 part 2" should "determine the correct number of increases in the example" in {

    val input = Stream.emits[IO, Long](List(199, 200, 208, 210, 200, 207, 240, 269, 260, 263))
    verifyIncreasesPart2(input, 5)
  }

  it should "determine the correct answer to the day 01 puzzle part 2" in {
    val input = TestHelpers
      .readResourceLines("/day_01.txt")
      .map(_.toLong)

    verifyIncreasesPart2(input, 1653)
  }
}

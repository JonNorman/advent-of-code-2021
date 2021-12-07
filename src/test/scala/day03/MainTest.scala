package day03

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.Stream
import org.scalatest.flatspec.AnyFlatSpec
import testutils.TestHelpers

class MainTest extends AnyFlatSpec {
  private def verify[In, Out](
      f: Stream[IO, In] => Stream[IO, Out]
    )(
      input: Stream[IO, In],
      expected: Out,
    ) =
    assert(
      f(input)
        .compile
        .toList
        .unsafeRunSync()
        .head == expected
    )

  "Day 03 part 1" should "calculate the correct answer from the example" in {

    val input = Stream.emits[IO, Char](
      """
        |00100
        |11110
        |10110
        |10111
        |10101
        |01111
        |00111
        |11100
        |10000
        |11001
        |00010
        |01010
        |""".stripMargin.toSeq
    )

    val expectedReadings = Map(
      Column(0) -> Readings(7, 5),
      Column(1) -> Readings(5, 7),
      Column(2) -> Readings(8, 4),
      Column(3) -> Readings(7, 5),
      Column(4) -> Readings(5, 7),
    )

    val expectedReport = Report(
      Seq(
        Rate.Gamma,
        Rate.Epsilon,
        Rate.Gamma,
        Rate.Gamma,
        Rate.Epsilon,
      )
    )

    verify(Main.parseReadings(_, 5))(
      input,
      expectedReadings,
    )

    assert(
      Main.buildReport(expectedReadings) === expectedReport
    )

    verify(Main.part1(_, 5))(input, 22 * 9)

  }

  it should "calculate the correct answer from part 1" in {
    val input = TestHelpers
      .readResourceChars("/day_03.txt")

    verify(Main.part1(_, 12))(input, 2346L * 1749L)
  }
}

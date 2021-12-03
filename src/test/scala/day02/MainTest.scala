package day02

import org.scalatest.flatspec.AnyFlatSpec
import fs2.Stream
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import testutils.TestHelpers

class MainTest extends AnyFlatSpec {
  private def verifyVector(
      f: Stream[IO, String] => Stream[IO, Long]
    )(
      input: Stream[IO, String],
      expectedVector: Long,
    ) =
    assert(
      f(input)
        .compile
        .toList
        .unsafeRunSync()
        .head == expectedVector
    )

  private val verifyVectorPart1 = verifyVector(Main.calcPart1)

  "Day 02 part 1" should "determine the correct vector in the example" in {

    val input = Stream.emits[IO, String](
      List(
        "forward 5",
        "down 5",
        "forward 8",
        "up 3",
        "down 8",
        "forward 2",
      )
    )
    verifyVectorPart1(input, 150)
  }

  it should "determine the correct vector in an empty list" in {

    val input = Stream.emits[IO, String](List.empty)
    verifyVectorPart1(input, 0)
  }

  it should "determine the correct answer to the day 02 puzzle part 1" in {
    val input = TestHelpers
      .readResourceLines("/day_02.txt")

    verifyVectorPart1(input, 1604850)
  }
}

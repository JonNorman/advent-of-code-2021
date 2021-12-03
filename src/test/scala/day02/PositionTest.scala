package day02

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.Stream
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import testutils.TestHelpers

class PositionTest extends AnyFlatSpec with TableDrivenPropertyChecks {
  "Position" should "correctly interpret new commands" in {

    val commands =
      Table(
        ("position", "command", "newPosition"),
        (Position(100, 100), Command(Direction.forward, 5), Position(105, 100)),
        (Position(100, 100), Command(Direction.up, 10), Position(100, 90)),
        (Position(100, 100), Command(Direction.down, 10), Position(100, 110)),
      )

    forAll(commands) { (position, command, newPosition) =>
      assert(position + command === newPosition)
    }
  }
}

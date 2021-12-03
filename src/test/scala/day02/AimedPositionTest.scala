package day02

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.Stream
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import testutils.TestHelpers

class AimedPositionTest extends AnyFlatSpec with TableDrivenPropertyChecks {
  "AimedPosition" should "correctly interpret new commands" in {

    val commands =
      Table(
        ("position", "command", "newPosition"),
        (AimedPosition(0, 0, 0), Command(Direction.forward, 5), AimedPosition(5, 0, 0)),
        (AimedPosition(5, 0, 0), Command(Direction.down, 5), AimedPosition(5, 0, 5)),
        (AimedPosition(5, 0, 5), Command(Direction.forward, 8), AimedPosition(13, 40, 5)),
        (AimedPosition(13, 40, 5), Command(Direction.up, 3), AimedPosition(13, 40, 2)),
        (AimedPosition(13, 40, 2), Command(Direction.down, 8), AimedPosition(13, 40, 10)),
        (AimedPosition(13, 40, 10), Command(Direction.forward, 2), AimedPosition(15, 60, 10)),
      )

    forAll(commands) { (position, command, newPosition) =>
      assert(position + command === newPosition)
    }
  }
}

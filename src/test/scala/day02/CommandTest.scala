package day02

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import fs2.Stream
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks
import testutils.TestHelpers

class CommandTest extends AnyFlatSpec with TableDrivenPropertyChecks {
  "Command" should "correctly parse out valid commands" in {

    val commands =
      Table(
        ("inputCommand", "parsedCommand"),
        ("forward 5", Command(Direction.forward, 5)),
        ("down 5", Command(Direction.down, 5)),
        ("up 3", Command(Direction.up, 3)),
      )

    forAll(commands) { (inputCommand, parsedCommand) =>
      assert(Command.parse(inputCommand).contains(parsedCommand))
    }
  }

  it should "handle invalid commands" in {

    val commands =
      Table(
        "inputCommand",
        "ahead 2",
        "back 5",
        "top 3",
      )

    forAll(commands) { command =>
      assert(Command.parse(command).isEmpty)
    }
  }
}

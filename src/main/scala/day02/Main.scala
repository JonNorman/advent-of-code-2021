package day02

import cats.effect.{IO, IOApp}
import fs2.io.file.{Files, Path}
import fs2.{Pipe, Stream, text}

import java.io.FileNotFoundException
import scala.io.Source
import scala.util.chaining.*
import scala.util.{Failure, Success, Try}

object Main:

  def calcPart1(input: Stream[IO, String]): Stream[IO, Long] =
    input
      .through(Command.parseCommands)
      .fold(Position.empty)(_ + _)
      .map(_.vector)

  def calcPart2(input: Stream[IO, String]): Stream[IO, Long] =
    input
      .through(Command.parseCommands)
      .fold(AimedPosition.empty)(_ + _)
      .map(_.vector)

enum Direction:
  case forward, up, down

case class Command(direction: Direction, units: Long)

object Command {
  def parse(command: String): Option[Command] =
    command.split(" ").toList match {
      case direction :: units :: Nil =>
        Try(
          Command(Direction.valueOf(direction), units.toLong)
        ).toOption
      case _ => None
    }

  def parseCommands[F[_], I, O]: Pipe[F, String, Command] = {
    _
      .map(Command.parse)
      .collect {
        case Some(command) => command
      }
  }
}

case class AimedPosition(horizontal: Long, depth: Long, aim: Long) {

  def +(command: Command): AimedPosition = {
    command.direction match {
      case Direction.forward => this.copy(horizontal = horizontal + command.units, depth = depth + (aim * command.units))
      case Direction.up => this.copy(aim = aim - command.units)
      case Direction.down => this.copy(aim = aim + command.units)
    }
  }

  val vector: Long = horizontal * depth

}

object AimedPosition {
  val empty: AimedPosition = AimedPosition(0, 0, 0)
}

case class Position(horizontal: Long, depth: Long) {

  def +(command: Command): Position = {
    command.direction match {
      case Direction.forward => this.copy(horizontal = horizontal + command.units)
      case Direction.up => this.copy(depth = depth - command.units)
      case Direction.down => this.copy(depth = depth + command.units)
    }
  }

  val vector: Long = horizontal * depth
}

object Position {
  val empty: Position = Position(0, 0)
}
package day01

import cats.effect.{IO, IOApp}
import fs2.{Stream, text}
import fs2.io.file.{Files, Path}

import java.io.FileNotFoundException
import scala.io.Source
import scala.util.{Failure, Success, Try}
import scala.util.chaining._

object Main:

  def calcPart1(input: Stream[IO, Long]): Stream[IO, Long] =
    input
      .fold(State.empty)((state, measurement) => {
    if (state.previousValue.exists(_ < measurement))
      State(previousValue = Some(measurement), state.increases + 1)
    else
      State(previousValue = Some(measurement), state.increases)
  }).map(_.increases)

  def calcPart2(input: Stream[IO, Long]): Stream[IO, Long] =
    input
      .sliding(3, 1)
      .filter(_.size == 3)
      .map(_.toList.sum)
      .pipe(calcPart1)

  private[Main] case class State(previousValue: Option[Long], increases: Long)

  private[Main] object State:
    val empty: State = State(None, 0)
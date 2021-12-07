package day03

import cats.effect.{IO, IOApp}
import fs2.io.file.{Files, Path}
import fs2.{Pipe, Stream, text}

import java.io.FileNotFoundException
import scala.io.Source
import scala.util.chaining.*
import scala.util.{Failure, Success, Try}

object Main:

  def part1(input: Stream[IO, Char], width: Int): Stream[IO, Long] =
    parseReadings(input, width)
      .map(buildReport)
      .map(report => report.gamma * report.epsilon)

  def parseReadings(input: Stream[IO, Char], width: Int): Stream[IO, Map[Column, Readings]] =
    input
      .filter(char => char == '1' || char == '0')
      .zipWithIndex
      .fold(Map.empty[Column, Readings]) { case (readings, (char, index)) =>

        val column = Column(index % width)
        val columnReading = readings.getOrElse(column, Readings.empty)

        char match {
          case '1' => readings + (column -> columnReading.incrementGamma)
          case '0' => readings + (column -> columnReading.incrementEpsilon)
        }
      }

  def buildReport(readings: Map[Column, Readings]): Report =
    Report
      .fromReadings(readings)

case class Column(value: Long) extends AnyVal

enum Rate:
  case Gamma, Epsilon

case class Report(columnReadings: Seq[Rate]):

  private def rateReading(rate: Rate): Long =
    columnReadings
      .reverse
      .zipWithIndex
      .foldLeft(0L){
        case (acc, (r, bitPosition)) if r == rate =>
          acc + Math.pow(2, bitPosition).toLong
        case (acc, _) =>
          acc
      }

  lazy val gamma: Long = rateReading(Rate.Gamma)

  lazy val epsilon: Long = rateReading(Rate.Epsilon)

object Report:

  def fromReadings(readings: Map[Column, Readings]): Report =
    Report(
    readings
      .toSeq
      .sortBy(_._1.value)
      .map(_._2.mostPopular)
    )



case class Readings(gamma: Int, epsilon: Int):
  def incrementGamma: Readings = this.copy(gamma = gamma + 1)
  def incrementEpsilon: Readings = this.copy(epsilon = epsilon + 1)

  def mostPopular: Rate = if (gamma >= epsilon) Rate.Gamma else Rate.Epsilon

object Readings:
  val empty: Readings = Readings(0, 0)

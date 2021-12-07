package testutils

import cats.effect.IO
import fs2.io.file.{Files, Path}
import fs2.text
import fs2.Stream

import java.io.FileNotFoundException
import java.nio.file.Paths
import scala.util.{Failure, Success, Try}
import io.Source

object TestHelpers:
  /**
   * Reads a UTF-8-encoded resource and splits it by line.
   * @param resourcePath - the location of the resource
   * @return an `fs2.Stream[IO, String]`, containing a single error if the file cannot be found.
   */
  def readResourceLines(resourcePath: String): Stream[IO, String] =

    findResource(resourcePath) match {
      case Failure(exception) => Stream.raiseError(exception)
      case Success(resource) => Files[IO]
        .readAll(resource)
        .through(text.utf8.decode)
        .through(text.lines)
    }

  def readResourceChars(resourcePath: String): Stream[IO, Char] =

    findResource(resourcePath) match {
      case Failure(exception) => Stream.raiseError(exception)
      case Success(resource) => Files[IO]
        .readAll(resource)
        .through(text.utf8.decode)
        .flatMap(Stream.emits)
    }

  private def findResource(resourcePath: String): Try[Path] =
    Option(
      getClass
        .getResource(resourcePath)
      ) match
      case Some(url) => Success(Path(Paths.get(url.toURI).toString))
      case None => Failure(new FileNotFoundException(resourcePath))


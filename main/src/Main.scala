package byom

import cats._
import cats.implicits._
import cats.effect._

object Main extends IOApp {

  def run(args: List[String]):IO[ExitCode] = for {
    _ <- IO.println(s"You passed in ${args.length} arguments: $args")
    _ <- IO.println("abstract nonsense ftw")
  } yield ExitCode.Success

}
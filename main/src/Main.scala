package byom

import cats._
import cats.implicits._
import cats.effect._


/**
 * implementations
 * */
implicit object primitivesIO extends HashFunction[IO] {
    def sha256(input: Array[Byte]): IO[Array[Byte]] = 
    IO(java.security.MessageDigest.getInstance("SHA-256")).map(_.digest(input))
}

object Main extends IOApp {

  def run(args: List[String]):IO[ExitCode] = for {
    _ <- IO.println(s"You passed in ${args.length} arguments: $args")
    _ <- IO.println(s"In hex these arguments are ${args.map(_.hex)}")
    _ <- HashFunction.sha256("abc".getBytes).map(_.hex) >>= (m => IO.println(s"sha256(abc) == $m"))
    tagged_hash <- HashFunction.taggedSha256("tag","abc".getBytes).map(_.hex)
    _ <- IO.println(s"sha256_tag(abc) == $tagged_hash")
    _ <- IO.println("abstract nonsense ftw")
  } yield ExitCode.Success

}
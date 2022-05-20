/**
 * Finally tagless style abstraction of cryptographic hash functions
 * */

package byom

import cats._
import cats.implicits._

trait HashFunction[F[_]]{
    def sha256(input: Array[Byte]): F[Array[Byte]]
}

object HashFunction {
    def apply[F[_] : HashFunction]: HashFunction[F] = implicitly
    def sha256[F[_] : HashFunction](input: Array[Byte]) = HashFunction[F].sha256(input)

    def taggedSha256[F[_] : HashFunction : FlatMap](tag: String, msg: Array[Byte]): F[Array[Byte]] =
        val tag_hash = sha256(tag.getBytes("UTF-8"))
        tag_hash.flatMap(th => sha256(th ++ th ++ msg))
}
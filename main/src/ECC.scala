package byom.ecc

import cats._
import cats.implicits._

/**
 * A finally tagless representation of some elliptic curve algorithms
 * 
 *  y^2 = x^3 + a*x + b  mod p
 * 
 * 
 * */
trait Curve[F[_]] {
    sealed trait ECCPoint
    final case class CurvePoint(x: BigInt, y:BigInt) extends ECCPoint
    case object PointAtInfinity extends ECCPoint

    def curveName: F[String]
    def _p: F[BigInt]  // the modulus of the finite field
    def _a: F[BigInt] // the a coefficient
    def _b: F[BigInt] // the b coefficient
    def G: F[CurvePoint] // the generator point G
    def _n: F[BigInt] // the order of the group generated by G
}


object Curve {
   
    def apply[F[_] : Curve]: Curve[F] = implicitly


    implicit class curveOpsM[F[_]: Monad](curve: Curve[F]){

        def isPointAtInfinity(point: F[Curve[F]#ECCPoint]): F[Boolean] =
            point.map{
                case curve.PointAtInfinity => true
                case _ => false
            }

        def pointDouble( point: F[Curve[F]#ECCPoint]): F[Curve[F]#ECCPoint] = for {
            pt <- point
            a <- curve._a
            p <- curve._p
            r = pt match {
                case curve.PointAtInfinity => pt
                case curve.CurvePoint(x,y) =>
                    val L = (  ((BigInt(3) * x.pow(2)) + a) * ((BigInt(2) * y)).modInverse(p) ).mod(p)
                    val x_new = (L.pow(2) - x - x).mod(p)
                    val y_new = (L*(x - x_new) - y).mod(p)
                    curve.CurvePoint(x_new, y_new)
            }
        } yield r

    }

    def secp256k1M[F[_] : Monad] : Curve[F] = new Curve[F] {
        // Members declared in byom.ecc.Curve
        def G: F[this.CurvePoint] = 
            this.CurvePoint(
                x = BigInt("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",16),
                y = BigInt("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8",16)
        ).pure[F]
        def _a: F[BigInt] = BigInt(0).pure[F]
        def _b: F[BigInt] = BigInt(7).pure[F]
        def _n: F[BigInt] = BigInt("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",16).pure[F]

        def _p: F[BigInt] = BigInt("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F",16).pure[F]

        def curveName: F[String] = "secp256k1".pure[F]
    }
}

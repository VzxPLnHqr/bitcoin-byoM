import mill._, scalalib._

object main extends ScalaModule {

  def scalaVersion = "3.1.2"
  
  def ivyDeps = Agg(
    ivy"org.typelevel::cats-effect:3.3.11"
  )

  def mainClass = Some("byom.Main")
}
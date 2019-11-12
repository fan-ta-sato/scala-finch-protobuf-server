import sbt._

object Versions {
  val Scala = "2.12.10"
  val ScalamacrosParadise = "2.1.1"

  val Finch = "0.31.0"  // not supported scala 2.13
  val Cats = "2.0.0"
  val Twitter = "19.11.0"
}

object Dependencies {
  lazy val grpc = Seq(
    Libraries.cats,
    Libraries.catsEffect,
    Libraries.finch,
    Libraries.twitterServer,
    Libraries.twitterUtil,
    Libraries.twitterUtilLogging,
  )
}

object Libraries {
  lazy val cats = "org.typelevel" %% "cats-core" % Versions.Cats
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % Versions.Cats
  lazy val finch = "com.github.finagle" %% "finchx-core" % Versions.Finch
  lazy val twitterServer = "com.twitter" %% "twitter-server" % Versions.Twitter
  lazy val twitterUtil = "com.twitter" %% "util-core" % Versions.Twitter
  lazy val twitterUtilLogging = "com.twitter" %% "util-logging" % Versions.Twitter
}

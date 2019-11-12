lazy val root = (project in file("."))
  .settings(
    name := "proto-sample",
    version := "1.0"
  )
  .settings(commonSettings)
  .settings(Seq(
    libraryDependencies ++= Dependencies.grpc
  ))
  .settings(pbSettings)

/**
 * Common Settings
 */
lazy val commonSettings = Seq(
  scalaVersion := Versions.Scala,
  scalacOptions ++= Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xfuture", // Turn on future language features.
    "-Ywarn-unused-import", // Warn when imports are unused.
    "-Ywarn-numeric-widen", // Warn when numerics are widened.
    "-Yno-adapted-args" // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  ),
  scalacOptions in (Compile, console) ~= (_.filterNot(Set("-Xlint", "-Ywarn-unused:imports"))),
  libraryDependencies ++= Seq(
    compilerPlugin(
      ("org.scalamacros" % "paradise" % Versions.ScalamacrosParadise).cross(CrossVersion.patch))
  ),
  testOptions in Test += Tests.Argument("-oD"),
  fork in Test := true,
  wartremoverErrors in (Compile, compile) := Warts.unsafe
    .filterNot(Set(Wart.Any)) ++ Seq(
    Wart.ExplicitImplicitTypes,
    Wart.FinalCaseClass,
    Wart.FinalVal,
    Wart.LeakingSealed,
    Wart.While
  ),
  wartremoverExcluded ++= Seq(sourceManaged.value),
  // scaladoc: Create inheritance diagrams for classes, traits and packages.
  scalacOptions in (Compile, doc) := Seq("-diagrams"),

  /**
   * @see https://github.com/sbt/sbt-assembly#merge-strategy
   */
  assemblyMergeStrategy in assembly := {
    case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  test in assembly := {},
)

lazy val pbSettings = Seq(
  PB.targets in Compile := Seq(
    protoc_lint.ProtocLint() -> (sourceManaged in Compile).value,
    scalapb.gen() -> (sourceManaged in Compile).value
  ),
)

/**
 * test
 */
addCommandAlias("testFast", ";testOnly -- -l org.scalatest.tags.Network")
addCommandAlias("testNetwork", ";testOnly -- -n org.scalatest.tags.Network")
addCommandAlias("t", "testFast")

/**
 * scalafmt
 */
addCommandAlias("fmt", ";scalafmt ;test:scalafmt")
scalafmtVersion in ThisBuild := "1.5.1"
scalafmtTestOnCompile in ThisBuild := true
scalafmtShowDiff in (ThisBuild, scalafmt) := true

name := "advent-of-code-2021"

version := "0.1"

scalaVersion := "3.1.0"

ThisBuild / scalacOptions ++=
  Seq(
    "-deprecation",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
  )

lazy val adventOfCode =
  project
    .in(file("."))
    .settings(name := "advent-of-code-2021")
    .settings(commonSettings)
    .settings(dependencies)

lazy val commonSettings = commonScalacOptions

lazy val commonScalacOptions = Seq(
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings",
  ),
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value,
)

lazy val dependencies = Seq(
  libraryDependencies ++=
    Seq("co.fs2" %% "fs2-core", "co.fs2" %% "fs2-io").map(_ % "3.2.2")
      ++ Seq("org.typelevel" %% "cats-effect" % "3.3.0"),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.2.10",
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0",
  ).map(_ % Test),
)

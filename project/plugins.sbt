ThisBuild / autoStartServer := false

update / evictionWarningOptions := EvictionWarningOptions.empty

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.4")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
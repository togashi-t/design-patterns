val scala3Version = "3.0.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "design-patterns",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
  )

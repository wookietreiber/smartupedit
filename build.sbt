import smartupedit.build._
import Dependencies._
import AssemblyKeys._

lazy val core = (
  EditorProject("smartupedit-core", "core")
  settings(
    libraryDependencies ++= Seq(markwrap, specs2 % "test")
  )
)

lazy val swingClient = (
  EditorProject("smartupedit-swing", "swing")
  dependsOn(core)
  settings(assemblySettings: _*)
  settings(
    libraryDependencies += swing % scalaVersion.value
  )
)

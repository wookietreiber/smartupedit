import smartupedit.build._
import Dependencies._

lazy val core = (
  EditorProject("smartupedit-core", "core")
  settings(
    libraryDependencies += markwrap
  )
)

lazy val swingClient = (
  EditorProject("smartupedit-swing", "swing")
  dependsOn(core)
  settings(
    libraryDependencies += swing % scalaVersion.value
  )
)

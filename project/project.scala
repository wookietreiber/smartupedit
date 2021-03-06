package smartupedit
package build

import sbt._
import Keys._

object EditorProject {
  def apply(name: String, path: String): Project = (
    Project(name, file(path))
    settings(commonSettings: _*)
  )
}

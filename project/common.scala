package smartupedit

import sbt._
import Keys._

package object build {
  val commonSettings = Seq (
    organization := "com.github.wookietreiber",
    version      := "0.2.0-SNAPSHOT",
    scalaVersion := "2.10.3",
    sourceDirectory <<= baseDirectory(identity)
  )
}

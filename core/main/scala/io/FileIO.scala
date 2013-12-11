package smartupedit
package io

import scala.io.Source

trait FileIO {

  type File = java.io.File

  def File(path: String): File = new File(path)

  implicit class RichFile(file: File) {
    def read(): String = {
      val source = Source.fromFile(file)
      try {
        source.getLines.mkString("\n")
      } finally {
        source.close()
      }
    }

    def write(content: String): Unit = {
      val writer = new java.io.FileWriter(file)
      try {
        writer.write(content)
      } finally {
        writer.close()
      }
    }
  }

}

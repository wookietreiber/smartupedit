package smartupedit

import java.io._

trait FileIO {

  implicit class RichFile(file: File) {
    def read: String = {
      val source = io.Source.fromFile(file)
      try {
        source.getLines.mkString("\n")
      } finally {
        source.close()
      }
    }

    def write(content: String) = {
      val writer = new FileWriter(file)
      try {
        writer.write(content)
      } finally {
        writer.close()
      }
    }
  }

}

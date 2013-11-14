package smartupedit

import java.io._

trait IOHandling {

  object IO {
    def write(content: ⇒ String) = new ContentWriter(content)

    def read(file: File) = new ContentReader(file)

    class ContentReader(file: File) {
      def to(f: String ⇒ Unit) = {
        val source = io.Source.fromFile(file)
        try {
          f(source.getLines.mkString("\n"))
        } finally {
          source.close()
        }
      }
    }

    class ContentWriter(content: ⇒ String) {
      def to(file: File) = {
        val writer = new FileWriter(file)
        try {
          writer.write(content)
        } finally {
          writer.close()
        }
      }
    }
  }

}

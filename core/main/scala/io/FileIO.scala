package smartupedit

trait FileIO {

  type File = java.io.File

  def File(path: String) = new File(path)

  implicit class RichFile(file: File) {
    def read(): String = {
      val source = io.Source.fromFile(file)
      try {
        source.getLines.mkString("\n")
      } finally {
        source.close()
      }
    }

    def write(content: String) = {
      val writer = new java.io.FileWriter(file)
      try {
        writer.write(content)
      } finally {
        writer.close()
      }
    }
  }

}

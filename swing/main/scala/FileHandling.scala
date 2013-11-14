package smartupedit

import java.io._

import scala.swing.FileChooser

trait FileHandling {

  self: SimpleMarkupEditor.type ⇒

  var current = Option.empty[File]

  def baseDir = current map { _.getParentFile } getOrElse {
    new File(sys.props("user.dir"))
  }

  def save(opt: Option[File] = current) = for (file ← opt orElse chooseSaveTarget()) {
    IO write editor.text to file
    current = Some(file)
  }

  def saveAs() = save(None)

  def export() = for (file ← chooseSaveTarget()) {
    IO write viewer.text to file
  }

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

  def chooseSaveTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showSaveDialog(over = editor) match {
      case FileChooser.Result.Approve ⇒
        Some(chooser.selectedFile)

      case _ ⇒ None
    }
  }

  def open() = for (file ← chooseOpenTarget()) {
    IO read file to editor.text_=
    current = Some(file)
  }

  def chooseOpenTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showOpenDialog(over = editor) match {
      case FileChooser.Result.Approve ⇒
        Some(chooser.selectedFile)

      case _ ⇒ None
    }
  }

}

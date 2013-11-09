package smartupedit

import java.io._

import scala.swing.FileChooser

trait FileHandling {

  self: SimpleMarkupEditor.type ⇒

  var current = Option.empty[File]

  def baseDir = current map { _.getParentFile } getOrElse {
    new File(sys.props("user.home"))
  }

  def save(opt: Option[File] = current) = for (file ← opt orElse chooseSaveTarget()) {
    writeTo(file)
    current = Some(file)
  }

  def saveAs() = save(None)

  def writeTo(file: File) = {
    val writer = new FileWriter(file)
    writer.write(editor.text)
    writer.close()
  }

  def chooseSaveTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showSaveDialog(over = editor) match {
      case FileChooser.Result.Approve ⇒
        Some(chooser.selectedFile)

      case _ ⇒ None
    }
  }

}

package smartupedit

import language.reflectiveCalls

import java.io.File

trait FileHandling extends IOHandling {

  def editor: {
    def text: String
    def text_=(s: String)
  }

  def viewer: {
    def text: String
  }

  def chooseSaveTarget(dir: File = baseDir): Option[File]
  def chooseOpenTarget(dir: File = baseDir): Option[File]

  var current = Option.empty[File]

  def baseDir = current map { _.getParentFile } getOrElse {
    new File(sys.props("user.dir"))
  }

  def open() = for (file ← chooseOpenTarget()) {
    IO read file to editor.text_=
    current = Some(file)
  }

  def save(opt: Option[File] = current) = for (file ← opt orElse chooseSaveTarget()) {
    IO write editor.text to file
    current = Some(file)
  }

  def saveAs() = save(None)

  def export() = for (file ← chooseSaveTarget()) {
    IO write viewer.text to file
  }

}

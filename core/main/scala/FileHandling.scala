package smartupedit

import language.reflectiveCalls

import java.io.File

trait FileHandling extends Client with FileIO {

  def chooseSaveTarget(dir: File = baseDir): Option[File]
  def chooseOpenTarget(dir: File = baseDir): Option[File]

  var current = Option.empty[File]

  def baseDir = current map { _.getParentFile } getOrElse {
    new File(sys.props("user.dir"))
  }

  def open(file: File) = {
    editor.text = file.read
    current = Some(file)
  }

  def save(file: File) = {
    file write editor.text
    current = Some(file)
  }

  def openAsk() = chooseOpenTarget() foreach open

  def saveAsk(opt: Option[File] = current) = {
    val target = opt orElse chooseSaveTarget()
    target foreach save
    target
  }

  def saveAskAs() = saveAsk(None)

  def exportAsk() = chooseSaveTarget() foreach export

  def export(file: File) =
    file write viewer.text

  abstract override def clear() = {
    super.clear()
    current = None
  }

}

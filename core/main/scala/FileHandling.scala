package smartupedit

import language.reflectiveCalls

trait FileHandling extends Client with FileIO {

  var current = Option.empty[File]

  def isCurrent(file: File) = current exists {
    _.getAbsolutePath == file.getAbsolutePath
  }

  def baseDir = current map { _.getParentFile } getOrElse {
    File(sys.props("user.dir"))
  }

  abstract override def clear() = {
    super.clear()
    current = None
  }

  // -----------------------------------------------------------------------------------------------
  // exporting
  // -----------------------------------------------------------------------------------------------

  def chooseExportTarget(dir: File = baseDir): Option[File]

  def exportAsk() = chooseExportTarget() foreach export

  def export(file: File) =
    file write viewer.text

  // -----------------------------------------------------------------------------------------------
  // opening
  // -----------------------------------------------------------------------------------------------

  def chooseOpenTarget(dir: File = baseDir): Option[File]

  def openAsk() = chooseOpenTarget() foreach open

  def open(file: File) = {
    editor.text = file.read()
    current = Some(file)
  }

  // -----------------------------------------------------------------------------------------------
  // saving
  // -----------------------------------------------------------------------------------------------

  def chooseSaveTarget(dir: File = baseDir): Option[File]

  def saveAsk(opt: Option[File] = current) = {
    val target = opt orElse chooseSaveTarget()
    target foreach save
    target
  }

  def saveAskAs() = saveAsk(None)

  def save(file: File) = {
    file write editor.text
    current = Some(file)
  }

}

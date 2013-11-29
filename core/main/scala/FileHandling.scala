package smartupedit

import language.reflectiveCalls

import java.io.File

trait FileHandling extends Client with IOHandling {

  def chooseSaveTarget(dir: File = baseDir): Option[File]
  def chooseOpenTarget(dir: File = baseDir): Option[File]

  var current = Option.empty[File]

  def baseDir = current map { _.getParentFile } getOrElse {
    new File(sys.props("user.dir"))
  }

  def open(file: File) = {
    IO read file to editor.text_=
    current = Some(file)
  }

  def save(file: File) = {
    IO write editor.text to file
    current = Some(file)
  }

  def openAsk() = for (file ← chooseOpenTarget())
    open(file)

  def saveAsk(opt: Option[File] = current) = {
    val o = opt orElse chooseSaveTarget()
    for (file ← o)
      save(file)
    o
  }

  def saveAskAs() = saveAsk(None)

  def export() = for (file ← chooseSaveTarget())
    IO write viewer.text to file

  abstract override def clear() = {
    super.clear()
    current = None
  }

}

package smartupedit

import language.reflectiveCalls

trait FileHandling extends Client with FileIO {

  var current: Option[File] = Option.empty[File]

  def isCurrent(file: File): Boolean = current exists {
    _.getAbsolutePath == file.getAbsolutePath
  }

  def baseDir: File = current map { _.getParentFile } getOrElse {
    File(sys.props("user.dir"))
  }

  def newFile(): ActionResult = {
    editor.text = ""
    current = None
    ActionPerformed
  }

}
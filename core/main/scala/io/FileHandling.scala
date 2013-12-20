package smartupedit
package io

import language.reflectiveCalls

trait FileHandling extends Client with FileIO {

  private var _current: Option[File] = Option.empty[File]

  /** Returns the current file. */
  final def current: Option[File] =
    _current

  private[io] final def current_=(of: Option[File]): Unit =
    _current = of

  private[io] final def current_=(f: File): Unit =
    _current = Some(f)

  private[io] final def isCurrent(file: File): Boolean = current exists {
    _.getAbsolutePath == file.getAbsolutePath
  }

  /** Returns the directory name of [[current]] or else the current working directory. */
  final def baseDir: File = current map { _.getParentFile } getOrElse {
    File(sys.props("user.dir"))
  }

  /** Creates a new and empty file. */
  final def newFile(): ActionResult =
    newFile1()

  private[io] def newFile1(): ActionResult = {
    editor.text = ""
    current = None
    ActionResult.Performed
  }

}

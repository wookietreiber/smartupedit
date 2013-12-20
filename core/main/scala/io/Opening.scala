package smartupedit
package io

import language.reflectiveCalls

trait Opening extends FileHandling {

  def chooseOpenTarget(dir: File = baseDir): Option[File]

  final def open(): ActionResult =
    open1()

  private[io] def open1(): ActionResult =
    chooseOpenTarget().map(open).getOrElse(ActionEscalate)

  private[io] def open(file: File): ActionResult = {
    editor.text = file.read()
    current = Some(file)
    ActionPerformed
  }

}

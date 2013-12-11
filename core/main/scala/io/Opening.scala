package smartupedit
package io

import language.reflectiveCalls

trait Opening extends FileHandling {

  def chooseOpenTarget(dir: File = baseDir): Option[File]

  def openAsk(): ActionResult =
    chooseOpenTarget().map(open).getOrElse(ActionEscalate)

  def open(file: File): ActionResult = {
    editor.text = file.read()
    current = Some(file)
    ActionPerformed
  }

}

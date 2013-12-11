package smartupedit
package io

import language.reflectiveCalls

trait Saving extends FileHandling {

  def chooseSaveTarget(dir: File = baseDir): Option[File]

  def saveAsk(opt: Option[File] = current): ActionResult =
    opt.orElse(chooseSaveTarget()).map(save).getOrElse(ActionEscalate)

  def saveAskAs(): ActionResult =
    saveAsk(None)

  def save(file: File): ActionResult = {
    file write editor.text
    current = Some(file)
    ActionPerformed
  }

}

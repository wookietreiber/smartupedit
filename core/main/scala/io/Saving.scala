package smartupedit
package io

import language.reflectiveCalls

trait Saving extends FileHandling {

  def chooseSaveTarget(dir: File = baseDir): Option[File]

  final def save(): ActionResult =
    save(current)

  final def saveAs(): ActionResult =
    save(None)

  private[io] final def save(opt: Option[File] = current): ActionResult =
    opt.orElse(chooseSaveTarget()).map(save).getOrElse(ActionEscalate)

  private[io] def save(file: File): ActionResult = {
    file write editor.text
    current = Some(file)
    ActionPerformed
  }

}

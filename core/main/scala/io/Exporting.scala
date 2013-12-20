package smartupedit
package io

import language.reflectiveCalls

trait Exporting extends FileHandling {

  def chooseExportTarget(dir: File = baseDir): Option[File]

  final def export(): ActionResult =
    chooseExportTarget().map(export).getOrElse(ActionResult.Escalate)

  private[io] def export(file: File): ActionResult = {
    file write viewer.text
    ActionResult.Performed
  }

}

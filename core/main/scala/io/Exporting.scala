package smartupedit

import language.reflectiveCalls

trait Exporting extends FileHandling {

  def chooseExportTarget(dir: File = baseDir): Option[File]

  def exportAsk(): ActionResult =
    chooseExportTarget().map(export).getOrElse(ActionEscalate)

  def export(file: File): ActionResult = {
    file write viewer.text
    ActionPerformed
  }

}

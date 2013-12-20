package smartupedit
package io

import language.reflectiveCalls

trait AskOverwrite extends FileHandlingClient {

  def askOverwrite(): DialogResult

  private[io] abstract override def export(file: File): ActionResult =
    if (!file.exists || askOverwrite() == DialogResult.Yes) {
      super.export(file)
    } else {
      ActionResult.Escalate
    }

  private[io] abstract override def save(file: File): ActionResult =
    if (isCurrent(file) || !file.exists || askOverwrite() == DialogResult.Yes) {
      super.save(file)
    } else {
      ActionResult.Escalate
    }

}

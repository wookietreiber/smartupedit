package smartupedit
package io

import language.reflectiveCalls

trait AskOverwrite extends FileHandlingClient {

  def askOverwrite(): DialogOption.Result

  private[io] abstract override def export(file: File): ActionResult =
    if (!file.exists || askOverwrite() == DialogOption.Yes) {
      super.export(file)
    } else {
      ActionEscalate
    }

  private[io] abstract override def save(file: File): ActionResult =
    if (isCurrent(file) || !file.exists || askOverwrite() == DialogOption.Yes) {
      super.save(file)
    } else {
      ActionEscalate
    }

}

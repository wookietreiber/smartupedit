package smartupedit
package io

trait AskSave extends FileHandlingClient {

  var hasChanged = false

  def askSave(): DialogResult

  def resetWith(body: => ActionResult): ActionResult = {
    val result = body

    if (result == ActionResult.Performed) {
      hasChanged = false
    }

    result
  }

  private[io] abstract override def save(file: File): ActionResult = resetWith {
    super.save(file)
  }

  private[smartupedit] abstract override def convert1(): Unit = {
    super.convert1()
    hasChanged = true
  }

  def hasChangedDependent(body: ⇒ ActionResult): ActionResult =
    if (!hasChanged) {
      body
    } else {
      askSave() match {
        case DialogResult.Yes ⇒
          val result = save()

          if (result == ActionResult.Performed) {
            body
          } else {
            result
          }

        case DialogResult.No ⇒
          body

        case DialogResult.Cancel ⇒
          ActionResult.Escalate
      }
    }

  abstract override def newFile(): ActionResult = hasChangedDependent {
    resetWith {
      super.newFile()
    }
  }

  private[io] abstract override def open1(): ActionResult = hasChangedDependent {
    resetWith {
      super.open1()
    }
  }

  abstract override def quit(): Unit = hasChangedDependent {
    super.quit()
    ActionResult.Performed
  }

}

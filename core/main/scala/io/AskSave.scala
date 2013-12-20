package smartupedit
package io

trait AskSave extends FileHandlingClient {

  var hasChanged = false

  def askSave(): DialogOption.Result

  def resetWith(body: => ActionResult): ActionResult = {
    val result = body

    if (result == ActionPerformed) {
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
        case DialogOption.Yes ⇒
          val result = save()

          if (result == ActionPerformed) {
            body
          } else {
            result
          }

        case DialogOption.No ⇒
          body

        case DialogOption.Cancel ⇒
          ActionEscalate
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
    ActionPerformed
  }

}

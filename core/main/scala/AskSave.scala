package smartupedit

trait AskSave extends FileHandlingClient {

  var hasChanged = false

  def askSave(): DialogOption.Result

  def resetWith(body: => ActionResult): ActionResult = {
    val result = body

    if (result == ActionPerformed)
      hasChanged = false

    result
  }

  abstract override def save(file: File) = resetWith {
    super.save(file)
  }

  abstract override def convert() = {
    super.convert()
    hasChanged = true
  }

  def hasChangedDependent(body: ⇒ ActionResult): ActionResult =
    if (!hasChanged) {
      body
    } else askSave() match {
      case DialogOption.Yes ⇒
        val result = saveAsk()

        if (result == ActionPerformed)
          body
        else
          result

      case DialogOption.No ⇒
        body

      case DialogOption.Cancel ⇒
        ActionEscalate
    }

  abstract override def newFile() = hasChangedDependent {
    resetWith {
      super.newFile()
    }
  }

  abstract override def openAsk() = hasChangedDependent {
    resetWith {
      super.openAsk()
    }
  }

  abstract override def quit() = hasChangedDependent {
    super.quit()
    ActionPerformed
  }

}

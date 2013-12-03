package smartupedit

import java.io.File

trait AskSave extends Client with FileHandling {

  var hasChanged = false

  def askSave(): DialogOption.Result

  abstract override def save(file: File) = {
    super.save(file)
    hasChanged = false
  }

  abstract override def convert() = {
    super.convert()
    hasChanged = true
  }

  def hasChangedDependent(body: ⇒ Unit): Unit =
    if (!hasChanged) {
      body
    } else askSave() match {
      case DialogOption.Yes ⇒
        if (saveAsk().isDefined) body

      case DialogOption.No ⇒
        body

      case DialogOption.Cancel ⇒
    }

  abstract override def clear() = hasChangedDependent {
    super.clear()
    hasChanged = false
  }

  abstract override def openAsk() = hasChangedDependent {
    super.openAsk()
    hasChanged = false
  }

  abstract override def quit() = hasChangedDependent {
    super.quit()
  }

}

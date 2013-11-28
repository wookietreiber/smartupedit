package smartupedit

import java.io.File

object AskSave {

  object AskSaveOption {
    sealed abstract class Result

    object Yes extends Result
    object No extends Result
    object Cancel extends Result
  }

}

import AskSave._

trait AskSave extends Client {

  var hasChanged = false

  def askSave(): AskSaveOption.Result

  abstract override def save(file: File) = {
    super.save(file)
    hasChanged = false
  }

  abstract override def convert(text: String): String = {
    val result = super.convert(text)
    hasChanged = true
    result
  }

  def hasChangedDependent(body: ⇒ Unit): Unit =
    if (!hasChanged) {
      body
    } else askSave() match {
      case AskSaveOption.Yes ⇒
        if (saveAsk().isDefined) body

      case AskSaveOption.No ⇒
        body

      case AskSaveOption.Cancel ⇒
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

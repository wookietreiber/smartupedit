package smartupedit

import scala.swing._
import scala.swing.event._

import scala.util._

trait Actions {

  self: SimpleMarkupEditor.type with FileHandling ⇒

  object action {
    val showError: PartialFunction[Throwable,Unit] = {
      case exception: Exception ⇒
        Dialog showMessage (
          parent = editor,
          message = exception.getMessage,
          title = exception.getClass.getSimpleName,
          messageType = Dialog.Message.Error
        )
    }

    object open extends Action("Open") {
      accelerator = Some(KeyStroke(Key.O, Key.Modifier.Control))
      mnemonic    = Key.O.id

      override def apply = Try(self.open()) recover showError
    }

    object save extends Action("Save") {
      accelerator = Some(KeyStroke(Key.S, Key.Modifier.Control))
      mnemonic    = Key.S.id

      override def apply = Try(self.save()) recover showError
    }

    object saveAs extends Action("Save As") {
      accelerator = Some(KeyStroke(Key.S, Key.Modifier.Control + Key.Modifier.Shift))
      mnemonic    = Key.A.id

      override def apply = Try(self.saveAs()) recover showError
    }

    object quit extends Action("Quit") {
      accelerator = Some(KeyStroke(Key.Q, Key.Modifier.Control))
      mnemonic    = Key.Q.id

      override def apply = self.quit()
    }
  }

}
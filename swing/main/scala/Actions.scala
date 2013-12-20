package smartupedit
package swing

import smartupedit.io._

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

    object export extends Action("Export ...") {
      accelerator = Some(KeyStroke(Key.E, Key.Modifier.Control))
      mnemonic    = Key.E.id

      override def apply(): Unit = Try(self.export()) recover showError
    }

    object markup extends Action("Markup Type ...") {
      mnemonic = Key.M.id

      override def apply(): Unit = for {
        choice <- Dialog.showInput[Markup](
          parent = editor,
          title = "Choose Markup Type",
          message = "Choose the markup type for the conversion!",
          entries = Seq(Markup.Markdown, Markup.Textile),
          initial = self.markup
        )
      } self.markup = choice
    }

    object newFile extends Action("New") {
      accelerator = Some(KeyStroke(Key.N, Key.Modifier.Control))
      mnemonic    = Key.N.id

      override def apply(): Unit = self.newFile()
    }

    object open extends Action("Open") {
      accelerator = Some(KeyStroke(Key.O, Key.Modifier.Control))
      mnemonic    = Key.O.id

      override def apply(): Unit = Try(self.open()) recover showError
    }

    object save extends Action("Save") {
      accelerator = Some(KeyStroke(Key.S, Key.Modifier.Control))
      mnemonic    = Key.S.id

      override def apply(): Unit = Try(self.save()) recover showError
    }

    object saveAs extends Action("Save As") {
      accelerator = Some(KeyStroke(Key.S, Key.Modifier.Control + Key.Modifier.Shift))
      mnemonic    = Key.A.id

      override def apply(): Unit = Try(self.saveAs()) recover showError
    }

    object quit extends Action("Quit") {
      accelerator = Some(KeyStroke(Key.Q, Key.Modifier.Control))
      mnemonic    = Key.Q.id

      override def apply(): Unit = self.quit()
    }
  }

}

package smartupedit

import scala.swing._
import scala.swing.event._

trait Actions {

  self: SimpleMarkupEditor.type ⇒

  object action {
    object quit extends Action("Quit") {
      accelerator = Some(KeyStroke(Key.Q, Key.Modifier.Control))
      mnemonic    = Key.Q.id

      override def apply = self.quit()
    }
  }

}

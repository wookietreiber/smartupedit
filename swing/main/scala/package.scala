package smartupedit

import language.implicitConversions

import scala.swing.Dialog
import scala.swing.event.Key

package object swing {

  def KeyStroke(key: Key.Value, modifiers: Int = 0) =
    javax.swing.KeyStroke.getKeyStroke(key.id, modifiers)

  implicit def swing2dialogOption(from: Dialog.Result.Value): DialogOption.Result = from match {
    case Dialog.Result.Yes    => DialogOption.Yes
    case Dialog.Result.No     => DialogOption.No
    case Dialog.Result.Cancel => DialogOption.Cancel
    case Dialog.Result.Closed => DialogOption.Cancel
  }

}

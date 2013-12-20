package smartupedit

import language.implicitConversions

import scala.swing.Dialog
import scala.swing.event.Key

import javax.swing.KeyStroke

package object swing {

  def KeyStroke(key: Key.Value, modifiers: Int = 0): KeyStroke =
    javax.swing.KeyStroke.getKeyStroke(key.id, modifiers)

  implicit def swing2dialogresult(from: Dialog.Result.Value): DialogResult = from match {
    case Dialog.Result.Yes    => DialogResult.Yes
    case Dialog.Result.No     => DialogResult.No
    case Dialog.Result.Cancel => DialogResult.Cancel
    case Dialog.Result.Closed => DialogResult.Cancel
  }

}

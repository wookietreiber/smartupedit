import scala.swing.event.Key

package object smartupedit {

  def KeyStroke(key: Key.Value, modifiers: Int = 0) =
    javax.swing.KeyStroke.getKeyStroke(key.id, modifiers)

}

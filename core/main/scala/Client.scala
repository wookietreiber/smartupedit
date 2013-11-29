package smartupedit

import language.reflectiveCalls

import java.io.File

trait Client extends Converting {

  def editor: {
    def text: String
    def text_=(s: String)
  }

  def viewer: {
    def text: String
    def text_=(s: String)
  }

  def quit(): Unit

  def current: Option[File]
  def current_=(o: Option[File]): Unit

  def clear() = {
    editor.text = ""
  }

}

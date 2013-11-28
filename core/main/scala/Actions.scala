package smartupedit

import language.reflectiveCalls

import java.io.File

trait Actions {

  def editor: {
    def text: String
    def text_=(s: String): Unit
  }

  def current: Option[File]
  def current_=(o: Option[File]): Unit

  def clear() = {
    editor.text = ""
    current = None
  }

}

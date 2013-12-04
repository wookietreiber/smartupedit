package smartupedit

import language.reflectiveCalls

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

}

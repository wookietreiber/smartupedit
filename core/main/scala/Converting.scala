package smartupedit

import language.reflectiveCalls

import org.clapper.markwrap._

trait Converting {

  self: Client =>

  val parser = MarkWrap.parserFor(MarkupType.Markdown)

  def convert() = {
    viewer.text = parser.parseToHTML(editor.text)
  }

}

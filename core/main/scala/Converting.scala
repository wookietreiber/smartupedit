package smartupedit

import language.reflectiveCalls

import org.clapper.markwrap._

trait Converting {

  self: Client =>

  val parser: MarkWrapParser = MarkWrap.parserFor(MarkupType.Markdown)

  def convert(): Unit = {
    viewer.text = parser.parseToHTML(editor.text)
  }

}

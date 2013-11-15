package smartupedit

import org.clapper.markwrap._

trait Converting {

  val parser = MarkWrap.parserFor(MarkupType.Markdown)

  def convert(text: String): String =
    parser.parseToHTML(text)

}

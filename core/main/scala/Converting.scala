package smartupedit

import language.implicitConversions
import language.reflectiveCalls

import org.clapper.markwrap._

trait Converting {

  self: Client =>

  private var _markup: Markup =
    Markup.Markdown

  private var parser: MarkWrapParser =
    MarkWrap.parserFor(_markup)

  final def markup: Markup =
    _markup

  final def markup_=(m: Markup): Unit = {
    _markup = m
    parser = MarkWrap.parserFor(markup)
    convert()
  }

  final def convert(): Unit =
    convert1()

  private[smartupedit] def convert1(): Unit =
    viewer.text = parser.parseToHTML(editor.text)

  private implicit def markup2markuptype(markup: Markup): MarkupType = markup match {
    case Markup.Markdown => MarkupType.Markdown
    case Markup.Textile  => MarkupType.Textile
  }

}

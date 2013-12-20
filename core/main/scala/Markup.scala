package smartupedit

import org.clapper.markwrap._

sealed trait Markup

object Markup {
  case object Markdown extends Markup
  case object Textile extends Markup
}

package smartupedit

import scala.swing._
import scala.swing.event._

import org.clapper.markwrap._

object SimpleMarkupEditor extends SimpleSwingApplication {

  object top extends MainFrame {
    title = "Simple Markup Editor"
    contents = splitter
  }

  object splitter extends GridPanel(1, 2) {
    hGap = 10
    contents += new ScrollPane(editor)
    contents += new ScrollPane(viewer)
  }

  object editor extends TextArea {
    font = java.awt.Font decode "Monospaced"
  }

  val parser = MarkWrap.parserFor(MarkupType.Markdown)

  object viewer extends EditorPane("text/html", "")

  listenTo(editor)
  reactions += {
    case ValueChanged(`editor`) ⇒
      viewer.text = parser.parseToHTML(editor.text)
  }

}

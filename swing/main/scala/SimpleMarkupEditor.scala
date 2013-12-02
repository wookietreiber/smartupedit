package smartupedit
package swing

import java.io.File

import scala.swing._
import scala.swing.event._

import AskSave._

object SimpleMarkupEditor extends SimpleSwingApplication with Client with Actions with AskSave {

  object top extends MainFrame {
    title = "Simple Markup Editor"
    contents = splitter

    menuBar = new MenuBar {
      val file = new Menu("File")
      file.mnemonic = Key.F
      file.contents += new MenuItem(action.clear)
      file.contents += new MenuItem(action.open)
      file.contents += new Separator
      file.contents += new MenuItem(action.save)
      file.contents += new MenuItem(action.saveAs)
      file.contents += new MenuItem(action.export)
      file.contents += new Separator
      file.contents += new MenuItem(action.quit)

      contents += file
    }
  }

  object splitter extends GridPanel(1, 2) {
    hGap = 10
    contents += new ScrollPane(editor)
    contents += new ScrollPane(viewer)
  }

  object editor extends TextArea {
    font = java.awt.Font decode "Monospaced"
  }

  object viewer extends EditorPane("text/html", "")

  listenTo(editor)
  reactions += {
    case ValueChanged(`editor`) ⇒
      convert()
  }

  def chooseSaveTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showSaveDialog(over = editor) match {
      case FileChooser.Result.Approve ⇒
        Some(chooser.selectedFile)

      case _ ⇒ None
    }
  }

  def chooseOpenTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showOpenDialog(over = editor) match {
      case FileChooser.Result.Approve ⇒
        Some(chooser.selectedFile)

      case _ ⇒ None
    }
  }

  override def quit(): Unit = super.quit()

  def askSave = Dialog showConfirmation (
    parent = editor,
    message = "The changes you made to the buffer are not yet saved.\n\nDo you want to save them now?",
    title = "Save Changes?",
    optionType = Dialog.Options.YesNoCancel
  ) match {
    case Dialog.Result.Yes ⇒ AskSaveOption.Yes
    case Dialog.Result.No ⇒ AskSaveOption.No
    case Dialog.Result.Cancel ⇒ AskSaveOption.Cancel
  }

}

package smartupedit
package swing

import smartupedit.io._

import scala.swing._
import scala.swing.event._

object SimpleMarkupEditor extends SimpleSwingApplication with FileHandlingClient with Actions
    with AskSave with AskOverwrite {

  object top extends MainFrame {
    title = "Simple Markup Editor"
    contents = splitter

    menuBar = new MenuBar {
      val file = new Menu("File")
      file.mnemonic = Key.F
      file.contents += new MenuItem(action.newFile)
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

  def chooseExportTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showOpenDialog(over = editor) match {
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

  def chooseSaveTarget(dir: File = baseDir): Option[File] = {
    val chooser = new FileChooser(dir)

    chooser.showSaveDialog(over = editor) match {
      case FileChooser.Result.Approve ⇒
        Some(chooser.selectedFile)

      case _ ⇒ None
    }
  }

  override def quit(): Unit = super.quit()

  override def askSave = Dialog showConfirmation (
    parent = editor,
    title = "Save Changes?",
    message = "The changes you made to the buffer are not yet saved.\n\nDo you want to save them now?",
    optionType = Dialog.Options.YesNoCancel
  )

  override def askOverwrite = Dialog showConfirmation (
    parent = editor,
    title = "Overwrite This File?",
    message = "You are about to overwrite the chosen file.\n\nDo you really want to do this?",
    optionType = Dialog.Options.YesNoCancel
  )

}

package smartupedit
package io

import java.io.File
import scala.util.Try

case class MockClientState (
    hasChanged: Boolean = false,
    hasExported: Boolean = false,
    hasBeenAskedToOverwrite: Boolean = false,
    hasBeenAskedToSave: Boolean = false,
    hasOpened: Boolean = false,
    hasSaved: Boolean = false,
    hasQuit: Boolean = false
  ) {

  override def toString =
    s"""MockClientState(hasChanged=$hasChanged,hasExported=$hasExported,hasBeenAskedToOverwrite=$hasBeenAskedToOverwrite,hasBeenAskedToSave=$hasBeenAskedToSave,hasOpened=$hasOpened,hasSaved=$hasSaved,hasQuit=$hasQuit)"""

}

object MockClientState {

  def apply(client: FullMockClient): MockClientState = MockClientState (
    hasChanged              = client.hasChanged,
    hasExported             = client.hasExported,
    hasBeenAskedToOverwrite = client.hasBeenAskedToOverwrite,
    hasBeenAskedToSave      = client.hasBeenAskedToSave,
    hasOpened               = client.hasOpened,
    hasSaved                = client.hasSaved,
    hasQuit                 = client.hasQuit
  )

}

abstract class MockClient extends FileHandlingClient {

  var hasExported = false
  var hasOpened = false
  var hasQuit = false
  var hasSaved = false

  private[io] override def export(file: File): ActionResult = {
    hasExported = true
    ActionResult.Performed
  }

  private[io] override def open(file: File): ActionResult = {
    hasOpened = true
    ActionResult.Performed
  }

  override def quit(): Unit =
    hasQuit = true

  private[io] override def save(file: File): ActionResult = {
    hasSaved = true
    ActionResult.Performed
  }

}

class AskSaveMockClient(
    val askSaveOption: DialogResult = DialogResult.Yes,
    val fileChoice: Option[File] = Some(new File("test.md")))
  extends MockClient with MockAskSave with MockFileChooser
  with DummyEditor with DummyViewer

class AskOverwriteMockClient(
    val askOverwriteOption: DialogResult = DialogResult.Yes,
    val fileChoice: Option[File] = Some(new File("README.md")))
  extends MockClient with MockAskOverwrite with MockFileChooser
  with DummyEditor with DummyViewer

class FullMockClient(
    val askOverwriteOption: DialogResult = DialogResult.Yes,
    val askSaveOption: DialogResult = DialogResult.Yes,
    val fileChoice: Option[File] = Some(new File("README.md")))
  extends MockClient with MockAskOverwrite with MockAskSave with MockFileChooser
  with DummyEditor with DummyViewer

trait MockFileChooser extends FileHandling {

  def fileChoice: Option[File]

  def chooseExportTarget(dir: File = baseDir): Option[File] =
    fileChoice

  def chooseOpenTarget(dir: File = baseDir): Option[File] =
    fileChoice

  def chooseSaveTarget(dir: File = baseDir): Option[File] =
    fileChoice

}

trait MockAskOverwrite extends AskOverwrite {

  var hasBeenAskedToOverwrite = false

  def askOverwriteOption: DialogResult

  def askOverwrite() = {
    hasBeenAskedToOverwrite = true
    askOverwriteOption
  }

}

trait MockAskSave extends AskSave {

  var hasBeenAskedToSave = false

  def askSaveOption: DialogResult

  def askSave() = {
    hasBeenAskedToSave = true
    askSaveOption
  }

}

trait DummyEditor {
  object editor {
    var text = ""
  }
}

trait DummyViewer {
  object viewer {
    var text = ""
  }
}

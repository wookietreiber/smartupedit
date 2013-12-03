package smartupedit

import java.io.File
import scala.util.Try

case class MockClientState (
    hasChanged: Boolean = false,
    hasExported: Boolean = false,
    hasBeenAsked: Boolean = false,
    hasOpened: Boolean = false,
    hasSaved: Boolean = false,
    hasQuit: Boolean = false
  ) {

  override def toString =
    s"""MockClientState(hasChanged=$hasChanged,hasExported=$hasExported,hasBeenAsked=$hasBeenAsked,hasOpened=$hasOpened,hasSaved=$hasSaved,hasQuit=$hasQuit)"""
}

abstract class MockClient extends Client with FileHandling {

  var hasExported = false
  var hasOpened = false
  var hasQuit = false
  var hasSaved = false

  override def export(file: File) =
    hasExported = true

  override def open(file: File) =
    hasOpened = true

  override def quit(): Unit =
    hasQuit = true

  override def save(file: File) =
    hasSaved = true

}

class AskSaveMockClient(
    askSaveOption: DialogOption.Result = DialogOption.Yes,
    choose: Option[File] = Some(new File("test.md")))
  extends MockClient with AskSave with DummyEditor with DummyViewer {

  var hasBeenAsked = false

  def chooseExportTarget(dir: File = baseDir): Option[File] =
    None

  def chooseOpenTarget(dir: File = baseDir): Option[File] =
    choose

  def chooseSaveTarget(dir: File = baseDir): Option[File] =
    choose

  def askSave() = {
    hasBeenAsked = true
    askSaveOption
  }

}

class AskOverwriteMockClient(
    askOverwriteOption: DialogOption.Result = DialogOption.Yes,
    choose: Option[File] = Some(new File("README.md")))
  extends MockClient with AskOverwrite with DummyEditor with DummyViewer {

  var hasBeenAsked = false

  def askOverwrite() = {
    hasBeenAsked = true
    askOverwriteOption
  }

  def chooseExportTarget(dir: File = baseDir): Option[File] =
    choose

  def chooseOpenTarget(dir: File = baseDir): Option[File] =
    None

  def chooseSaveTarget(dir: File = baseDir): Option[File] =
    choose

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

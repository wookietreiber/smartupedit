package smartupedit

import java.io.File
import scala.util.Try

import AskSave._

abstract class MockClient extends Client with FileHandling {

  var hasQuit = false
  var hasSaved = false
  var hasOpened = false

  override def open(file: File) =
    hasOpened = true

  override def quit(): Unit =
    hasQuit = true

  override def save(file: File) =
    hasSaved = true

}

class AskSaveMockClient(
    askSaveOption: AskSaveOption.Result = AskSaveOption.Yes,
    choose: Option[File] = Some(new File("test.md")))
  extends MockClient with AskSave with DummyEditor with DummyViewer {

  var hasBeenAsked = false

  def chooseSaveTarget(dir: File = baseDir): Option[File] =
    choose

  def chooseOpenTarget(dir: File = baseDir): Option[File] =
    choose

  def askSave() = {
    hasBeenAsked = true
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

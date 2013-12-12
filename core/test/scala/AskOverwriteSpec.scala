package smartupedit
package io

import org.specs2._

import java.io.File

class AskOverwriteSpec extends Specification { def is = s2"""

  export action
    file missing no ask just export                                                       $e0
    file exists
      option yes                                                                          $e1
      option no                                                                           $e2
      option cancel                                                                       $e3

  save action
    file missing no ask just save                                                         $s0
    file exists
      option yes                                                                          $s1
      option no                                                                           $s2
      option cancel                                                                       $s3
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def e0 = {
    val client = new AskOverwriteMockClient(fileChoice = missing)
    client.exportAsk()
    MockClientState(client) === MockClientState (
      hasExported = true
    )
  }

  def e1 = {
    val client = new AskOverwriteMockClient()
    client.exportAsk()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true,
      hasExported = true
    )
  }

  def e2 = {
    val client = new AskOverwriteMockClient(DialogOption.No)
    client.exportAsk()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true
    )
  }

  def e3 = {
    val client = new AskOverwriteMockClient(DialogOption.Cancel)
    client.exportAsk()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true
    )
  }

  def s0 = {
    val client = new AskOverwriteMockClient(fileChoice = missing)
    client.save()
    MockClientState(client) === MockClientState (
      hasSaved = true
    )
  }

  def s1 = {
    val client = new AskOverwriteMockClient()
    client.save()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true,
      hasSaved = true
    )
  }

  def s2 = {
    val client = new AskOverwriteMockClient(DialogOption.No)
    client.save()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true
    )
  }

  def s3 = {
    val client = new AskOverwriteMockClient(DialogOption.Cancel)
    client.save()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true
    )
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  def missing  = Some(new File("missing.md"))

  implicit class RichMockClientState(underlying: MockClientState.type) {
    def apply(client: AskOverwriteMockClient): MockClientState = MockClientState (
      hasBeenAskedToOverwrite = client.hasBeenAskedToOverwrite,
      hasExported             = client.hasExported,
      hasSaved                = client.hasSaved
    )
  }

}

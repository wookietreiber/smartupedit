package smartupedit
package io

import org.specs2._

class AskSaveSpec extends Specification { def is = s2"""

  convert action
    converting sets changed to true                                                       $con1

  newFile action
    no changes no asking                                                                  $c1
    changes and option yes
      choosing a file                                                                     $c2a
      canceling choosing a file                                                           $c2b
    changes and option no                                                                 $c3
    changes and option cancel                                                             $c4

  open action
    no changes no asking                                                                  $o1
    changes and option yes
      choosing a file                                                                     $o2a
      canceling choosing a file                                                           $o2b
    changes and option no                                                                 $o3
    changes and option cancel                                                             $o4

  quit action
    no changes no asking                                                                  $q1
    changes and option yes
      choosing a file                                                                     $q2a
      canceling choosing a file                                                           $q2b
    changes and option no                                                                 $q3
    changes and option cancel                                                             $q4

  save action
    sets hasChanged to false                                                              $s1
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def con1 = {
    val client = new AskSaveMockClient()
    client.convert()
    client.hasChanged === true
  }

  def c1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = false
    client.newFile()
    MockClientState(client) === MockClientState()
  }

  def c2a = {
    val client = new AskSaveMockClient(DialogResult.Yes)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasBeenAskedToSave = true,
      hasSaved = true
    )
  }

  def c2b = {
    val client = new AskSaveMockClient(DialogResult.Yes, fileChoice = None)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true
    )
  }

  def c3 = {
    val client = new AskSaveMockClient(DialogResult.No)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasBeenAskedToSave = true
    )
  }

  def c4 = {
    val client = new AskSaveMockClient(DialogResult.Cancel)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true
    )
  }

  def o1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = false
    client.open()
    MockClientState(client) === MockClientState (
      hasOpened = true
    )
  }

  def o2a = {
    val client = new AskSaveMockClient(DialogResult.Yes)
    client.hasChanged = true
    client.open()
    MockClientState(client) === MockClientState (
      hasBeenAskedToSave = true,
      hasOpened = true,
      hasSaved = true
    )
  }

  def o2b = {
    val client = new AskSaveMockClient(DialogResult.Yes, fileChoice = None)
    client.hasChanged = true
    client.open()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true
    )
  }

  def o3 = {
    val client = new AskSaveMockClient(DialogResult.No)
    client.hasChanged = true
    client.open()
    MockClientState(client) === MockClientState (
      hasBeenAskedToSave = true,
      hasOpened = true
    )
  }

  def o4 = {
    val client = new AskSaveMockClient(DialogResult.Cancel)
    client.hasChanged = true
    client.open()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true
    )
  }

  def q1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = false
    client.quit()
    MockClientState(client) === MockClientState (
      hasQuit = true
    )
  }

  def q2a = {
    val client = new AskSaveMockClient(DialogResult.Yes)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasBeenAskedToSave = true,
      hasSaved = true,
      hasQuit = true
    )
  }

  def q2b = {
    val client = new AskSaveMockClient(DialogResult.Yes, fileChoice = None)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true
    )
  }

  def q3 = {
    val client = new AskSaveMockClient(DialogResult.No)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true,
      hasQuit = true
    )
  }

  def q4 = {
    val client = new AskSaveMockClient(DialogResult.Cancel)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAskedToSave = true
    )
  }

  def s1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = true
    client.save()
    MockClientState(client) === MockClientState (
      hasSaved = true
    )
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  implicit class RichMockClientState(underlying: MockClientState.type) {
    def apply(client: AskSaveMockClient): MockClientState = MockClientState (
      hasChanged         = client.hasChanged,
      hasBeenAskedToSave = client.hasBeenAskedToSave,
      hasOpened          = client.hasOpened,
      hasSaved           = client.hasSaved,
      hasQuit            = client.hasQuit
    )
  }

}

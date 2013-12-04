package smartupedit

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
    val client = new AskSaveMockClient(DialogOption.Yes)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasBeenAsked = true,
      hasSaved = true
    )
  }

  def c2b = {
    val client = new AskSaveMockClient(DialogOption.Yes, choose = None)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true
    )
  }

  def c3 = {
    val client = new AskSaveMockClient(DialogOption.No)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasBeenAsked = true
    )
  }

  def c4 = {
    val client = new AskSaveMockClient(DialogOption.Cancel)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true
    )
  }

  def o1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = false
    client.openAsk()
    MockClientState(client) === MockClientState (
      hasOpened = true
    )
  }

  def o2a = {
    val client = new AskSaveMockClient(DialogOption.Yes)
    client.hasChanged = true
    client.openAsk()
    MockClientState(client) === MockClientState (
      hasBeenAsked = true,
      hasOpened = true,
      hasSaved = true
    )
  }

  def o2b = {
    val client = new AskSaveMockClient(DialogOption.Yes, choose = None)
    client.hasChanged = true
    client.openAsk()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true
    )
  }

  def o3 = {
    val client = new AskSaveMockClient(DialogOption.No)
    client.hasChanged = true
    client.openAsk()
    MockClientState(client) === MockClientState (
      hasBeenAsked = true,
      hasOpened = true
    )
  }

  def o4 = {
    val client = new AskSaveMockClient(DialogOption.Cancel)
    client.hasChanged = true
    client.openAsk()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true
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
    val client = new AskSaveMockClient(DialogOption.Yes)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasBeenAsked = true,
      hasSaved = true,
      hasQuit = true
    )
  }

  def q2b = {
    val client = new AskSaveMockClient(DialogOption.Yes, choose = None)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true
    )
  }

  def q3 = {
    val client = new AskSaveMockClient(DialogOption.No)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true,
      hasQuit = true
    )
  }

  def q4 = {
    val client = new AskSaveMockClient(DialogOption.Cancel)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasChanged = true,
      hasBeenAsked = true
    )
  }

  def s1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = true
    client.saveAsk()
    MockClientState(client) === MockClientState (
      hasSaved = true
    )
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  implicit class RichMockClientState(underlying: MockClientState.type) {
    def apply(client: AskSaveMockClient): MockClientState = MockClientState (
      hasChanged   = client.hasChanged,
      hasBeenAsked = client.hasBeenAsked,
      hasOpened    = client.hasOpened,
      hasSaved     = client.hasSaved,
      hasQuit      = client.hasQuit
    )
  }

}

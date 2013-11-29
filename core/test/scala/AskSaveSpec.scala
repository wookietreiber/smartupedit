package smartupedit

import org.specs2._

import AskSave._

class AskSaveSpec extends Specification { def is = s2"""

  convert action
    converting sets changed to true                                                       $con1

  clear action
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
    client.clear()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = false,
      hasSaved = false
    )
  }

  def c2a = {
    val client = new AskSaveMockClient(AskSaveOption.Yes)
    client.hasChanged = true
    client.clear()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = true,
      hasSaved = true
    )
  }

  def c2b = {
    val client = new AskSaveMockClient(AskSaveOption.Yes, choose = None)
    client.hasChanged = true
    client.clear()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasSaved = false
    )
  }

  def c3 = {
    val client = new AskSaveMockClient(AskSaveOption.No)
    client.hasChanged = true
    client.clear()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = true,
      hasSaved = false
    )
  }

  def c4 = {
    val client = new AskSaveMockClient(AskSaveOption.Cancel)
    client.hasChanged = true
    client.clear()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasSaved = false
    )
  }

  def o1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = false
    client.openAsk()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = false,
      hasOpened = true,
      hasSaved = false
    )
  }

  def o2a = {
    val client = new AskSaveMockClient(AskSaveOption.Yes)
    client.hasChanged = true
    client.openAsk()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = true,
      hasOpened = true,
      hasSaved = true
    )
  }

  def o2b = {
    val client = new AskSaveMockClient(AskSaveOption.Yes, choose = None)
    client.hasChanged = true
    client.openAsk()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasOpened = false,
      hasSaved = false
    )
  }

  def o3 = {
    val client = new AskSaveMockClient(AskSaveOption.No)
    client.hasChanged = true
    client.openAsk()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = true,
      hasOpened = true,
      hasSaved = false
    )
  }

  def o4 = {
    val client = new AskSaveMockClient(AskSaveOption.Cancel)
    client.hasChanged = true
    client.openAsk()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasSaved = false
    )
  }

  def q1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = false
    client.quit()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = false,
      hasSaved = false,
      hasQuit = true
    )
  }

  def q2a = {
    val client = new AskSaveMockClient(AskSaveOption.Yes)
    client.hasChanged = true
    client.quit()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = true,
      hasSaved = true,
      hasQuit = true
    )
  }

  def q2b = {
    val client = new AskSaveMockClient(AskSaveOption.Yes, choose = None)
    client.hasChanged = true
    client.quit()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasSaved = false,
      hasQuit = false
    )
  }

  def q3 = {
    val client = new AskSaveMockClient(AskSaveOption.No)
    client.hasChanged = true
    client.quit()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasSaved = false,
      hasQuit = true
    )
  }

  def q4 = {
    val client = new AskSaveMockClient(AskSaveOption.Cancel)
    client.hasChanged = true
    client.quit()
    AskSaveState(client) === AskSaveState (
      hasChanged = true,
      hasBeenAsked = true,
      hasSaved = false
    )
  }

  def s1 = {
    val client = new AskSaveMockClient()
    client.hasChanged = true
    client.saveAsk()
    AskSaveState(client) === AskSaveState (
      hasBeenAsked = false,
      hasSaved = true
    )
  }

  // -----------------------------------------------------------------------------------------------
  // util
  // -----------------------------------------------------------------------------------------------

  case class AskSaveState(hasChanged: Boolean = false, hasBeenAsked: Boolean, hasOpened: Boolean = false,
    hasSaved: Boolean, hasQuit: Boolean = false) {
    override def toString =
      s"""AskSaveState(hasChanged=$hasChanged,hasBeenAsked=$hasBeenAsked,hasOpened=$hasOpened,hasSaved=$hasSaved,hasQuit=$hasQuit)"""
  }

  object AskSaveState {
    def apply(client: AskSaveMockClient): AskSaveState = AskSaveState (
      hasChanged   = client.hasChanged,
      hasBeenAsked = client.hasBeenAsked,
      hasOpened    = client.hasOpened,
      hasSaved     = client.hasSaved,
      hasQuit      = client.hasQuit
    )
  }

}

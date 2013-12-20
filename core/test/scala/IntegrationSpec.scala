package smartupedit
package io

import org.specs2._

class IntegrationSpec extends Specification { def is = s2"""

  action escalation

    new file action on a client with changed content
      must not trigger while asking to save but canceling the overwrite                   $n1

    open file action on a client with changed content
      must not trigger while asking to save but canceling the overwrite                   $o1

    quit action on a client with changed content
      must not trigger while asking to save but canceling the overwrite                   $q1
                                                                                                 """
  // -----------------------------------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------------------------------

  def n1 = {
    val client = new FullMockClient(askOverwriteOption = DialogResult.Cancel)
    client.hasChanged = true
    client.newFile()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true,
      hasBeenAskedToSave = true,
      hasChanged = true
    )
  }

  def o1 = {
    val client = new FullMockClient(askOverwriteOption = DialogResult.Cancel)
    client.hasChanged = true
    client.open()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true,
      hasBeenAskedToSave = true,
      hasChanged = true
    )
  }

  def q1 = {
    val client = new FullMockClient(askOverwriteOption = DialogResult.Cancel)
    client.hasChanged = true
    client.quit()
    MockClientState(client) === MockClientState (
      hasBeenAskedToOverwrite = true,
      hasBeenAskedToSave = true,
      hasChanged = true
    )
  }

}

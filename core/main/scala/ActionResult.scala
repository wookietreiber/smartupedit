package smartupedit

sealed trait ActionResult

case object ActionPerformed extends ActionResult
case object ActionEscalate extends ActionResult

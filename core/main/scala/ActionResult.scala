package smartupedit

sealed trait ActionResult

object ActionResult {
  case object Performed extends ActionResult
  case object Escalate extends ActionResult
}

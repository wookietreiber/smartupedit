package smartupedit

sealed trait DialogResult

object DialogResult {
  object Yes extends DialogResult
  object No extends DialogResult
  object Cancel extends DialogResult
}

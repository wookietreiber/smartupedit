package smartupedit

object DialogOption {
  sealed abstract class Result

  object Yes extends Result
  object No extends Result
  object Cancel extends Result
}

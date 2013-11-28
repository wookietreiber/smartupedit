package smartupedit

trait Client extends Actions with Converting with FileHandling {
  def quit(): Unit
}

package smartupedit

import language.reflectiveCalls

trait AskOverwrite extends FileHandling {

  def askOverwrite(): DialogOption.Result

  abstract override def export(file: File) =
    if (!file.exists || askOverwrite() == DialogOption.Yes)
      super.export(file)

  abstract override def save(file: File) =
    if (isCurrent(file) || !file.exists || askOverwrite() == DialogOption.Yes)
      super.save(file)

}

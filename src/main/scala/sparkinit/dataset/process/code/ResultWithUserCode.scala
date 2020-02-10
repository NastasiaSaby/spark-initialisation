package sparkinit.dataset.process.code

import sparkinit.abtest.Result

//Object companion de la case class ResultWithUserCode
object ResultWithUserCode {
  def fromAbResult(result: Result): ResultWithUserCode = {
    ResultWithUserCode(
      result.user,
      result.color,
      result.view,
      result.user + "A"
    )
  }
}

case class ResultWithUserCode(user: String, color: String, view: Int, codeUser: String)

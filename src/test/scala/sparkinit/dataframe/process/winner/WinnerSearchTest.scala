package sparkinit.dataframe.process.winner

import org.apache.spark.sql.DataFrame
import org.scalatest.FunSuite
import sparkinit.SparkSessionTestWrapper
import sparkinit.abtest.Result

class WinnerSearchTest extends FunSuite with SparkSessionTestWrapper {
  import spark.implicits._

  test("Get the color blue as the winner") {

    val abTestResult: DataFrame = Seq(
      Result("user1", "blue", 5),
      Result("user2", "green", 1),
      Result("user1", "blue", 2),
      Result("user1", "blue", 1),
      Result("user3", "blue", 1),
      Result("user4", "error", 3),
      Result("user3", "error", 1)
    ).toDF

    val result: String = WinnerSearch.defineWinner(abTestResult)
    assert(result === "blue")
  }
}

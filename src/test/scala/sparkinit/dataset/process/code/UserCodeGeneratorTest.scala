package sparkinit.dataset.process.code

import org.apache.spark.sql.Dataset
import org.scalatest.FunSuite
import sparkinit.SparkSessionTestWrapper
import sparkinit.abtest.Result

class UserCodeGeneratorTest extends FunSuite with SparkSessionTestWrapper {
  import spark.implicits._

  test("Generate user code") {

    val abTestResult: Dataset[Result] = Seq(
      Result("user1", "blue", 5),
      Result("user2", "green", 1),
      Result("user1", "blue", 2),
      Result("user1", "blue", 1),
      Result("user3", "blue", 1),
      Result("user4", "error", 3),
      Result("user3", "error", 1)
    ).toDS

    val result: Dataset[ResultWithUserCode] = UserCodeGenerator.generateUserCode(abTestResult, spark)

    /* Nous voulons faire plusieurs éléments avec notre dataset
       comme compter le nombre de résultats ou regarder ce qu'il y a à l'intérieur.
       Afin d'éviter de relancer les transformations plusieurs fois,
       nous revenons dans l'API Scala pour effectuer nos tests.
    */
    val resultAsScalaObject: Array[ResultWithUserCode] = result.collect

    assert(resultAsScalaObject.length === 7)
    assert(resultAsScalaObject.count(_.codeUser == "user1A") === 3)
  }
}

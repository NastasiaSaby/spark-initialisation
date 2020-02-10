package sparkinit.dataset.process.code

import org.apache.spark.sql.{Dataset, SparkSession}
import sparkinit.abtest.Result

object UserCodeGenerator {
  def generateUserCode(results: Dataset[Result], spark: SparkSession): Dataset[ResultWithUserCode] = {
    import spark.implicits._

    results.map(ResultWithUserCode.fromAbResult)
  }
}

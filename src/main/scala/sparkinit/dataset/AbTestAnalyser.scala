package sparkinit.dataset

import sparkinit.abtest.Result
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoders, SparkSession}
import sparkinit.configuration.SimpleConfig
import sparkinit.dataset.process.winner.WinnerSearch

object AbTestAnalyser {
  def main(args: Array[String]) {

    //val spark: SparkSession = SparkSession.builder.getOrCreate

    //Version local pour tester, debuguer au pas à pas
    val spark: SparkSession = SparkSession.builder.master("local").getOrCreate

    //Pour faire des conversions implicites vers les datasets
    import spark.implicits._

    //A partir de la case class, on crée un schéma
    val schema: StructType = Encoders.product[Result].schema

    val abTestResult: Dataset[Result] = spark.
      read.
      option("header", "true").
      schema(schema).
      csv(SimpleConfig.getConfig.company.resultPath).
      as[Result]

    val winnerColor: String = WinnerSearch.defineWinner(abTestResult, spark)

    println(winnerColor)

    spark.stop
  }
}

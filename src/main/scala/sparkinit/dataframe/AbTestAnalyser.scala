package sparkinit.dataframe

import sparkinit.abtest.Result
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}
import sparkinit.configuration.{Company, SimpleConfig}
import sparkinit.dataframe.process.winner.WinnerSearch

object AbTestAnalyser {
  def main(args: Array[String]) {
    //val spark: SparkSession = SparkSession.builder.getOrCreate

    //Version local pour tester, debuguer au pas à pas
    val spark: SparkSession = SparkSession.builder.master("local").getOrCreate

    //Pour faire des conversions implicites vers les datasets
    import spark.implicits._

    //A partir de la case class, on crée un schéma
    val schema: StructType = Encoders.product[Result].schema

    val config: Company = SimpleConfig.getConfig

    val abTestResult: DataFrame = spark.
      read.
      option("header", "true").
      schema(schema).
      csv(config.company.resultPath)

    val winnerColor: String = WinnerSearch.defineWinner(abTestResult)

    println(winnerColor)

    spark.stop
  }
}

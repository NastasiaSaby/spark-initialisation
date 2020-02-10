package sparkinit

import org.apache.spark.sql.SparkSession

trait SparkSessionTestWrapper {
  val spark: SparkSession = SparkSession.builder.master("local").getOrCreate
}

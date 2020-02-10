package sparkinit.window

import org.apache.spark.sql.{DataFrame, Dataset, Row}
import org.scalatest.FunSuite
import sparkinit.{Generators, SparkSessionTestWrapper}
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions._

class DefineDuplicatesTest extends FunSuite with SparkSessionTestWrapper {
  import spark.implicits._

  test("define duplicates") {
    val patrimoineModel: Patrimoine = Generators.patrimoineGen

    val patrimoine: Patrimoine = patrimoineModel.
      withSerialNumber("A").
      withAMR("1").
      withShortName("duplicate1")

    val patrimoine2: Patrimoine = patrimoineModel.
      withSerialNumber("A").
      withAMR("2").
      withShortName("duplicate1")

    val patrimoine3: Patrimoine = patrimoineModel.
      withSerialNumber("B").
      withAMR("3")

    val patrimoine4: Patrimoine = patrimoineModel.
      withAMR("C").
      withSerialNumber("1")

    val patrimoine5: Patrimoine = patrimoineModel.
      withAMR("D").
      withSerialNumber("2").
      withShortName("duplicate2")

    val patrimoine6: Patrimoine = patrimoineModel.
      withAMR("D").
      withSerialNumber("3").
      withShortName("duplicate2")

    val patrimoines: DataFrame = Seq(
      patrimoine,
      patrimoine2,
      patrimoine3,
      patrimoine4,
      patrimoine5,
      patrimoine6
    ).toDF

    val bySerialNumber: WindowSpec = Window.partitionBy('SerialNumber)
    val byAMR: WindowSpec = Window.partitionBy('AMR)

    val result: DataFrame = patrimoines.
      withColumn("countSerialNumber", count("*") over bySerialNumber).
      withColumn("countAMR", count("*") over byAMR).
      where("countSerialNumber > 1 or countAMR > 1").
      distinct

    assert(result.collect.map(_.get(2)).toSet === Set("duplicate1", "duplicate2"))
  }
}

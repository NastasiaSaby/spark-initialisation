package sparkinit.dataframe.process.winner

import org.apache.spark.sql.functions.{desc, sum}
import org.apache.spark.sql.{DataFrame, Dataset, RelationalGroupedDataset, Row}

object WinnerSearch {
  def defineWinner(result: DataFrame): String = {
    val resultWithoutError: DataFrame = filterErrors(result)

    val viewsByColor: DataFrame = defineViewsByColor(resultWithoutError)

    val highestViewsByColor: Dataset[Row] = getHighestViewsByColor(viewsByColor)

    highestViewsByColor.collect.head.getAs[String](0)
  }

  private def filterErrors(result: DataFrame): DataFrame = {
    //Equivalent de "abTestingResult.filter(result => result.color != "Error")"
    //Si le champ "color" n'existe pas => plantage
    result.filter("color != 'error'")
  }

  private def defineViewsByColor(resultWithoutError: DataFrame): DataFrame = {
    //On groupe les éléments dans l'API Dataset
    //Encore une fois si le champ "color" n'existe pas => plantage
    val groupedResults: RelationalGroupedDataset = resultWithoutError.
      groupBy("color")

    //On applique l'aggregator qui renvoie un Dataset de Tuple avec la clef et ViewsByColor
    groupedResults.
      agg(sum("view").as("viewsCount"))
  }

  private def getHighestViewsByColor(viewsByColor: DataFrame): DataFrame = {
    viewsByColor.
      orderBy(desc("viewsCount")).
      limit(1)
  }
}

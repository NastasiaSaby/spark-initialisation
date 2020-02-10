package sparkinit.dataset.process.winner

import org.apache.spark.sql.{Dataset, KeyValueGroupedDataset, SparkSession}
import sparkinit.abtest.{Result, ViewAddition, ViewsByColor}

object WinnerSearch {
  def defineWinner(result: Dataset[Result], spark: SparkSession): String = {
    val resultWithoutError: Dataset[Result] = filterErrors(result)

    val viewsByColor: Dataset[ViewsByColor] = defineViewsByColor(resultWithoutError, spark)

    val highestViewsByColor: ViewsByColor = getHighestViewsByColor(viewsByColor)

    highestViewsByColor.color
  }

  private def filterErrors(result: Dataset[Result]): Dataset[Result] = {
    //Equivalent de "abTestingResult.filter(result => result.color != "Error")"
    //Si le champ "color" n'existe pas => plantage
    result.filter(_.color != "error")
  }

  private def defineViewsByColor(resultWithoutError: Dataset[Result], spark: SparkSession): Dataset[ViewsByColor] = {
    import spark.implicits._

    //On groupe les éléments dans l'API Dataset
    //Encore une fois si le champ "color" n'existe pas => plantage
    val groupedResults: KeyValueGroupedDataset[String, Result] = resultWithoutError.
      groupByKey(_.color)

    //On applique l'aggregator qui renvoie un Dataset de Tuple avec la clef et ViewsByColor
    val viewByColorWithKey: Dataset[(String, ViewsByColor)] =
      groupedResults.
        agg(new ViewAddition().toColumn.name("viewAddition"))

    //On ne garde que l'élément ViewsByColor
    viewByColorWithKey.map(oneViewByColorWithKey => {
      val (key, viewsByColor) = oneViewByColorWithKey
      viewsByColor
    })
  }

  private def getHighestViewsByColor(viewsByColor: Dataset[ViewsByColor]): ViewsByColor = {
    viewsByColor.
      reduce((viewsByColor1: ViewsByColor, viewsByColor2: ViewsByColor) => {
        if (viewsByColor1.totalViews > viewsByColor2.totalViews) {
          viewsByColor1
        } else {
          viewsByColor2
        }
      })
  }
}

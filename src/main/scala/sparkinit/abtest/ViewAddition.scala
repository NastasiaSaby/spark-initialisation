package sparkinit.abtest

import org.apache.spark.sql._
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.expressions._

class ViewAddition extends Aggregator[Result, Array[Result], ViewsByColor] {

  def zero: Array[Result] = Array.empty[Result] // The initial value.

  def reduce(results: Array[Result], result: Result): Array[Result] = results :+ result // Add an element to the running total
  def merge(results1: Array[Result], results2: Array[Result]) = results1 ++ results2 // Merge intermediate values.
  def finish(results: Array[Result]): ViewsByColor = {
    ViewsByColor(
      results.head.color,
      results.map(_.view).sum
    )
  } // Return the final result.

  def bufferEncoder: Encoder[Array[Result]] = ExpressionEncoder()

  def outputEncoder: Encoder[ViewsByColor] = ExpressionEncoder()
}

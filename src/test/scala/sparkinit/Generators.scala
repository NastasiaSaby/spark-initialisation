package sparkinit

import org.scalacheck.{Arbitrary, Gen}
import sparkinit.window.Patrimoine

object Generators {

  val patrimoineGen: Patrimoine = Patrimoine(
    Gen.choose(0, 300000).sample.get,
    Gen.choose(0, 300000).sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    Arbitrary.arbString.arbitrary.sample.get,
    "serialNumber",
    "amr"
  )
}

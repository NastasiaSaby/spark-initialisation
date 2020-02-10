package sparkinit.window

case class Patrimoine(
                       ReadingPointId: Int,
                       DateDerniereMaj: Long,
                       ShortName: String,
                       Estate_Name: String,
                       AddressLine1: String,
                       AddressLine2: String,
                       PostCode: String,
                       Town: String,
                       Latitude: String,
                       Longitude: String,
                       SerialNumber: String,
                       AMR: String
                     ) {
  def withSerialNumber(newSerialNumber: String): Patrimoine = copy(SerialNumber = newSerialNumber)
  def withAMR(newAMR: String): Patrimoine = copy(AMR = newAMR)
  def withShortName(newShortName: String): Patrimoine = copy(ShortName = newShortName)
}

package sparkinit.configuration

case class Company(company: CompanyDetails)

case class CompanyDetails(
                           resultPath: String,
                           fullName: String,
                           started: Int,
                           employees: String,
                           offices: List[String],
                           officesInIndia: Map[String, String],
                           extraActivity: Option[String])

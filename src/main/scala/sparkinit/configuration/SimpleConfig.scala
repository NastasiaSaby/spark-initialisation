package sparkinit.configuration

import pureconfig.error.ConfigReaderFailures
import pureconfig.loadConfig
import pureconfig.generic.auto._

object SimpleConfig {
  def getConfig: Company = {
    val simpleConfig: Either[ConfigReaderFailures, Company] = loadConfig[Company]

    val configuration: Option[Company] = simpleConfig match {
      case Left(ex) => {
        ex.toList.foreach(println)
        None
      }
      case Right(config) => {
        println(s"Result Path ${config.company.resultPath}")
        println(s"Company's Name ${config.company.fullName}")
        println(s"Company started at ${config.company.started}")
        println(s"Company's strength is ${config.company.employees}")
        println(s"Company's presence are in  ${config.company.offices}")
        println(s"Company's office in India are  ${config.company.officesInIndia}")
        println(s"Company's extra activity is  ${config.company.extraActivity}")

        Some(config)
      }
    }

    configuration.getOrElse(throw new Exception("Errors in configuration"))
  }
}

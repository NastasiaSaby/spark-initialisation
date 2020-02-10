name := "SparkInit"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.11.1"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % Test
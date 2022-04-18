ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.13.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.0"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.2.0"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "Capstone_Project"
  )

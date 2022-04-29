ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.12.10"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.0"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.0.0"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.0.0"
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.0.0"

//libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
//libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"
//libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.3.0"
//libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.6.0"
//libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.2.0"
//libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % "2.4.8"
//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.1"
//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.1"
//libraryDependencies += "com.snowplowanalytics" %% "scala-forex" % "2.0.0"
//libraryDependencies += "com.lambdista" %% "money" % "0.8.0"

lazy val root = (project in file("."))
  .settings(
    name := "Capstone_Project"
  )

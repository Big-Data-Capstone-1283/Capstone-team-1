package Clover.data
import org.apache.spark.sql.{DataFrame, SparkSession}
class DataGenerator(spark:SparkSession){

  val companies: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/sites.csv").toDF()
  val customers: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/").toDF()

}

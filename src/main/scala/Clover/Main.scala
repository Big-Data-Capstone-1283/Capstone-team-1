package Clover
import Clover.Analysis.CAnalysis
import Clover.data._
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.sql.SparkSession
object Main {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    System.setProperty("hadoop.home.dir", "C:\\hadoop")
    val spark = SparkSession
      .builder()
      .appName("Capstone Project")
      .config("spark.master","local[*]")
      .enableHiveSupport()
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    println("created spark session")
    import spark.implicits._
    val sc = spark.sparkContext
    val analysis = new CAnalysis(spark)
    //val cons = new Clover.Kafka.Consumer(spark)
    //cons.TestBatch()



  }
}

package Clover
import org.apache.spark.sql.SparkSession
import data._
import org.apache.spark.sql.functions.from_unixtime
import org.apache.spark.sql.functions.{to_timestamp,to_utc_timestamp}
//import org.apache.spark.sql.types.TimestampType
import java.sql.Timestamp
import org.apache.kafka.clients.producer.{KafkaProducer,ProducerConfig,ProducerRecord}
import org.apache.spark.streaming._
import java.io.PrintWriter
import org.apache.log4j.{Level, Logger}
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
    val sc = spark.sparkContext
    val fm = new Formulas(spark)
    val saveInflation = () => {
      fm.SetInflation()
      val printer = new PrintWriter("inflation.csv")
      printer.println("year,price")
      var year = 2000
      while (year < 2022) {
        printer.println(year + "," + fm.ApplyInflation(1000, year))
        year += 1
      }
      printer.close()
    }
    import spark.implicits._
    //val a = new Timestamp(946684800000L)
    //print(a)
    //val thing = new DateTime()
    //thing.EpochTimeGenerationTest1()

    println(fm.Convert("Japan",100))
    println(fm.Convert("United Kingdom",50))
    println(fm.Convert("South Korea",3000))
    println(fm.Convert("VEF",3))
    //println(3600000L*24)
    //1 hour is: 3600000 milliseconds
    //1 day is: 86400000 milliseconds
    //30 day month is: 2,592,000,000 milliseconds






  }
}

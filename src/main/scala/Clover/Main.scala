package Clover
import Clover.Kafka.{Consumer, Producer}
import org.apache.spark.sql.SparkSession
import org.apache.spark._
import data._
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
    val desiredTime = "1/1/2002 00:00:01"

    val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
    val time = format.parse(desiredTime).getTime()
    print(time)
    val desiredStartTime = "1/1/2000 00:00:01"
    val desiredEndTime = "1/1/2003 00:00:01"
    val time1 = format.parse(desiredStartTime).getTime()
    val time2 = format.parse(desiredEndTime).getTime()
    val companyTaperPoints:Array[Long] = new Array(2)
    companyTaperPoints(0) = time1
    companyTaperPoints(1) = time2
    val companySalesRates:Array[Double] = new Array(2)
    companySalesRates(0) = 10
    companySalesRates(1) = 20
    val companySalesVariation:Array[Double] = new Array(2)
    companySalesVariation(0) = 5
    companySalesVariation(1) = 10

    val dateProcessor = new DateTime()
   val returnedArrayTimes = dateProcessor.dateTimeGenerationTaperPoints("Blockbuster", time1, time2, companyTaperPoints, companySalesRates, companySalesVariation)
    //println(returnedArrayTimes.toList)
    val returnedList = returnedArrayTimes.toList
    returnedList.foreach(println)
    /**
     * val desiredTime = "3/20/2017 16:5:45"

val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
val time = format.parse(desiredTime).getTime()
print(time)

     *
     *

    val thing = new Clover.data.DateTime()
    //Clover.data.DateTime.EpochTimeGenerationTest1(thing)
    val startTimeNormal = "1/1/2000 00:01:01"
    val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
    val endTimeNormal = "1/1/2002 00:01:01"
    val endTimeMS = format.parse(startTimeNormal).getTime()
 //   val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
   // val time = format.parse(desiredTime).getTime()
    print(time)
*/
  }
}

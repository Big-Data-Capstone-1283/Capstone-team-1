package Clover
import Clover.Kafka.Producer
import org.apache.spark.sql.SparkSession

import java.sql.Timestamp
import org.apache.spark._
import data._
import org.apache.log4j.{Level, Logger}

import scala.collection.mutable.HashMap
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







  }
}

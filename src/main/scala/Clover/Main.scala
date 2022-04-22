package Clover
import org.apache.spark.sql.SparkSession

import java.sql.Timestamp
//import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.apache.kafka.common.serialization.{StringDeserializer,StringSerializer}
//import org.apache.spark.streaming.kafka010._
//import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
//import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark._
import data._
//import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
//import org.apache.spark.streaming._
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
    //val prod = new Clover.Kafka.Producer(spark)
    //prod.Batch()
    val cons = new Clover.Kafka.Consumer(spark)
    cons.TestBatch()
    val rand = new Clover.Tools.Random()

  }
}

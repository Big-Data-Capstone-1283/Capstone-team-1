package Clover
import org.apache.spark.sql.SparkSession

import java.sql.Timestamp
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.{StringDeserializer,StringSerializer}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark._
import data._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.streaming._
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
    val ssc = new StreamingContext(sc,Seconds(1))
    import spark.implicits._
    //val a = new Timestamp(946684800000L)
    //print(a)
    //val thing = new DateTime()
    //thing.EpochTimeGenerationTest1()
    //println(3600000L*24)
    //1 hour is: 3600000 milliseconds
    //1 day is: 86400000 milliseconds
    //30 day month is: 2,592,000,000 milliseconds

    val kafkaParams = Map[String, Object](
      "bootstrap.servers"->"172.26.93.148:9092",
      "key.deserializer"->classOf[StringDeserializer],
      "value.deserializer"->classOf[StringDeserializer],
      "group.id"->"use_a_separate_group_id_for_each_stream",
      "auto.offset.reset"->"latest",
      "enable.auto.commit"->(false: java.lang.Boolean)
    )

    //case class row(oid:Int,cid:Int,cn:String,pid:Int,pn:String,pc:String,pt:String,qt:Int,p:Double,d:Timestamp,co:String,ci:String,web:String,ptid:Int,pts:String,fr:String)
    //val a = row(1,1,"billy",1,"thingy","stuffs","money",10,5.95,new Timestamp(946684800000L),"place","smaller place","www.internet.com",1,"Y","")
    //val df = Seq(a).toDF()
    /*df.foreachPartition(partition=>{
      val props = Map[String, Object](
        "bootstrap.servers"->"172.26.93.148:9092",
        "key.serializer"->classOf[StringSerializer],
        "value.serializer"->classOf[StringSerializer],
        //"group.id"->"testing2",
        //"enable.auto.commit"->(false: java.lang.Boolean)
      )
      partition.foreach(f =>{

      })
    })*/


    val ss = new StringSerializer
    val sd = new StringDeserializer
    val a:Array[Byte] = ss.serialize("","Hidy ho neighborino")

    a.foreach(println)



    val topic = Array("streaming")
    val stream = KafkaUtils.createDirectStream[String,String](
      ssc,
      PreferConsistent,
      Subscribe[String,String](topic,kafkaParams)
    )
    //stream.map(record =>(record.value().toString)).print
    //ssc.start()
    //ssc.awaitTermination()






  }
}

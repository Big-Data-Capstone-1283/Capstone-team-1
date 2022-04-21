package Clover.Kafka
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.streaming._
import Clover.data.Row
import scala.collection.mutable.ArrayBuffer

import java.sql.Timestamp
class Producer(spark:SparkSession){
  import spark.implicits._
  val row: Row = Row(1,1,"bob",1,"thingy","stuffs","money",1,1.25,new Timestamp(946684800000L),"place","smaller place","www.web.com",1,"Y","")
  val row2: Row = Row(2,2,"frank",2,"thingy","stuffs","money",2,1.25,new Timestamp(946684800000L),"place","smaller place","www.web.com",2,"Y","")
  val rows:ArrayBuffer[Row] = ArrayBuffer(row,row2)
  for(x<- 3 until 5000){
      rows.append(Row(x,x,"person",x,"something","what","cash",x,2.79,new Timestamp(946684800000L),"somewhere","overrainbow","www.com",x,"Y",""))
    }
  val ds: Dataset[Row] = rows.toSeq.toDS
  println(ds.count())
  ds.rdd.collect().foreach(row=>
    println(row.toString.replace("Row","").replace("(","").replace(")",""))
  )
  //println(ds.rdd.collect()(0).toString.replace("Row","").replace("(","").replace(")",""))
  /*val products: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/products.csv")
  products.rdd.collect().foreach(row=>
    println(row.toString().replace("Row","").replace("(","").replace(")",""))
  )*/
}
/*
//val ssc = new StreamingContext(sc,Seconds(1))
//import spark.implicits._
//val a = new Timestamp(946684800000L)
//print(a)
//val thing = new DateTime()
//thing.EpochTimeGenerationTest1()
//println(3600000L*24)
//1 hour is: 3600000 milliseconds
//1 day is: 86400000 milliseconds
//30 day month is: 2,592,000,000 milliseconds

/*val kafkaParams = Map[String, Object](
  "bootstrap.servers"->"172.26.93.148:9092",
  "key.deserializer"->classOf[StringDeserializer],
  "value.deserializer"->classOf[StringDeserializer],
  "group.id"->"use_a_separate_group_id_for_each_stream",
  "auto.offset.reset"->"latest",
  "enable.auto.commit"->(false: java.lang.Boolean)
)*/

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


/*val ss = new StringSerializer
val sd = new StringDeserializer
val a:Array[Byte] = ss.serialize("","Hidy ho neighborino")

a.foreach(println)*/



/*val topic = Array("streaming")
val stream = KafkaUtils.createDirectStream[String,String](
  ssc,
  PreferConsistent,
  Subscribe[String,String](topic,kafkaParams)
)*/
//stream.map(record =>(record.value().toString)).print
//ssc.start()
//ssc.awaitTermination()
*/

package Clover.Kafka
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}
import org.apache.spark.sql.{DataFrame, Encoders}

import java.text.SimpleDateFormat
//import org.apache.spark.streaming.kafka010._
//import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
//import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.spark.sql.SparkSession
//import org.apache.spark.streaming._
import Clover.data.Row

import java.util.Properties
class Producer(spark:SparkSession){
  import spark.implicits._

  def Stream(): Unit={

  }

  def Batch(): Unit ={

    val schema = Encoders.product[Row].schema
    /*val transactions2: Dataset[Row] = (spark.read.format("csv")
      .option("delimiter", ",")
      .option("header", "true")
      .schema(schema)
      .load("src/main/scala/Clover/data/files/BaseData/transactions.csv")
      .as[Row])*/
    val transactions: DataFrame = spark.read.format("csv")
      .option("delimiter", ",")
      .option("header", "true")
      .option("infer.schema","true")
      .load("src/main/scala/Clover/data/files/BaseData/transactions.csv")

    val topicName = "team1"
    val prop = new Properties()
    prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"ec2-3-93-174-172.compute-1.amazonaws.com:9092")
    prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[IntegerSerializer].getName)
    prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

    val f =(x: String)=>{val format = new SimpleDateFormat("M/dd/yyy");format.parse(x).getTime}

    val prod = new KafkaProducer[Int,String](prop)
    var count = 1
    transactions.collect().foreach(row=>{
      prod.send(new ProducerRecord[Int,String](topicName,count,row.toString.replaceAll("Row(|)","")))
      println(count)
      count +=1
    })

    //ds.rdd.collect().foreach(row=>
      //prod.send(new ProducerRecord[Int, String](topicName,row.order_id,row.toString.replace("Row","").replace("(","").replace(")","")))
    //)
    prod.flush()
  }
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

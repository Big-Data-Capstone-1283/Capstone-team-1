package Clover.Kafka
import java.time.Duration
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.common.serialization.{IntegerDeserializer, IntegerSerializer, StringDeserializer, StringSerializer}
//import org.apache.spark.streaming.kafka010._
//import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
//import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession, functions}
//import org.apache.spark.streaming._
import Clover.data.Row
import org.apache.kafka.clients.consumer.ConsumerConfig.{BOOTSTRAP_SERVERS_CONFIG, GROUP_ID_CONFIG, KEY_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS_CONFIG}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.col

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import java.sql.Timestamp
import java.util.Properties
import java.util.regex.Pattern
import org.apache.spark.sql.functions.split

class Consumer(spark:SparkSession){

  //val ssc = new StreamingContext(spark.sparkContext, Seconds(1))



  /*val kafkaParams = Map[String, Object](
    "bootstrap.servers"->"172.26.93.148:9092",
    "key.deserializer"->classOf[IntegerDeserializer],
    "value.deserializer"->classOf[StringDeserializer],
    "group.id"->"group-id-2",
    "auto.offset.reset"->"latest",
    "enable.auto.commit"->(false: java.lang.Boolean)
  )*/

  //val topic = Array("streaming")
  //val stream = KafkaUtils.createDirectStream[String,String](
  //  ssc,
  //  PreferConsistent,
  //  Subscribe[String,String](topic,kafkaParams)
  //)

  //stream.map(record =>(record.value().toString)).print
  //ssc.start()
  //ssc.awaitTermination()

  def TestBatch():Unit={
    import spark.implicits._
    val topics: Pattern = Pattern.compile("team2")
    val prop = new Properties()
    prop.setProperty(BOOTSTRAP_SERVERS_CONFIG, "ec2-3-93-174-172.compute-1.amazonaws.com:9092")
    prop.setProperty(GROUP_ID_CONFIG, "team-1")
    prop.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    prop.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    prop.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
    prop.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "false")
    //prop.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000")
    val consumer: KafkaConsumer[String,String] = new KafkaConsumer(prop)
    var count = 0
    try{
      consumer.subscribe(topics)
      while(true){
        val buffer: ArrayBuffer[String] = ArrayBuffer()
        val records: ConsumerRecords[String,String] = consumer.poll(Duration.ofMinutes(1L))
        records.records("team2").forEach(x=>buffer.append(x.value()))

        val ar2: RDD[String] = spark.sparkContext.parallelize(buffer.toSeq)
        val data: DataFrame = ar2.toDF().select("*")
        data.show(false)
        val df2 : DataFrame = data.select(
          split(col("value"), ",").getItem(0).as("order_id"),
          split(col("value"), ",").getItem(1).as("customer_id"),
          split(col("value"), ",").getItem(2).as("customer_name"),
          split(col("value"), ",").getItem(3).as("product_id"),
          split(col("value"), ",").getItem(4).as("product_name"),
          split(col("value"), ",").getItem(5).as("product_category"),
          split(col("value"), ",").getItem(8).as("price"),
          split(col("value"), ",").getItem(7).as("qty"),
          split(col("value"), ",").getItem(6).as("payment_type"),
          split(col("value"), ",").getItem(9).as("datetime"),
          split(col("value"), ",").getItem(10).as("country"),
          split(col("value"), ",").getItem(11).as("city"),
          split(col("value"), ",").getItem(12).as("ecommerce_website_name"),
          split(col("value"), ",").getItem(13).as("payment_txn_id"),
          split(col("value"), ",").getItem(14).as("payment_txn_success"),
          split(col("value"), ",").getItem(15).as("failure_reason")
        ).drop("value")
        df2.show(Int.MaxValue,false)
        if(count == 0){
          df2.write.mode("overwrite").option("header","true").csv("src/main/scala/Clover/data/files/ConsumedData/theirs")
        }
        else{
          df2.write.mode("append").option("header","true").csv("src/main/scala/Clover/data/files/ConsumedData/theirs")
        }
        count += buffer.length
      }
    }catch{
      case e: Exception => e.printStackTrace()
    }
  }

  def Batch():Unit= {
    val topicName = "streaming"
    val prop = new Properties()
    prop.setProperty(BOOTSTRAP_SERVERS_CONFIG, "172.26.93.148:9092")
    prop.setProperty(GROUP_ID_CONFIG, "group-id-1")
    prop.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, classOf[IntegerDeserializer].getName)
    prop.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    prop.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
    prop.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "true")
    prop.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000")

    val consumer = new KafkaConsumer[Int, String](prop)
    val test =consumer.partitionsFor(topicName)
    val loop=test.iterator()
    while(loop.hasNext){
      println(loop.next().toString)
    }
    consumer.subscribe(List(topicName).asJava)
    val polledRecords: ConsumerRecords[Int, String] = consumer.poll(Duration.ofSeconds(30))

    val ri = polledRecords.iterator()
    while (ri.hasNext) {
      val record = ri.next()
      val key = record.key()
      val value = record.value()
      println(value)
    }
  }

}
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


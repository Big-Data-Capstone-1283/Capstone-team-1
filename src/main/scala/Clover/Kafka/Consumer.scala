package Clover.Kafka
import java.time.Duration
import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.common.serialization.{IntegerDeserializer, IntegerSerializer, StringDeserializer, StringSerializer}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.streaming._
import Clover.data.Row
import org.apache.kafka.clients.consumer.ConsumerConfig.{BOOTSTRAP_SERVERS_CONFIG, GROUP_ID_CONFIG, KEY_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS_CONFIG}
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import java.sql.Timestamp
import java.util.Properties

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
  def Batch():Unit= {
    val topicName = "streaming"
    val prop = new Properties()
    prop.setProperty(BOOTSTRAP_SERVERS_CONFIG, "172.26.93.148:9092")
    prop.setProperty(GROUP_ID_CONFIG, "group-id-1")
    prop.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, classOf[IntegerDeserializer].getName)
    prop.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)

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


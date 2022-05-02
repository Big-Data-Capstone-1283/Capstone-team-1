package Clover.Kafka
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{IntegerSerializer, StringSerializer}
import org.apache.spark.sql.DataFrame
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.spark.sql.SparkSession
import java.util.Properties
class Producer(spark:SparkSession){

  /**Produces and sends records from the generated data
   *@param topicName The topic to send records to
   */
  def Produce(topicName:String = "team1"): Unit ={

    val transactions: DataFrame = spark.read.format("csv")
      .option("delimiter", ",")
      .option("header", "true")
      .option("infer.schema","true")
      .load("src/main/scala/Clover/data/files/BaseData/transactions.csv")

    val prop = new Properties()
    prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"ec2-3-93-174-172.compute-1.amazonaws.com:9092")
    prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[IntegerSerializer].getName)
    prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

    val prod = new KafkaProducer[Int,String](prop)
    var count = 1
    transactions.collect().foreach(row=>{
      prod.send(new ProducerRecord[Int,String](topicName,count,row.toString.replaceAll("Row(|)","")))
      println(count)
      count +=1
    })
    prod.flush()
  }
}

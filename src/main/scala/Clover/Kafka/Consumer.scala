package Clover.Kafka
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions._
import java.time.Duration
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.kafka.clients.consumer.ConsumerConfig.{BOOTSTRAP_SERVERS_CONFIG, GROUP_ID_CONFIG, KEY_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS_CONFIG}
import java.util.Properties
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

class Consumer(spark:SparkSession){
  /**Consumes records from supplied topic and stores them to a file
   *@param topicName The topic to consume from
   */
  def Consume(topicName:String = "team2"):Unit={
    import spark.implicits._
    val prop = new Properties()
    prop.setProperty(BOOTSTRAP_SERVERS_CONFIG, "ec2-3-93-174-172.compute-1.amazonaws.com:9092")
    prop.setProperty(GROUP_ID_CONFIG, "group-id-1")
    prop.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    prop.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    prop.setProperty(AUTO_OFFSET_RESET_CONFIG, "earliest")
    //prop.setProperty(ENABLE_AUTO_COMMIT_CONFIG, "true")
    //prop.setProperty(AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000")
    val consumer = new KafkaConsumer[String, String](prop)
    var firstTime = true
    try {
      consumer.subscribe(List(topicName).asJava)
      while(true) {
        val buffer: ArrayBuffer[String] = ArrayBuffer()
        val records: ConsumerRecords[String, String] = consumer.poll(Duration.ofMinutes(1L))
        records.records(topicName).forEach(x => {
          if (x.value().split(",").length == 14)
            buffer.append(x.value())
        })
        val sp = (x:String,index:Int) => split(col("value"),",").getItem(index).as(x)
        val df: DataFrame = spark.sparkContext.parallelize(buffer).toDF().select(
          sp("order_id",0),
          sp("customer_id",1),
          sp("customer_name",2),
          sp("product_id",3),
          sp("product_name",4),
          sp("product_category",5),
          sp("qty",6),
          sp("price",7),
          sp("datetime",8),
          sp("country",9),
          sp("city",10),
          sp("ecommerce_website_name",11),
          sp("payment_txn_id",12),
          sp("payment_txn_success",13)
        )
        df.show()
        if(firstTime){
          df.write.mode("overwrite").option("header","true").parquet("src/main/scala/Clover/data/files/ConsumedData")
          firstTime = false
        }
        else{
          df.write.mode("append").option("header","true").parquet("src/main/scala/Clover/data/files/ConsumedData")
        }
        //val data: DataFrame = ar2.toDF().select("*")
        //data.show(false)
      }
    }catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }

}
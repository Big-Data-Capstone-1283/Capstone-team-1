package Clover
import Clover.data._
import org.apache.log4j.{Level, Logger}
import org.apache.spark._
import org.apache.spark.sql.SparkSession
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
    println("created spark session")
    import spark.implicits._
    val sc = spark.sparkContext
    //val dg = new DataGenerator(spark)
    //dg.GenerateTimed(dg.Generate)
    val prod = new Clover.Kafka.Producer(spark)
    prod.Batch()
    //val cons = new Clover.Kafka.Consumer(spark)
    //cons.TestBatch()

    //val form = new Formulas(spark)
    //val schema = Encoders.product[Row].schema
    //val transactions: Dataset[Row] = spark.read.format("csv")
    //  .option("delimiter",",")
    //  .option("header","true")
    //  .schema(schema)
    //  .load("src/main/scala/Clover/data/files/ConsumedData/transactions.csv")
    //  .as[Row]
    //val printer = new PrintWriter("src/main/scala/Clover/data/files/ConsumedData/t2.csv")
    //printer.println("order_id,customer_id,customer_name,product_id,product_name,product_category,transaction_payment_type" +
    //  ",qty,price,datetime,country,city,ecommerce_website_name,payment_txn_id,payment_txn_success,failure_reason")
    //transactions.collect.foreach(row=>{
    //  val newPrice = form.Convert(row.country,row.price,false)
    //  printer.println(row.toString.replaceAll("Row\\(|\\)","").replaceAll(row.price.toString,newPrice.toString))
    //})
    //printer.close()


    //val cons = new Consumer(spark)
    //cons.TestBatch()

    /*val desiredTime = "1/1/2002 00:00:01"

    val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
    val format2 = new SimpleDateFormat("M/dd/yyy")
    //val time = format.parse(desiredTime).getTime
    //print(time)
    val desiredStartTime = "1/1/2000 00:00:01"
    val desiredEndTime = "1/1/2003 00:00:01"
    val time1 = format.parse(desiredStartTime).getTime
    val time2 = format.parse(desiredEndTime).getTime
    val companyTaperPoints:Array[Long] = new Array(2)
    companyTaperPoints(0) = time1
    companyTaperPoints(1) = time2
    val companySalesRates:Array[Double] = new Array(2)
    companySalesRates(0) = 10
    companySalesRates(1) = 20
    val companySalesVariation:Array[Double] = new Array(2)
    companySalesVariation(0) = 5
    companySalesVariation(1) = 10*/

    /*val dateProcessor = new DateTime()
   //val returnedArrayTimes = dateProcessor.dateTimeGenerationTaperPoints("Blockbuster", time1, time2, companyTaperPoints, companySalesRates, companySalesVariation)
    //println(returnedArrayTimes.toList)
    //val returnedList = returnedArrayTimes.toList
    //returnedList.foreach(println)
    //dateProcessor.LogisticGrowth(.05,.01,.001,100,format2.parse("1/1/2001").getTime,format2.parse("1/1/2010").getTime)
    val maps =dateProcessor.LogisticGrowth(.01,.025,.001,10000,format2.parse("1/1/2001").getTime,format2.parse("1/1/2010").getTime)
    val maps2 =dateProcessor.LogisticGrowth(.01,.025,.001,10000,format2.parse("1/1/2010").getTime,format2.parse("1/1/2020").getTime)
    val both = Map("compa"->maps,"compb"->maps2)
    for(date<-format2.parse("1/1/2001").getTime until format2.parse("1/1/2015").getTime by 86400000L) {
      val d = new Date(date)
      both.foreach(x=>try{println(x._1+": "+x._2(d))}catch{case _: Any =>})
      //val map = maps(d)
      //println(d + ": " + map)
    }
    */


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

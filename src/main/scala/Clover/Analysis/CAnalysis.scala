package Clover.Analysis

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

import java.sql.Timestamp
class CAnalysis(spark:SparkSession) {
  import spark.implicits._
  val tempdf: DataFrame = spark.read.format("parquet")
    .option("delimiter", ",")
    .option("header", "true")
    .option("infer.schema","true")
    .load("src/main/scala/Clover/data/files/ConsumedData").toDF("order_id","customer_id","customer_name"
    ,"product_id","product_name","product_category","datetime","price","qty","country","city","ecommerce_website_name"
    ,"payment_txn_id","payment_txn_success","nothing","nothing2")

  /*val df = tempdf.select("order_id","customer_id","customer_name","product_id","product_name"
    ,"product_category","qty","price","datetime","country","city","ecommerce_website_name","payment_txn_id","payment_txn_success")
  df.createOrReplaceTempView("temp")
 // df.show() */

  val adf = tempdf.filter(tempdf("datetime")==="2022-03-03 12:47:25")

  val tdf = adf.select(adf("order_id").cast("int"),adf("customer_id"),adf("customer_name"),adf("product_id").cast("int"),
    adf("product_name"),adf("product_category"),adf("qty").cast("int"),adf("price"),adf("datetime"),adf("country"),
    adf("city"),adf("ecommerce_website_name"),adf("payment_txn_id"),adf("payment_txn_success")).as[team2Row]

  //val df2 = df.filter(df("datetime")==="2022-03-03 12:47:25").as[team2Row]

  //tdf.show(Int.MaxValue)
  tdf.createOrReplaceTempView("temp2")

  spark.sql("SELECT DISTINCT * FROM temp2 WHERE datetime = '2022-03-03 12:47:25' ORDER BY order_id ASC").show(Int.MaxValue)
  //spark.sql("SELECT DISTINCT city FROM temp WHERE datetime = '2022-03-03 12:47:25'").show(Int.MaxValue)
  //spark.sql("SELECT DISTINCT ecommerce_website_name FROM temp WHERE datetime = '2022-03-03 12:47:25'").show(Int.MaxValue)
}
case class team2Row(order_id: Option[Int],customer_id: Option[String],customer_name: Option[String],product_id: Option[Int]
                   ,product_name: Option[String], product_category: Option[String], qty: Option[Int], price: Option[String]
                   ,datetime: Option[String],country: Option[String],city: Option[String],ecommerce_website_name: Option[String]
                   ,payment_txn_id: Option[String],payment_txn_success: Option[String])

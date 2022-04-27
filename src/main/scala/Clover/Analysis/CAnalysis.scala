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

  val df: Dataset[team2Row] = tempdf.select("order_id","customer_id","customer_name","product_id","product_name"
    ,"product_category","qty","price","datetime","country","city","ecommerce_website_name","payment_txn_id","payment_txn_success")
    .as[team2Row]
  df.createOrReplaceTempView("temp")
  df.show()
  //spark.sql("SELECT DISTINCT country,city FROM temp ORDER BY country ASC").show(Int.MaxValue)
}
case class team2Row(order_id: Option[Int],customer_id: Option[String],customer_name: Option[String],product_id: Option[Int]
                   ,product_name: Option[String], product_category: Option[String], qty: Option[Int], price: Option[String]
                   ,datetime: Option[Timestamp],country: Option[String],city: Option[String],ecommerce_website_name: Option[String]
                   ,payment_txn_id: Option[String],payment_txn_success: Option[String])

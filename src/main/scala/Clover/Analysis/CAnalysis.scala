package Clover.Analysis

import org.apache.spark.sql.functions.{col, regexp_replace}
import org.apache.spark.sql.{DataFrame, SparkSession}
class CAnalysis(spark:SparkSession) {
  import spark.implicits._
  val tempdf: DataFrame = spark.read.format("parquet")
    .option("delimiter", ",")
    .option("header", "true")
    .option("infer.schema","true")
    .load("src/main/scala/Clover/data/files/ConsumedData").toDF("order_id","customer_id","customer_name"
    ,"product_id","product_name","product_category","datetime","price","qty","country","city","ecommerce_website_name"
    ,"payment_txn_id","payment_txn_success","nothing","nothing2")

  val df = tempdf.withColumn("newValue",regexp_replace(col("price"),"\\$",""))
    .select(
      col("order_id").cast("int"),
      col("customer_id"),
      col("customer_name"),
      col("product_id").cast("int"),
      col("product_name"),
      col("product_category"),
      col("qty").cast("int"),
      col("newValue").cast("double"),
      col("datetime"),col("country"),
      col("city"),
      col("ecommerce_website_name"),
      col("payment_txn_id"),
      col("payment_txn_success"))
    .withColumnRenamed("newValue","price")
    .filter(col("datetime")==="2022-03-03 12:47:25")
    .distinct()
  df.createOrReplaceTempView("temp")
  spark.sql("SELECT product_category,SUM(price) sum FROM temp GROUP BY product_category ORDER BY sum").show()

  //val df2 = df.withColumn("new_value",col(df("price")).toString.substring(0,2)))


  //spark.sql("SELECT * FROM temp").show(Int.MaxValue)

  //spark.sql("SELECT customer_name, COUNT(customer_name) cnt FROM temp GROUP BY customer_name ORDER BY cnt").show()
  //spark.sql("SELECT DISTINCT country,city FROM temp ORDER BY country ASC").show(Int.MaxValue)
}
case class team2Row(order_id: Int,customer_id: Option[String],customer_name: Option[String],product_id: Option[Int]
                   ,product_name: Option[String], product_category: Option[String], qty: Option[Int], price: Option[Double]
                   ,datetime: Option[String],country: Option[String],city: Option[String],ecommerce_website_name: Option[String]
                   ,payment_txn_id: Option[String],payment_txn_success: Option[String])

package Clover.Analysis

import org.apache.spark.sql.functions.{col, regexp_replace}
import org.apache.spark.sql.{DataFrame, SparkSession}

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

  val df = tempdf.withColumn("newValue",regexp_replace(col("price"),"\\$",""))..select(adf("order_id").cast("int"),adf("customer_id"),adf("customer_name"),adf("product_id").cast("int"),
    adf("product_name"),adf("product_category"),adf("qty").cast("int"),adf("newValue").cast("double"),adf("datetime"),adf("country"),
    adf("city"),adf("ecommerce_website_name"),adf("payment_txn_id"),adf("payment_txn_success"))
    .withColumnRenamed("newValue","price")
    .filter(tempdf("datetime")==="2022-03-03 12:47:25")
    .distinct()

  //val adf = tempdf.filter(tempdf("datetime")==="2022-03-03 12:47:25")

  //val tdf = adf.as[team2Row]

  //val df2 = df.withColumn("new_value",col(df("price")).toString.substring(0,2)))


  //spark.sql("SELECT * FROM temp").show(Int.MaxValue)

  //spark.sql("SELECT customer_name, COUNT(customer_name) cnt FROM temp GROUP BY customer_name ORDER BY cnt").show()
  //spark.sql("SELECT DISTINCT country,city FROM temp ORDER BY country ASC").show(Int.MaxValue)
}
case class team2Row(order_id: Int,customer_id: Option[String],customer_name: Option[String],product_id: Option[Int]
                   ,product_name: Option[String], product_category: Option[String], qty: Option[Int], price: Option[Double]
                   ,datetime: Option[String],country: Option[String],city: Option[String],ecommerce_website_name: Option[String]
                   ,payment_txn_id: Option[String],payment_txn_success: Option[String])

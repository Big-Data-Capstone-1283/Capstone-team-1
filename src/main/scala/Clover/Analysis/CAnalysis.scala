package Clover.Analysis
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.Row
import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel}
class CAnalysis(spark:SparkSession) {
  import spark.implicits._
  val df = spark.read.format("parquet")
    .option("delimiter", ",")
    .option("header", "true")
    .option("infer.schema","true")
    .load("src/main/scala/Clover/data/files/ConsumedData")
    .toDF("order_id","customer_id","customer_name","product_id","product_name","product_category"
    ,"qty","price","datetime","country","city","ecommerce_website_name","payment_txn_id","payment_txn_success")
    .withColumn("newValue",regexp_replace(col("price"),"\\$",""))
    .select(
      col("order_id").cast("int"),
      col("customer_id"),
      col("customer_name"),
      col("product_id").cast("int"),
      col("product_name"),
      col("product_category"),
      col("qty").cast("int"),
      col("newValue").cast("double"),
      col("datetime"),
      col("country"),
      col("city"),
      col("ecommerce_website_name"),
      col("payment_txn_id"),
      col("payment_txn_success"))
    .withColumnRenamed("newValue","price")
    .filter(
      col("city") =!= " ")
    .distinct().as[team2Row]
  df.write
    .mode("overwrite")
    .option("header","true")
    .option("delimiter",",")
    .parquet("src/main/scala/Clover/data/files/CleanedData/Rows")
  df.show()
  //spark.sql("SELECT product_category,SUM(price) sum FROM temp GROUP BY product_category ORDER BY sum").show()
  val customers: Dataset[team2Customer] = df.select(col("customer_id"),col("customer_name"),col("country"),col("city"))
    .distinct()
    .filter(x=>{x.getString(2) != "Mozambique" && x.getString(3) != " "})
    .as[team2Customer]
  /*customers.write
    .mode("overwrite")
    .option("header","true")
    .partitionBy("country")
    .parquet("src/main/scala/Clover/data/files/CleanedData/Customers")*/
  val products: Dataset[team2Product] = df.select(col("product_id"),col("product_category"),col("product_name"),col("price"))
    .distinct()
    .as[team2Product]
 /* products.write
    .mode("overwrite")
    .option("header","true")
    .partitionBy("product_category")
    .parquet("src/main/scala/Clover/data/files/CleanedData/Products")*/
  val transactions: Dataset[team2Transaction] = df.select(col("order_id"),col("payment_txn_id"),col("payment_txn_success"),col("datetime"))
    .distinct()
    .as[team2Transaction]
  //val idTest = customers.map(x=>{(x.customer_id.substring(0,2),x.customer_id.substring(3))})
  /*val idTest = customers.withColumn("initial",regexp_extract(col("customer_id"),"\\w\\w",0))
    .withColumn("id",regexp_extract(col("customer_id"),"[^\\D]\\w+",0).cast("bigint"))
    .as[idTestC]*/
  //idTest.orderBy(asc("id")).createOrReplaceTempView("idTest")
  /*val data = customers.select("customer_id","customer_name")
    .map(x=> (x.getString(0),x.getString(1)))
    .toDF("customer_id","customer_name")
  val cvModel: CountVectorizerModel = new CountVectorizer()
    .setInputCol("customer_name")
    .setOutputCol("features")
    .setVocabSize(33)
    .setMinDF(2)
    .fit(data)
  cvModel.transform(data).show(false)*/
  customers.createOrReplaceTempView("customers")
  products.createOrReplaceTempView("products")
  transactions.createOrReplaceTempView("transactions")
}
case class team2Row(order_id: Int,customer_id: Option[String],customer_name: Option[String],product_id: Option[Int]
                   ,product_name: Option[String], product_category: Option[String], qty: Option[Int], price: Option[Double]
                   ,datetime: Option[String],country: Option[String],city: Option[String],ecommerce_website_name: Option[String]
                   ,payment_txn_id: Option[String],payment_txn_success: Option[String])
case class team2Transaction(order_id:Int,payment_txn_id:String,payment_txn_success:String,datetime:String)
case class team2Customer(customer_id:String,customer_name:String,country:String,city:String)
case class team2Product(product_id:Int,product_category:String,product_name:String,price:Double)
case class idTestC(customer_id:String,customer_name:String,country:String,city:String,initial:String,id:Long)
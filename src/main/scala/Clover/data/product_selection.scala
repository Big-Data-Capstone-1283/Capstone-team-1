package Clover.data
import Clover.{Main, SweepstakesGen}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}
import scala.util.Random

object product_selection extends App {

    System.setProperty("hadoop.home.dir", "/usr/local/Cellar/hadoop/3.3.2/libexec")
    println("Creating Spark session....")
    Logger.getLogger("org").setLevel(Level.ERROR)//remove messages
    val spark = SparkSession
      .builder
      .appName("Capstone")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")//remove messages
    println("created spark session")


  //call sparksection from main
  //trelo 8
  //jaceguai 4/05/2022 4:01 Est

  val sc = spark.sparkContext
  import spark.implicits._

  val companies: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/sites.csv").toDF()
  val customers: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/people.csv").toDF()
  val products: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/products.csv").toDF()

  val productsRows = products.count().toInt - 1
  val a_products = products.collect()
  val sweepstakesGen = new SweepstakesGen()
  //companies.show()
  case class SelectedProduct(var product_category: String, var product_name: String, var product_value: Double)
  var selectedProduct = new SelectedProduct("","", 0)

  productSelection("car,App,Drug", "plants, groceries", 0.10)

  println(selectedProduct.product_category, selectedProduct.product_name, selectedProduct.product_value)

  def productSelection(notSell: String, prefCategory: String = "", oddPrefCategory: Double = 1): Unit={
    //returns product_id, product_category, product_name, product_price, product_value Double

    //var result = new Array[String](3)
    if(prefCategory.isEmpty){
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        selectedProduct.product_category = result(0).toString
        selectedProduct.product_name = result(1).toString
        selectedProduct.product_value = result(2).asInstanceOf[Double]
      }while(notSell.toLowerCase.contains(selectedProduct.product_category.toLowerCase))
    }else if(sweepstakesGen.shuffle(oddPrefCategory)){
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        selectedProduct.product_category = result(0).toString
        selectedProduct.product_name = result(1).toString
        selectedProduct.product_value = result(2).asInstanceOf[Double]
      }while(notSell.toLowerCase.contains(selectedProduct.product_category.toLowerCase) & selectedProduct != prefCategory)
    } else{
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        selectedProduct.product_category = result(0).toString
        selectedProduct.product_name = result(1).toString
        selectedProduct.product_value = result(2).asInstanceOf[Double]
      }while(notSell.toLowerCase.contains(selectedProduct.product_category.toLowerCase))
    }


  }


}

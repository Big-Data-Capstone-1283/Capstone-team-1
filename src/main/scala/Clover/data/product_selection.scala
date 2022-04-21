package Clover.data
import Clover.{Main, SweepstakesGen}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}
import scala.util.Random

//class product_selection(spark: SparkSession){
//Object to test method productSelection
//the method will go DataGenerator

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


  val sc = spark.sparkContext
  import spark.implicits._

  val products: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/products.csv").toDF()

  val productsRows = products.count().toInt - 1
  val a_products = products.collect()
  val sweepstakesGen = new SweepstakesGen()
  //companies.show()
  case class SelectedProduct(var product_id: String, var product_category: String, var product_name: String, var product_value: String)
  var selectedProduct = new SelectedProduct("","","", "")

  for(i<- 1 to 10) {
    productSelection("","", .50)
    println(selectedProduct.product_id, selectedProduct.product_category, selectedProduct.product_name, selectedProduct.product_value)

  }


  def productSelection(notSell: String, prefCategory: String = "", oddPrefCategory: Double = 1): Unit={
    //returns product_id, product_category, product_name, product_price, product_value Double

    if(prefCategory.isEmpty){
      do {
        val result = (a_products(Random.nextInt(productsRows)))
        selectedProduct.product_id = result(0).toString
        selectedProduct.product_category = result(1).toString
        selectedProduct.product_name = result(2).toString
        selectedProduct.product_value = result(3).toString
        //println("1 = " + selectedProduct.product_category)
      }while(notSell.toLowerCase.contains(selectedProduct.product_category.toLowerCase))
    }else if(sweepstakesGen.shuffle(oddPrefCategory)){
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        selectedProduct.product_id = result(0).toString
        selectedProduct.product_category = result(1).toString
        selectedProduct.product_name = result(2).toString
        selectedProduct.product_value = result(3).toString
        //println("2 = " + selectedProduct.product_category)
      }while(notSell.toLowerCase.contains(selectedProduct.product_category.toLowerCase) | !prefCategory.toLowerCase.contains(selectedProduct.product_category.toLowerCase))
    } else{
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        selectedProduct.product_id = result(0).toString
        selectedProduct.product_category = result(1).toString
        selectedProduct.product_name = result(2).toString
        selectedProduct.product_value = result(3).toString
        //println("3 = " + selectedProduct.product_category)
      }while(notSell.toLowerCase.contains(selectedProduct.product_category.toLowerCase) | prefCategory.toLowerCase.contains(selectedProduct.product_category.toLowerCase) )
    }


  }


}

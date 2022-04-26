package Clover.data
import Clover.Main
import Clover.Tools.SweepstakesGen
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Random

//class product_selection(spark: SparkSession){
//Object to test method productSelection and productRules
//the methods will go DataGenerator

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

    .load("src/main/scala/Clover/data/files/BaseData/products.csv").toDF()

  val productsRows = products.count().toInt - 1
  val a_products = products.collect()
  val sweepstakesGen = new SweepstakesGen()
  //companies.show()
  case class Product(var id: Int, var category: String, var name: String, var value: Double)
  var product = new Product(0,"","", 0D)

 case class ProductParameters(var notSell: String, var prefCategory: String, var oddPrefCategory: Double)
 var productParameters = new ProductParameters("","", 1D)

  for(i<- 1 to 10) {
    productRules()
    productSelection(productParameters.notSell, productParameters.prefCategory, productParameters.oddPrefCategory)
    println(product.id, product.category, product.name, product.value)

  }


  def productSelection(notSell: String = "", prefCategory: String = "", oddPrefCategory: Double = 1): Unit= {
    //returns product_id, product_category, product_name, product_price, product_value Double

    if (prefCategory.isEmpty) {
      do {
        val result = (a_products(Random.nextInt(productsRows)))
        product.id = result(0).toString.toInt
        product.category = result(1).toString
        product.name = result(2).toString
        product.value = result(3).toString.toDouble
        //println("1 = " + selectedProduct.product_category)
      } while (notSell.toLowerCase.contains(product.category.toLowerCase))
    } else if (sweepstakesGen.shuffle(oddPrefCategory)) {
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        product.id = result(0).toString.toInt
        product.category = result(1).toString
        product.name = result(2).toString
        product.value = result(3).toString.toDouble
        //println("2 = " + selectedProduct.product_category)
      } while (notSell.toLowerCase.contains(product.category.toLowerCase) | !prefCategory.toLowerCase.contains(product.category.toLowerCase))
    } else {
      do {
        //val row = Random.nextInt(productsRows)
        val result = (a_products(Random.nextInt(productsRows)))
        product.id = result(0).toString.toInt
        product.category = result(1).toString
        product.name = result(2).toString
        product.value = result(3).toString.toDouble
        //println("3 = " + selectedProduct.product_category)
      } while (notSell.toLowerCase.contains(product.category.toLowerCase) | prefCategory.toLowerCase.contains(product.category.toLowerCase))
    }
  }

    def productRules(date: Int = 1, company: String = ""): Unit = {
      //according to the date
      var date = 1 //for testing purposes
      if (date > 0 & date < 2) {
        //if(company)
        {
          productParameters.notSell = "Car"
          productParameters.prefCategory = "Drug"
          productParameters.oddPrefCategory = 1D

        }
      }
    }

//end of class
}

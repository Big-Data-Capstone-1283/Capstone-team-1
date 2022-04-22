package Clover.data

import org.apache.spark.sql.{DataFrame, SparkSession}
import Clover.{Main, SweepstakesGen}
import scala.util.Random


class DataGenerator(spark:SparkSession){
  val companies: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/sites.csv").toDF()
  val customers: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/people.csv").toDF()
  val products: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/products.csv").toDF()
  case class Customer(id:Int,name:String,country:String,city:String)
  //case class to support product selection: Jaceguai 04/21/2022
  case class Product(var id: Int, var category: String, var name: String, var value: Double)
  var product = new Product(0,"","", 0D)

  //case clas for selection products rules
  case class ProductParameters(var notSell: String, var prefCategory: String, var oddPrefCategory: Double)
  var productParameters = new ProductParameters("","", 1D)

  //size of product table, if filtering the table the table here must be
  //the filtered table
  val productsRows = products.count().toInt - 1
  //array to work in the product selection, if filtering the table before
  //it must use that filtered table
  val a_products = products.collect()
  //class to manage the odds
  val sweepstakesGen = new SweepstakesGen()


  def Generate(): Unit= {
    /**
     * Essentially should work like this:
     * Loop for each company (website)
     * Loop for every day from January 1, 2000 to January 1, 2022
     * Loop the amount of times based on the companies salesRate and create a transaction for each one
     * Append that transaction to a dataframe for all of the transactions
     */
    companies.foreach(row => {
      var salesRate = row.getInt(3)
      for (date <- 946684800000L until 1640998800000L by 86400000L) {
        for (x <- 0 until salesRate) {
          /**
           *  val cust = random row from customer table that is within parameters
           *  val prod = random row from products table that is within parameters
           *  val customer = Customer(input data from cust)
           *  val product = Product(input data from prod)
           *  Randomly set transaction type
           *  Randomly decide if transaction was a success
           *  Give explanation if it wasn't a success
           *  val row = Row(input previous data)
           *  Append the row to dataset
           * */
          //call product selection
          //Parameters string of not selling categories, string of preferential categories, odds off preferential categories

          productRules()//call product rules with date and company and it will create productParameters ocording to the rule
          productSelection(productParameters.notSell, productParameters.prefCategory, productParameters.oddPrefCategory)
        }
      }
    })
  }

  def productSelection(notSell: String = "", prefCategory: String = "", oddPrefCategory: Double = 1): Unit= {
    //returns product_id, product_category, product_name, product_price, product_value Double
    //the user can assign categories not to return, preferential categories to choose with their odds
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
    //end of product selection
  }

  def productRules(date: Int = 1, company: String = ""): Unit = {
    //according to the date
    var date = 1 //for testing purposes
    if (date > 0 & date < 2) {
      //if(company)
      {
        productParameters.notSell = "Car"
        productParameters.prefCategory = "Movies"
        productParameters.oddPrefCategory = 1D

      }
    }
    //end of product rules
  }

}

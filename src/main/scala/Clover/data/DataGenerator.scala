package Clover.data

import org.apache.spark.sql.{DataFrame, SparkSession}
import Clover.{Main, SweepstakesGen}
import org.apache.log4j.{Level, Logger}

import java.io.{BufferedWriter, File, FileWriter}
import java.sql.Timestamp
import scala.util.Random


//class DataGenerator(spark:SparkSession){
object DataGenerator extends App{

  System.setProperty("hadoop.home.dir", "C:\\hadoop")
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
  val countries: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/countries.csv").toDF()

  case class Customer(var id:Int,var name:String,var country:String,var city:String)
  var person = Customer(0," "," ", " ")
  val customerRow = customers.count().toInt - 1
  val array_Customer = customers.collect()
  case class Company(name:String , notSellCat: String, notSellCountries: String, prefCountries: String,
                     prefCountriesPer: Double, priceOffset: Double, salesRate: Int)

  //case class to support product selection: Jaceguai 04/21/2022
  case class Product(var id: Int, var category: String, var name: String, var value: Double)
  var product = new Product(0,"","", 0D)

  //case clas for selection products rules
  case class ProductParameters(var notSell: String, var prefCategory: String, var oddPrefCategory: Double)
  var productParameters = new ProductParameters("","", 1D)

  case class Transactions(var payment_type: String, var payment_txn_id: String, var payment_txn_success: String, var failure_reason: String)
  var txn_final = new Transactions("", "", "", "")

  case class Order(order_id:String, customer_name:String, product_id: Int, product_name: String,
                   product_category: String, payment_type: String, qty: Int, price: Double,
                   datetime: Timestamp, country: String, city: String, ecommerce_website_name: String,
                   payment_txn_id: String, failure_reason: String)

  //size of product table, if filtering the table the table here must be
  //the filtered table
  val productsRows = products.count().toInt - 1
  //array to work in the product selection, if filtering the table before
  //it must use that filtered table
  val a_products = products.collect()
  //class to manage the odds
  val sweepstakesGen = new SweepstakesGen()

// companies.foreach(row => {
//   println(row.getString(0))
// })

  Generate()

  def Generate(): Unit= {
    /**
     * Essentially should work like this:
     * Loop for each company (website)
     * Loop for every day from January 1, 2000 to January 1, 2022
     * Loop the amount of times based on the companies salesRate and create a transaction for each one
     * Append that transaction to a dataframe for all of the transactions
     */
    companies.rdd.collect.foreach(row => {
      val company = new Company(row.getString(0), row.getString(1), row.getString(2), row.getString(3),
                         row.getDouble(4), row.getDouble(5), row.getInt(6))
      //var salesRate = row.getInt(3)
      //println(row)
      var order = ""
      for (date <- 946684800000L until 1640998800000L by 86400000L) {
        for (x <- 0 until company.salesRate) {
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
          //Parameters string of not selling categories, string of preferential categories, odds off preferential categorie

//          println(company.notSellCountries)
//          println(company.prefCountries)
//          println(company.prefCountriesPer)
          shufflePeople(company.notSellCountries, company.prefCountries, company.prefCountriesPer)
         // println(person)
          productRules()//call product rules with date and company and it will create productParameters according to the rule
          productSelection(productParameters.notSell, productParameters.prefCategory, productParameters.oddPrefCategory)
         // println(product)
          val txn_id = Random.alphanumeric.take(15).mkString
          CreateTransaction(txn_id)
          val order_id = Random.alphanumeric.take(15).mkString
          val country = countries.filter(countries("Country") === person.country)
          val rate = country.select("Exchange_Rate").as[Double].collect()
          val prod_qty = qty(product.category).toString
          val prod_price = (product.value * rate(0))
          val final_date = date.toString
          val customer_id = person.id.toString
          val sproduct_id = product.id.toString
           if (order =="") {
             order += f"$order_id,$customer_id,${person.name},$sproduct_id,${product.name},${product.category},${txn_final.payment_type}," +
               f"$prod_qty,$prod_price%.2f,$final_date,${person.country},${person.city},${company.name},${txn_final.payment_txn_id}" +
               f",${txn_final.payment_txn_success},${txn_final.failure_reason}"
           }else{
             order += f"\n$order_id,$customer_id,${person.name},$sproduct_id,${product.name},${product.category},${txn_final.payment_type}," +
               f"$prod_qty,$prod_price%.2f,$final_date,${person.country},${person.city},${company.name},${txn_final.payment_txn_id}" +
               f",${txn_final.payment_txn_success},${txn_final.failure_reason}"
           }

          writeToCsv(order)

        }

      }

    })
  }

  def writeToCsv(string:String): Unit ={
    val file = new File("src\\main\\scala\\Clover\\data\\BigData.csv")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(string)
    bw.close()
  }
//  var person = Customer(0," "," ", " ")
//  val customerRow = customers.count().toInt - 1
//  val array_Customer = customers.collect()

  def shufflePeople(notInCountry:String = "", prefCountry: String = "", probabilityPrefCountry: Double = 1D): Unit = {
    //println(1)
    if(prefCountry.isEmpty) {
      //println(2)
      do {
        val random_customer = array_Customer(Random.nextInt(customerRow)).toString()
        val replace = random_customer.replace("[", "").replace("]", "")
        val split = replace.split(",")
        person.id = split(0).toInt
        person.name = split(1)
        person.country = split(2)
        person.city = split(3)
      } while (notInCountry.toLowerCase.contains(person.country.toLowerCase))
    }else if (sweepstakesGen.shuffle(probabilityPrefCountry)) {
      //println(3)
      do {
        val random_customer = array_Customer(Random.nextInt(customerRow)).toString()
        val replace = random_customer.replace("[", "").replace("]", "")
        val split = replace.split(",")
        person.id = split(0).toInt
        person.name = split(1)
        person.city = split(2)
        person.country = split(3)
        //println(person)
      }while(notInCountry.toLowerCase.contains(person.country.toLowerCase) | !prefCountry.toLowerCase.contains(person.country.toLowerCase))
    } else {
      //println(4)
      do {
        val random_customer = array_Customer(Random.nextInt(customerRow)).toString()
        val replace = random_customer.replace("[", "").replace("]", "")
        val split = replace.split(",")
        person.id = split(0).toInt
        person.name = split(1)
        person.country = split(3)
        person.city = split(2)
      } while (notInCountry.toLowerCase.contains(person.country.toLowerCase) | !prefCountry.toLowerCase.contains(person.country.toLowerCase))
    }
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
//    if (date > 0 & date < 2) {
//      //if(company)
//      {
//        productParameters.notSell = "Car"
//        productParameters.prefCategory = "Movies"
//        productParameters.oddPrefCategory = 1D
//
//      }
//    }
    //end of product rules
  }

  def CreateTransaction(txn_id: String) : Unit = {


    //<editor-fold desc="Fail Reasons">

    val CreditCardFailReasons = Array("Card Information Incorrect", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Card Expired", "Credit Line Limit Reached")
    val DebitCardFailReasons = Array("Card Information Incorrect", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Card Expired", "Insufficient Funds")
    val CheckFailReasons = Array("Fraud", "Connection Interrupted",
      "Server Maintenance", "Invalid Check Number", "Bank Account Suspended")
    val PayPalFailReasons = Array("PayPal Service Down", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Incorrect Credentials", "Out of Funds")

    //</editor-fold>

    val paymentTypes = Array("Credit Card", "Debit Card", "Check", "Paypal")

    val sweepstakesGen = new SweepstakesGen()

    val payType = paymentTypes(Random.nextInt(paymentTypes.length))
    val isTxnSuccess = sweepstakesGen.shuffle(.95)
    var TxnSuccess = ""
    var TxnFailedReason = ""

    isTxnSuccess match {
      case true => TxnSuccess = "Y"; TxnFailedReason = "None"
      case false =>
        TxnSuccess = "N"
        payType match {
          case "Credit Card" => TxnFailedReason = CreditCardFailReasons(Random.nextInt(CreditCardFailReasons.length))
          case "Debit Card" => TxnFailedReason = DebitCardFailReasons(Random.nextInt(DebitCardFailReasons.length))
          case "Check" => TxnFailedReason = CheckFailReasons(Random.nextInt(CheckFailReasons.length))
          case "Paypal" => TxnFailedReason = PayPalFailReasons(Random.nextInt(PayPalFailReasons.length))
        }
    }

    txn_final.payment_type = payType
    txn_final.payment_txn_id = txn_id
    txn_final.payment_txn_success = TxnSuccess
    txn_final.failure_reason = TxnFailedReason
  }

  def qty(category:String): Int={
    category.toLowerCase match {
      case "car" => {if(sweepstakesGen.shuffle(0.98)) return 1 else return Random.nextInt(9) }
      case "plants" => {if(sweepstakesGen.shuffle(0.85)) return 1 else return Random.nextInt(10)}
      case "groceries" => {if(sweepstakesGen.shuffle(0.75)) return 1 else return Random.nextInt(20)}
      case "movie" => {if(sweepstakesGen.shuffle(0.99)) return 1 else return 2}
      case "drug" => {if(sweepstakesGen.shuffle(0.94)) return 1 else return Random.nextInt(5)}
      case "app" => return 1
      case _ => return 1
    }
  }

}

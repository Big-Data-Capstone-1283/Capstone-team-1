package Clover.data

import org.apache.spark.sql.{DataFrame, SparkSession}
import Clover.{Main, SweepstakesGen}
import org.apache.log4j.{Level, Logger}

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
    .load("src/main/scala/Clover/data/files/BaseData/people.csv").toDF("customer_id","customer_name","city","country")
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

  case class Customer(var id:Int,var name:String,var country:String,var city:String){
    def this(){
      this(0," "," "," ")
    }
    def this(split:Array[String]){
      //var random_customer = ""
      this(split(0).toInt,split(1),split(2),split(3))
    }
  }
  var person = Customer(0," "," ", " ")

  case class Company(name:String , notSellCat: String, notSellCountries: String, prefCountries: String,
                     prefCountriesPer: Double, priceOffset: Double, salesRate: Int)

  //case class to support product selection: Jaceguai 04/21/2022
  case class Product(var id: Int, var category: String, var name: String, var value: Double){
    def this(){
      this(0," "," ",0)
    }
  }
  var product = new Product(0,"","", 0D)

  //case clas for selection products rules
  case class ProductParameters(var notSell: String, var prefCategory: String, var oddPrefCategory: Double)
  var productParameters = new ProductParameters("","", 1D)

  case class Order(order_id:String, customer_name:String, product_id: Int, product_name: String,
                   product_category: String, payment_type: String, qty: Int, price: Double,
                   datetime: Timestamp, country: String, city: String, ecommerce_website_name: String,
                   payment_txn_id: String, failure_reason: String)
  case class Transactions(var payment_type: String, var payment_txn_id: String, var payment_txn_success: String, var failure_reason: String){
    def this(){
      this(" "," "," "," ")
    }
  }
  var txn_final = new Transactions("", "", "", "")

  case class Order2(var product:Product,var transaction:Transactions,var customer:Customer,var company:Company,var qty:Int){
    def this(){
      this(null,null,null,null,0)
    }
  }


  //size of product table, if filtering the table the table here must be
  //the filtered table

  //val customerRow = customers.count().toInt - 1
  //val array_Customer = customers.collect()
  val productsRows = products.count().toInt - 1
  //array to work in the product selection, if filtering the table before
  //it must use that filtered table

  //class to manage the odds


// companies.foreach(row => {
//   println(row.getString(0))
// })

  val a_products = products.collect()

  Generate()

  def Generate(): Unit= {
    /**
     * Essentially should work like this:
     * Loop for each company (website)
     * Loop for every day from January 1, 2000 to January 1, 2022
     * Loop the amount of times based on the companies salesRate and create a transaction for each one
     * Append that transaction to a dataframe for all of the transactions
     */

    val sweepstakesGen = new SweepstakesGen()
    val formulas = new Formulas(spark)
    companies.rdd.collect.foreach(row => {
      val company = new Company(row.getString(0), row.getString(1), row.getString(2), row.getString(3),
                         row.getDouble(4), row.getDouble(5), row.getInt(6))

      val selection: Map[String,Array[String]] = Map(
        "noSellCountries"->company.notSellCountries.split("-")
      ,"preferredSellCountries"->company.prefCountries.split("-")
      ,"noSellCategory"->company.notSellCat.split("-")
      )
      val array_Customers = customers
        .filter(x=> !selection("noSellCountries").contains(x.getString(3)))
        .collect()
        .map(row=>row.toString().replaceAll("\\[|\\]", ""))

      val preferred_Customers = customers
        .filter(x=> selection("preferredSellCountries").contains(x.getString(3)))
        .collect()
        .map(row=>row.toString().replaceAll("\\[|\\]", ""))

      val arrayProducts = products
        .filter(x=> !selection("noSellCategory").contains(x.getString(1)))
        .collect()
        .map(row=>row.toString().replaceAll("\\[|\\]", ""))

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
          val customer = shufflePeople(array_Customers,preferred_Customers,sweepstakesGen.shuffle(company.prefCountriesPer))
          //productRules()//call product rules with date and company and it will create productParameters according to the rule
          val product = productSelection(arrayProducts)
          val transaction = CreateTransaction(sweepstakesGen.shuffle(.95))
          val order_id = Random.alphanumeric.take(15).mkString
          val prod_qty = qty(product.category)
          val row = Row(order_id,customer.id,customer.name,product.id,product.name,product.category,transaction.payment_type
            ,prod_qty,formulas.Convert(customer.country,product.value)*prod_qty,new Timestamp(date),customer.country,customer.city
            ,company.name,transaction.payment_txn_id,transaction.payment_txn_success,transaction.failure_reason)
          /*val order = f"$order_id,${customer.id},${customer.name},${product.id},${product.name},${product.category},${txn_final.payment_type}," +
            f"$prod_qty,${formulas.Convert(customer.country,product.value)*prod_qty}%.2f,$final_date,${customer.country},${customer.city},${company.name},${txn_final.payment_txn_id}" +
            f",${txn_final.payment_txn_success},${txn_final.failure_reason}"*/
          println(row.toString)
        }
      }
    })
    def shufflePeople(array_Customers:Array[String], preferred_Customers:Array[String], yesno:Boolean): Customer = {
      /**
       * If yesno is True it will make a customer from the preferred table
       * If it's false it will make one from the generic table for the company
       */
      if(yesno)new Customer(preferred_Customers(Random.nextInt(preferred_Customers.length-1)).split(","))
      else new Customer(array_Customers(Random.nextInt(array_Customers.length-1)).split(","))
    }

    def productSelection(arrayProducts:Array[String]): Product= {
      //returns product_id, product_category, product_name, product_price, product_value Double
      //the user can assign categories not to return, preferential categories to choose with their odds
          //val row = Random.nextInt(productsRows)
      val product = new Product()
      val result = arrayProducts(Random.nextInt(arrayProducts.length)).split(",")
      product.id = result(0).toInt
      product.category = result(1)
      product.name = result(2)
      product.value = result(3).toDouble
      product
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

    def CreateTransaction(success:Boolean) : Transactions = {


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

      val transaction = new Transactions()
      val paymentTypes = Array("Credit Card", "Debit Card", "Check", "Paypal")
      transaction.payment_type = paymentTypes(Random.nextInt(paymentTypes.length))

      if (success) {
        transaction.payment_txn_success = "Y"
      } else {
        transaction.payment_txn_success = "N"
        transaction.payment_type match {
          case "Credit Card" => transaction.failure_reason = CreditCardFailReasons(Random.nextInt(CreditCardFailReasons.length))
          case "Debit Card" => transaction.failure_reason = DebitCardFailReasons(Random.nextInt(DebitCardFailReasons.length))
          case "Check" => transaction.failure_reason = CheckFailReasons(Random.nextInt(CheckFailReasons.length))
          case "Paypal" => transaction.failure_reason = PayPalFailReasons(Random.nextInt(PayPalFailReasons.length))
        }
      }
      transaction
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
//  var person = Customer(0," "," ", " ")
//  val customerRow = customers.count().toInt - 1
//  val array_Customer = customers.collect()



}

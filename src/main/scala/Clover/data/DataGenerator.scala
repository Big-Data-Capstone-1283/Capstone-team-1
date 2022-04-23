package Clover.data

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import Clover.SweepstakesGen
import java.io.PrintWriter
import java.sql.Timestamp
import scala.util.Random

class DataGenerator(spark:SparkSession){

  import spark.implicits._
  val companies: Dataset[Company] = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/sites.csv")
    .as[Company]
  val customers: Dataset[Customer] = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/people.csv")
    .as[Customer]
  val products: Dataset[Product] = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/products.csv")
    .as[Product]
  /*val countries: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/countries.csv").toDF()*/
  val sweepstakesGen = new SweepstakesGen()
  val formulas = new Formulas(spark)
  /** Generates Entire Dataset*/
  def GenerateLoop(loops: Int): Unit={
    val printer = new PrintWriter("src/main/scala/Clover/data/files/ConsumedData/time.csv")
    printer.println("algorithm,loop,time")
    for(x<- 1 to loops)
      {
        printer.println("mine,"+x+","+GenerateTimed(Generate))
        printer.println("old,"+x+","+GenerateTimed(Generate2))
      }
      printer.close()
  }
  def Generate(): Unit= {

    /*val row = Row(order_id, customer.id, customer.name, product.id, product.name, product.category, transaction.payment_type
    , prod_qty, formulas.Convert(customer.country, product.value), new Timestamp(date), customer.country, customer.city
    , company.name, transaction.payment_txn_id, transaction.payment_txn_success, transaction.failure_reason)*/
    val printer = new PrintWriter("src/main/scala/Clover/data/files/ConsumedData/transactions.csv")
    printer.println("order_id,customer_id,customer_name,product_id,product_name,product_category,transaction_payment_type" +
      ",qty,price,datetime,country,city,ecommerce_website_name,payment_txn_id,payment_txn_success,failure_reason")
    var transactionid = 0
    companies.rdd.collect.foreach(company => {
      var orderid= 0
      val selection: Map[String, Array[String]] = Map(
        "noSellCountries" -> company.notSellCountries.split("-")
        , "preferredSellCountries" -> company.prefCountries.split("-")
        , "noSellCategory" -> company.notSellCat.split("-")
      )
      val array_Customers = customers
        .filter(x => !selection("noSellCountries").contains(x.country))
        .collect()
      val preferred_Customers = customers
        .filter(x => selection("preferredSellCountries").contains(x.country))
        .collect()
      val arrayProducts = products
        .filter(x => !selection("noSellCategory").contains(x.category))
        .collect()

      for (date <- 946684800000L until 1640998800000L by 86400000L) {
        for (x <- 0 until company.salesRate) {
          val customer = {
            if(sweepstakesGen.shuffle(company.prefCountriesPer)&&preferred_Customers.length>0)preferred_Customers(Random.nextInt(preferred_Customers.length-1))
            else array_Customers(Random.nextInt(array_Customers.length-1))
          }
          val product = arrayProducts(Random.nextInt(arrayProducts.length))
          val transaction = CreateTransaction(orderid,transactionid,new Timestamp(date),qty(product.category),sweepstakesGen.shuffle(.95))
          val row = new Row(customer,company,product,transaction)
          orderid+=1
          transactionid+=1
          printer.println(row.toString.replaceAll("Row(|)",""))
        }
      }

    })
    println("\nTotal transactions: "+transactionid)
    printer.close()
  }
  def Generate2(): Unit= {



    var transactionid = 0
    companies.rdd.collect.foreach(company => {
      var orderid= 0
      val selection: Map[String, Array[String]] = Map(
        "noSellCountries" -> company.notSellCountries.split("-")
        , "preferredSellCountries" -> company.prefCountries.split("-")
        , "noSellCategory" -> company.notSellCat.split("-")
      )
      val array_Customers = customers
        .filter(x => !selection("noSellCountries").contains(x.country))
        .collect()

      val preferred_Customers = customers
        .filter(x => selection("preferredSellCountries").contains(x.country))
        .collect()

      val arrayProducts = products
        .filter(x => !selection("noSellCategory").contains(x.category))
        .collect()
      //.map(row => row.toString().replaceAll("[\\[\\]]", ""))

      for (date <- 946684800000L until 1640998800000L by 86400000L) {
        for (x <- 0 until company.salesRate) {

          //val customer = shufflePeople(array_Customers, preferred_Customers, sweepstakesGen.shuffle(company.prefCountriesPer))
          val customer = {
            if(sweepstakesGen.shuffle(company.prefCountriesPer)&&preferred_Customers.length>0)preferred_Customers(Random.nextInt(preferred_Customers.length-1))
            else array_Customers(Random.nextInt(array_Customers.length-1))
          }
          //val product = new Product(arrayProducts(Random.nextInt(arrayProducts.length)).split(","), company.priceOffset)
          val product = arrayProducts(Random.nextInt(arrayProducts.length))
          val transaction = CreateTransaction2(orderid,transactionid,new Timestamp(date),qty(product.category),sweepstakesGen.shuffle(.95))
          val row = new Row(customer,company,product,transaction)
          /*val row = Row(order_id, customer.id, customer.name, product.id, product.name, product.category, transaction.payment_type
            , prod_qty, formulas.Convert(customer.country, product.value), new Timestamp(date), customer.country, customer.city
            , company.name, transaction.payment_txn_id, transaction.payment_txn_success, transaction.failure_reason)*/
          /*val order = f"$order_id,${customer.id},${customer.name},${product.id},${product.name},${product.category},${txn_final.payment_type}," +
            f"$prod_qty,${formulas.Convert(customer.country,product.value)*prod_qty}%.2f,$final_date,${customer.country},${customer.city},${company.name},${txn_final.payment_txn_id}" +
            f",${txn_final.payment_txn_success},${txn_final.failure_reason}"*/
          //println(row.toString)
          orderid+=1
          transactionid+=1
        }
      }
    })
    //println("\nTotal transactions: "+transactionid)
  }
    /** Gets a random person from one of the customer tables
     *  @param array_Customers General Customer Array
     *  @param preferred_Customers Preferred Customer Array
     *  @param yesno Decides if General or Preferred is used
     */
    def shufflePeople(array_Customers:Array[String], preferred_Customers:Array[String], yesno:Boolean): Customer = {

      if(yesno&&preferred_Customers.length>0)new Customer(preferred_Customers(Random.nextInt(preferred_Customers.length-1)).split(","))
      else new Customer(array_Customers(Random.nextInt(array_Customers.length-1)).split(","))
    }

    /**Gets a random product from the product table
     *
     * @param arrayProducts Array of Products
     * @return
     */
    def productSelection(arrayProducts:Array[String],priceOffset:Double): Product= {
      new Product(arrayProducts(Random.nextInt(arrayProducts.length)).split(","),priceOffset)
    }

    def CreateTransaction(orderid:Int,transactionid:Int,date:Timestamp,qty:Int,success:Boolean) : Transaction = {

      val CardFailReasons = Array("Card Information Incorrect", "Fraud", "Connection Interrupted",
        "Server Maintenance", "Card Expired")
      val BankingFailReasons = Array("Fraud", "Connection Interrupted",
        "Server Maintenance", "Invalid Routing Number", "Bank Account Suspended")
      val PayPalFailReasons = Array("PayPal Service Down", "Fraud", "Connection Interrupted",
        "Server Maintenance", "Incorrect Credentials", "Out of Funds")

      val paymentTypes = Array("Card","Bank","UPI","Paypal")
      val rand = Random.nextInt(paymentTypes.length)
      if (success) {
        Transaction(paymentTypes(rand),transactionid,"Y","",qty,orderid,date)
      } else {
        rand match {
          case 0 => Transaction(paymentTypes(0),transactionid,"N",CardFailReasons(Random.nextInt(CardFailReasons.length))
          ,qty,orderid,date)
          case 1|2 => Transaction(paymentTypes(rand),transactionid,"N",BankingFailReasons(Random.nextInt(BankingFailReasons.length))
          ,qty,orderid,date)
          case 3 => Transaction(paymentTypes(3),transactionid,"N",PayPalFailReasons(Random.nextInt(PayPalFailReasons.length))
          ,qty,orderid,date)
        }
      }
    }
    def CreateTransaction2(orderid:Int,transactionid:Int,date:Timestamp,qty:Int,success:Boolean) : Transaction = {

    val CardFailReasons = Array("Card Information Incorrect", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Card Expired")
    val BankingFailReasons = Array("Fraud", "Connection Interrupted",
      "Server Maintenance", "Invalid Routing Number", "Bank Account Suspended")
    val PayPalFailReasons = Array("PayPal Service Down", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Incorrect Credentials", "Out of Funds")

    val paymentTypes = Array("Card","Bank","UPI","Paypal")
    val rand = Random.nextInt(paymentTypes.length)
    if (success) {
      Transaction(paymentTypes(rand),transactionid,"Y","",qty,orderid,date)
    } else {
      paymentTypes(rand) match {
        case "Card" => Transaction(paymentTypes(0),transactionid,"N",CardFailReasons(Random.nextInt(CardFailReasons.length))
          ,qty,orderid,date)
        case "Bank"|"UPI" => Transaction(paymentTypes(rand),transactionid,"N",BankingFailReasons(Random.nextInt(BankingFailReasons.length))
          ,qty,orderid,date)
        case "Paypal" => Transaction(paymentTypes(3),transactionid,"N",PayPalFailReasons(Random.nextInt(PayPalFailReasons.length))
          ,qty,orderid,date)
      }
    }
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


  /** Generates dntire dataset and prints how long it took*/
  def GenerateTimed(gen:()=>Unit): Unit={
    time{gen()}
  }
  def time[R](block: => R): Unit = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Completed in: "+((t1 - t0)/1e+9).toString+" seconds")
  }


  //case class to support product selection: Jaceguai 04/21/2022

  //case class ProductParameters(var notSell: String, var prefCategory: String, var oddPrefCategory: Double)

  /*case class Order(order_id:String, customer_name:String, product_id: Int, product_name: String,
                   product_category: String, payment_type: String, qty: Int, price: Double,
                   datetime: Timestamp, country: String, city: String, ecommerce_website_name: String,
                   payment_txn_id: String, failure_reason: String)*/

  /*case class Order2(var product:Product,var transaction:Transaction,var customer:Customer,var company:Company,var qty:Int){
    def this(){
      this(null,null,null,null,0)
    }
  }*/
}

package Clover.data

import Clover.Tools.SweepstakesGen
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

import java.io.PrintWriter
import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat
import scala.collection.mutable
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
  def Generate(): Unit= {

    /*val row = Row(order_id, customer.id, customer.name, product.id, product.name, product.category, transaction.payment_type
    , prod_qty, formulas.Convert(customer.country, product.value), new Timestamp(date), customer.country, customer.city
    , company.name, transaction.payment_txn_id, transaction.payment_txn_success, transaction.failure_reason)*/
    val printer = new PrintWriter("src/main/scala/Clover/data/files/ConsumedData/transactions.csv")
    printer.println("order_id,customer_id,customer_name,product_id,product_name,product_category,transaction_payment_type" +
      ",qty,price,datetime,country,city,ecommerce_website_name,payment_txn_id,payment_txn_success,failure_reason")
    var transactionid = 0
    val dateProcessor = new DateTime()
    val dateMap: Map[Company,mutable.ListMap[Date,Int]] ={
      //val maps =dateProcessor.LogisticGrowth(.01,.025,.001,10000,format2.parse("1/1/2001").getTime,format2.parse("1/1/2010").getTime)
      val f =(x: String)=>{val format = new SimpleDateFormat("M/dd/yyy");format.parse(x).getTime}
      val d = (comp:Company,R:Double,D:Double,C:Int,start:Long,end:Long) => Map(comp->dateProcessor.LogisticGrowthRand(R,D,C,comp.salesRate,start,end))
      val c = (str:String)=>companies.where(companies("name")===str).collect()(0)
      val amazonbr = d(c("www.amazon.com.br"),.03,.01,175,f("1/1/2000"),f("1/1/2010"))
      val amazon = d(c("www.amazon.com"),.04,.02,375,f("1/1/2000"),f("1/1/2010"))
      val etsy = d(c("www.etsy.com"),.04,.04,300,f("1/1/2000"),f("1/1/2010"))
      val ebay = d(c("www.ebay.com"),.04,.02,250,f("1/1/2000"),f("1/1/2010"))
      val alibaba = d(c("www.allibaba.com"),.03,.025,300,f("1/1/2000"),f("1/1/2010"))
      val amazonin = d(c("www.amazon.in"),.03,.01,175,f("1/1/2000"),f("1/1/2010"))
      val blockbuster = d(c("www.blockuster.com"),.01,.02,250,f("1/1/2000"),f("1/1/2010"))
      val netflix = d(c("www.netflix.com"),.03,.005,300,f("1/1/2000"),f("1/1/2010"))
      amazonbr++amazon++etsy++ebay++alibaba++amazonin++blockbuster++netflix
    }
    val rand = new Clover.Tools.Random()
    dateMap.foreach(list => {
      var orderid= 0
      val company = list._1
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

      list._2.foreach(compList=>{
        for (x <- 0 until compList._2) {
          val customer = {
            if(sweepstakesGen.shuffle(company.prefCountriesPer)&&preferred_Customers.length>0)preferred_Customers(Random.nextInt(preferred_Customers.length-1))
            else array_Customers(Random.nextInt(array_Customers.length-1))
          }
          val product = arrayProducts(Random.nextInt(arrayProducts.length))
          val transaction = CreateTransaction(orderid,transactionid,new Timestamp(compList._1.getTime+rand.nextLong(86300000L)),qty(product.category),sweepstakesGen.shuffle(.95))
          val row = new Row(customer,company,product,transaction,formulas)
          orderid+=1
          transactionid+=1
          printer.println(row.toString.replaceAll("Row\\(|\\)",""))
        }
      })

    })
    println("\nTotal transactions: "+transactionid)
    printer.close()
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

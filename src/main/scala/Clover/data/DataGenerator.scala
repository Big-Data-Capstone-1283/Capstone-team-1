package Clover.data
import org.apache.spark.sql.{DataFrame, SparkSession}
import java.sql.Timestamp
import java.io.PrintWriter
class DataGenerator(spark:SparkSession){
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
    .load("src/main/scala/Clover/data/products.csv")
  case class Customer(id:Int,name:String,country:String,city:String)
  case class Product(id:Int,category:String,name:String,value:Double)
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
        }
      }
    })
  }
  val desiredTime = "3/20/2017 16:5:45"

  val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
  val time = format.parse(desiredTime).getTime()
  print(time)


}

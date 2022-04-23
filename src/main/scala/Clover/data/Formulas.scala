package Clover.data
import scala.util.Random
import scala.math.BigDecimal
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.col
class Formulas(spark:SparkSession) {
  /**
   * This will probably be temporary, at least where it is
   * But this creates a dataframe from the countries csv to work with the convert function
   */
  val df: DataFrame = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/countries.csv").toDF("Country","Currency","Exchange_Rate")
  val exchangeRate: Map[String, Double] = df.collect().flatMap(row => {
    Map(row.toString().replaceAll("\\[|\\]", "").split(",")(0) -> row.toString().replaceAll("\\[|\\]", "").split(",")(2).toDouble)
  }).toMap

  private val inflationOccured: Array[Double] = new Array[Double](22)

  def SetInflation(): Unit={
    val rand = new Random()
    var low:Double = 0
    var high:Double = 2.5
    //Key     0       1       2       3       4
    //Values  1.00    1.02    1.05    1.09    1.16
    for(w<- inflationOccured.indices){
      if(w == 0){
        inflationOccured(w) = 1
      }
      else
      {
        if(rand.nextInt(3) >2){
          low = 2.6
          high = 5
        }
        else
        {
          low = 0
          high = 2.5
        }
        //inflationOccured(w) = inflationOccured(w - 1)*(1 + rand.between(low,high)/100)
      }
    }
    inflationOccured.foreach(println)
  }
  def ApplyInflation(price: Double,year: Int): Double={
    BigDecimal(price*inflationOccured(year-2000)).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble //Should be functional. SHOULD
  }

  /**
   *
   * @param country Country where sale took place
   * @param price price of product
   * @return exchanged price
   */
  def Convert(country:String,price:Double): Double={
    /**
     * Simply does a quick query of the dataframe and filters by country/currency code
     * Then it just grabs the value of the exchange rate and multiplies it by the price
     */
    price * exchangeRate(country)
  }

}

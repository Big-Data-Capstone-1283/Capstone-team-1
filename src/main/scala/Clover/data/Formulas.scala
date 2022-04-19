package Clover.data
import scala.util.Random
import scala.math.BigDecimal
object Formulas {

  //private val InflationRate: Array[Double] = new Array[Double](22)
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
        inflationOccured(w) = inflationOccured(w - 1)*(1 + rand.between(low,high)/100)
    //        InflationRate(x) = BigDecimal(InflationRate(x-1) + rand.between(low,high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }

    }
    /*for(x<- InflationRate.indices) {
      if(rand.nextInt(3) >2){
        low = 2.6
        high = 5 //Is this setting it universally or locally? Like, it hits it once, and 'high' is 5 forever.
      }
      if(x != 0){
        InflationRate(x) = BigDecimal(InflationRate(x-1) + rand.between(low,high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }
      else {
        InflationRate(x) = BigDecimal(rand.between(low, high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }
    }*/
    inflationOccured.foreach(println)
  }
  def GetInflation(): Array[Double]={
    //InflationRate
    inflationOccured
  }
  def ApplyInflation(price: Double,year: Int): Double={
    //listedprice*inflationOccured(year-2000) inflationO
    //(price*InflationRate(year-2000))
    BigDecimal(price*inflationOccured(year-2000)).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble //Should be functional. SHOULD
    //price * inflationrate/100
    //price + ^
  }

}

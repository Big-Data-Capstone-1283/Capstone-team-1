package Clover.data
import scala.util.Random
import scala.math.BigDecimal
object Formulas {

  private val InflationRate: Array[Double] = new Array[Double](22)

  def SetInflation(): Unit={
    val rand = new Random()
    var low:Double = 0
    var high:Double = 2.5
    for(x<- InflationRate.indices) {
      if(rand.nextInt(3) >2){
        low = 2.6
        high = 5
      }
      InflationRate(x)= BigDecimal(rand.between(low, high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    }
    InflationRate.foreach(println)
  }
  def GetInflation(): Array[Double]={
    InflationRate
  }
  def ApplyInflation(price: Double,year: Int): Double={
    var newPrice = price
    for(x<- 0 to year-2000) newPrice+= newPrice*InflationRate(x)/100
    BigDecimal(newPrice).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
  }

}

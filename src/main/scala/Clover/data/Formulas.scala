package Clover.data
import Clover.Tools.{Random, SweepstakesGen}

import scala.math.BigDecimal
import java.sql.Date
import org.apache.spark.sql.SparkSession
import scala.collection.mutable
import scala.collection.mutable.ListMap
class Formulas(spark:SparkSession) {

  private val exchangeRate = spark.read.format("csv")
    .option("delimiter",",")
    .option("header","true")
    .option("inferSchema","true")
    .load("src/main/scala/Clover/data/files/BaseData/countries.csv").toDF("Country","Currency","Exchange_Rate").collect().flatMap(row => {
    Map(row.toString().replaceAll("\\[|\\]", "").split(",")(0) -> row.toString().replaceAll("\\[|\\]", "").split(",")(2).toDouble)
  }).toMap
  /**Converts the given price into the desired country's currency
   * @param country Country where sale took place
   * @param price price of product
   * @return converted price
   */
  def Convert(country:String,price:Double,fromUSD:Boolean): Double={
    if(fromUSD)BigDecimal(price * exchangeRate(country)).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
    else BigDecimal(price / exchangeRate(country)).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  /**Creates a randomized logistical curve
   *
   * @param R Increase Chance
   * @param D Decrease Chance
   * @param C Crowding Coefficient
   * @param N Starting Value
   * @param start Start Date
   * @param end End Date
   * @return ListMap of Date/Value pairs
   */
  def LogisticGrowthRand(R:Double, D:Double,C:Int,N:Int,start:Long,end:Long): mutable.ListMap[Date,Int]={
    val rand = new SweepstakesGen
    var NN:Double = N
    val NRecord = new Array[Double](((end-start)/86400000L).toInt)
    NRecord(0)=N
    val dates = (start to end by 86400000L).toList.map(x=>new Date(x))
    val maps = ListMap[Date,Int](dates.head->N)
    for(x<- 1 until dates.length-1){
      val increase = if(rand.shuffle(R))R else 0
      val decrease = if(rand.shuffle(D))D else 0
      val delta = (increase-decrease)*NN
      //val delta = C/(N+(C-N)*Math.exp(increase-decrease*x))
      if(NN >C) NN-=Math.abs(delta)
      else NN+=delta
      maps+=(dates(x)->NN.toInt)
      NRecord(x)=Math.round(N)
    }
    maps
  }
  /**Creates a determined logistical curve
   *
   * @param R Increase/decrease over time
   * @param C Crowding Coefficient
   * @param N Starting Value
   * @param start Start Date
   * @param end End Date
   * @return ListMap of Date/Value pairs
   */
  def LogisticGrowth(R:Double,C:Double,N:Int,start:Long,end:Long): mutable.ListMap[Date,Int]={
    var NN:Double = N
    val NRecord = new Array[Double](((end-start)/86400000L).toInt)
    //C/(N+(C-N)*e^-Rt

    NRecord(0)=N
    val dates = (start to end by 86400000L).toList.map(x=>new Date(x))
    val maps = ListMap[Date,Int](dates.head->N)
    for(x<- 1 until dates.length-1){

      val delta = C/(N+(C-N)*Math.exp(-R*x))
      NN += delta
      maps+=(dates(x)->NN.toInt)
      //R=.02,D=.001,C=.0001,100
      //val delta = ((R-(C*N))*N)
      //NN += delta
      //maps+=(dates(x)->NN.toInt)
    }
    maps
  }

}

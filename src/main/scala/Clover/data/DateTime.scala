package Clover.data
import scala.collection.mutable.ArrayBuffer
import java.text.SimpleDateFormat
import Clover.Tools.{Random, SweepstakesGen}
import scala.collection.mutable.ListMap
import scala.math.BigDecimal
import java.sql.Timestamp
import java.io.PrintWriter
import java.sql.Date
import scala.collection.mutable


class DateTime{

  def EpochTimeGenerationTest1(): Unit= {
    val startTime = 946684800000L
    val totalLoop = (365 * 21 + 4 * 30 + 1)
    val rand = new Random()
    val printer = new PrintWriter("testing.csv")
    for (i <- 0 until totalLoop) {
      var newTime: Long = (startTime + i * 8.64e+7).toLong //+ rand.between(1,8.64e+7)).toLong
      val test = new Timestamp(newTime)
      printer.println(test)

    }
  }

    def EpochTimeGenerationTest2(startingMS:Long, endingMS:Long, amountOfSales:Int): Array[Long]={
      val rand = new Random()
      var timeStampArrayBuffer = new ArrayBuffer[Long]()
      val printer = new PrintWriter("testing.csv")
      var i = 0
      while(i < amountOfSales){
        //val generatedTimeStamp = new Timestamp(rand.between(startingMS, endingMS))
        timeStampArrayBuffer += rand.between(startingMS, endingMS)
        i = i + 1
        if(rand.between(1, 14) == 1){
          i = i + rand.between(-7, 7)
        }
      }
      var timeStampArray = timeStampArrayBuffer.toArray
      return timeStampArray
    }

    def dateTimeGenerationTaperPoints(companyName:String, companyStartMS:Long, companyEndMS:Long, companyTaperPoints:Array[Long],
                            companySalesRates:Array[Double], companySalesVariation:Array[Double] ): Array[Timestamp] ={
      /*
      companyName Not used.

      companyStartMS : The absolute start time of the companies purchases in MS

      companyEndMS : The absolute end time of the comampies purchases in MS

      companyTaperPoints : An array of points wherein the values of the amount of what the company was purchasing changes,
      i.e. companyTaperPoints(Jan 1st 2000 in MS, Jan 1st 2001 in MS)
      Assumes that each taperPoint is at least 24 hours apart from the previous, if they're closer points will be skipped.
      !!TIME OF LAST POINT SHOULD BE AFTER companyEndMS!!

      companySalesRates : Array of sales matching with the companyTaperPoints
      You want 10 sales on 2000, and 20 on 2001, then you would put the time in MS in the companyTaperPoints, and put the sales values in here
      i.e. companyTaperPoints(Jan 1st 2000 in MS, Jan 1st 2001 in MS)
           companySalesRates(10,                   20               )
      companySalesVariation : Array of variations matching with companyTaperPoints
      Determines the variation at a specific point, matches with the companyTaperPoints exactly like companySalesRates
      You want a variation of +-five sales per day on 2000, and +-ten per day on 20001, you'd do
      i.e. companyTaperPoints(Jan 1st 2000 in MS, Jan 1st 2001 in MS)
           companySalesRates(10,                   20               )
           companySalesVariation(5,                 10              )

      }
      */

      val rand = new Random()
      var taperTracker = 0
      val randomArrayArrayBuffer = new ArrayBuffer[Array[Long]]()
      var i = companyStartMS
      while(i < companyEndMS){
        if(i > companyTaperPoints(taperTracker + 1)){
          taperTracker = taperTracker + 1
        }
        //var generationAmount = (companySalesRates(taperTracker) + companySalesRates(taperTracker + 1))/2 + rand.between((-(companySalesVariation(taperTracker) + companySalesVariation(taperTracker))/2),
        //  (companySalesVariation(taperTracker) + companySalesVariation(taperTracker))/2) //Says round is depreciated, and that there's no reason to round an int
        val weightPart1:Float = (i - companyTaperPoints(taperTracker))
        val weightPart2:Float = (companyTaperPoints(taperTracker + 1) - companyTaperPoints(taperTracker))
        val weightPart3:Float = 1 - (weightPart1/weightPart2)


       // var firstWeight:Float = (1 - ((i - companyTaperPoints(taperTracker)) / (companyTaperPoints(taperTracker + 1) - companyTaperPoints(taperTracker))))

        var generationAmount = (
          (companySalesRates(taperTracker)*weightPart3) + (companySalesRates(taperTracker + 1)*(1 - weightPart3)) +
          rand.between(
            -((companySalesVariation(taperTracker)*weightPart3) + companySalesVariation(taperTracker + 1)*(1 - weightPart3)),
            ((companySalesVariation(taperTracker)*weightPart3) + companySalesVariation(taperTracker + 1)*(1 - weightPart3))
                      )
          ).toInt
        //println(generationAmount)
        if(0 >= generationAmount){
          generationAmount = 0
        } else {
          var dateArray = new Array[Long](generationAmount)
          for (j <- 0 until generationAmount) {
            //what am I packing the dates into, exactly? How am I returning it? Flatmap append all tables into one big array?
            dateArray(j) = rand.between(i, i + 8.64e+7.toLong) //Get timestamps between the start of the day and the end of the day
          }
          //dateArray could be sorted here, or in the actual returnArray assignment
          randomArrayArrayBuffer += dateArray

          i = i + 8.64e+7.toLong
        }
      }

      val returnArray = randomArrayArrayBuffer.flatten.map(x => new Timestamp(x)).toArray
      returnArray
    }

  def dateTimeGenerationTaperPoints(companyName:String, companyStartSales:Int, companyStartMS:Long, companyEndMS:Long, companyTaperPoints:Array[Long],
                                    companySalesChange:Array[Double], companySalesVariation:Array[Double] ): Array[Timestamp] ={
    /*
    companyStartSales Initial sales of the company

    companyName Not used.

    companyStartMS : The absolute start time of the companies purchases in MS

    companyEndMS : The absolute end time of the comampies purchases in MS

    companyTaperPoints : An array of points wherein the values of the amount of what the company was purchasing changes,
    i.e. companyTaperPoints(Jan 1st 2000 in MS, Jan 1st 2001 in MS)
    Assumes that each taperPoint is at least 24 hours apart from the previous, if they're closer points will be skipped.
    !!TIME OF LAST POINT SHOULD BE AFTER companyEndMS!!

    companySalesChange : Array of sales matching with the companyTaperPoints
    You want 10 sales on 2000, and 20 on 2001, then you would put the time in MS in the companyTaperPoints, and put the sales values in here
    i.e. companyTaperPoints(Jan 1st 2000 in MS, Jan 1st 2001 in MS)
         companySalesRates(10,                   20               )
    companySalesVariation : Array of variations matching with companyTaperPoints
    Determines the variation at a specific point, matches with the companyTaperPoints exactly like companySalesRates
    You want a variation of +-five sales per day on 2000, and +-ten per day on 20001, you'd do
    i.e. companyTaperPoints(Jan 1st 2000 in MS, Jan 1st 2001 in MS)
         companySalesRates(10,                   20               )
         companySalesVariation(5,                 10              )
    def formatDateForSitemap(date: Long): String = {
      val d = new Date(date)
      new SimpleDateFormat("yyyy-MM-d").format(d)
    }
    */

    val rand = new Random()
    var companySales = companyStartSales
    var taperTracker = 0
    var randomArrayArrayBuffer = new ArrayBuffer[Array[Long]]()
    var i = companyStartMS
    while(i < companyEndMS){
      if(i > companyTaperPoints(taperTracker + 1)){
        taperTracker = taperTracker + 1
      }
      companySales = companySales + companySalesChange(taperTracker).toInt
      //var generationAmount = (companySalesRates(taperTracker) + companySalesRates(taperTracker + 1))/2 + rand.between((-(companySalesVariation(taperTracker) + companySalesVariation(taperTracker))/2),
      //  (companySalesVariation(taperTracker) + companySalesVariation(taperTracker))/2) //Says round is depreciated, and that there's no reason to round an int
      val weightPart1:Float = (i - companyTaperPoints(taperTracker))
      val weightPart2:Float = (companyTaperPoints(taperTracker + 1) - companyTaperPoints(taperTracker))
      val weightPart3:Float = 1 - (weightPart1/weightPart2)


      // var firstWeight:Float = (1 - ((i - companyTaperPoints(taperTracker)) / (companyTaperPoints(taperTracker + 1) - companyTaperPoints(taperTracker))))

      var generationAmount =
        (companySales +
          rand.between(
            -((companySalesVariation(taperTracker)*weightPart3) + companySalesVariation(taperTracker + 1)*(1 - weightPart3)),
            ((companySalesVariation(taperTracker)*weightPart3) + companySalesVariation(taperTracker + 1)*(1 - weightPart3))
          )
        ).toInt
      println(generationAmount)
      if(0 >= generationAmount){
        generationAmount = 0
      } else {
        var dateArray = new Array[Long](generationAmount)
        for (j <- 0 until generationAmount) {
          dateArray(j) = rand.between(i, i + 8.64e+7.toLong)
        }
        //dateArray could be sorted here, or in the actual returnArray assignment
        randomArrayArrayBuffer += dateArray

        i = i + 8.64e+7.toLong
      }
    }

    val returnArray = randomArrayArrayBuffer.flatten.map(x => new Timestamp(x)).toArray
    return returnArray
  }

  /**Creates a randomized logistical curve
   *
   * @param R Increase Chance
   * @param D Decrease Chance
   * @param C Crowding Coefficient
   * @param n Starting Value
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
      if(NN >C) NN-=delta
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
    NRecord(0)=N
    val dates = (start to end by 86400000L).toList.map(x=>new Date(x))
    val maps = ListMap[Date,Int](dates.head->N)
    for(x<- 1 until dates.length-1){
      //R=.02,D=.001,C=.0001,100
      val delta = ((R-(C*N))*N)
      NN += delta
      maps+=(dates(x)->NN.toInt)
    }
    maps
  }


  /*
  val desiredTime = "3/20/2017 16:5:45"

  val format = new java.text.SimpleDateFormat("M/dd/yyyy HH:m:ss")
  val time = format.parse(desiredTime).getTime()
  print(time)


  * Start at 2000 Jan 1 00:00
  * For each day, check what day it is using (epochtime/86400 + 4) % 7
  * If it's a Saturday, increase the upper/lower limit of sales to be generated
  * generate sales
  * pack sales into df at the same time.
  *
  *
  * */




  //private var year = 0
 /* var month = 0
  var day = 0
  var hour = 0
  var minute = 0
  var second = 0

    days_since_epoch = localtime / 86400;
	// 1 Jan 1970 was a Thursday, so add 4 so Sunday is day 0, and mod 7
	day_of_week = (days_since_epoch + 4) % 7;





  /*def this(year:Int,month:Int,day:Int,hour:Int,minute:Int,second:Int){

  }*/
  def year(): Int={this.year}
  def year(x:Int): Unit={
    if(x < 1)this.year=1
    else if(x>12)this.year=12
  }*/

}

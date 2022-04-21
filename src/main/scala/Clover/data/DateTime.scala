package Clover.data
import com.google.protobuf.TextFormat.printer
import scala.collection.mutable.ArrayBuffer

import scala.util.Random
import scala.math.BigDecimal
import java.sql.Timestamp
import java.io.PrintWriter


class DateTime{

  def EpochTimeGenerationTest1(): Unit={
    val startTime = 946684800000L
    val totalLoop = (365*21 + 4*30 + 1)
    val rand = new Random()
    val printer = new PrintWriter("testing.csv")
    for(i <- 0 until totalLoop){
      var newTime:Long = (startTime + i * 8.64e+7).toLong //+ rand.between(1,8.64e+7)).toLong
      val test = new Timestamp(newTime)
      printer.println(test)

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

    printer.close()

/*
* Start at 2000 Jan 1 00:00
* For each day, check what day it is using (epochtime/86400 + 4) % 7
* If it's a Saturday, increase the upper/lower limit of sales to be generated
* generate sales
* pack sales into df at the same time.
*
*
* */


  }

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

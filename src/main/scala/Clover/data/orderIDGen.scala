package Clover.data
import Clover.SweepstakesGen


import scala.util.Random

object orderIDGen extends App {
  println(Random.alphanumeric.take(15).mkString)

  val sweepstakesGen = new SweepstakesGen()
  def orderIDGen (IDLength : Int = 16 ) : Unit = {
    var orderID = ""
    for (i <- 0 until IDLength) {
      val determineElement = sweepstakesGen.shuffle(.5)

      determineElement match{
        case true => Random.nextInt(9).toString
        case false=> Random.alphanumeric.take(5).mkString
      }
    }
  }

}

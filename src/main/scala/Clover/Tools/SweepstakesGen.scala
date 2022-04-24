package Clover.Tools

import scala.math._
import scala.util.Random

class SweepstakesGen { //coment for test purposes
  //this class return true at the odd you want. For example if you want 10%
  //chance to have true call method shuffle(0.10) and
  // there is a 10% odd that the result will be true
  // jaceguai 4/20/2022 18:39 Est

  //uncomment for test purposes
  /*object SweepstakesGen extends App {
  var conttrue = 0
  var contfalse = 0
  var result = false
  val n = 1
  for(i <- 1 to n){
    result = shuffle(0.05)
    if(result == true) conttrue += 1 else contfalse += 1
  }
  val resultFalse = contfalse/n.toDouble
  val resultTrue = conttrue/n.toDouble
  println(f"Events = $n True = $resultTrue%.2f%% False = $resultFalse%.2f%%")*/


  //returns true when win the sweepstakes
  def shuffle(odd: Double): Boolean={
    val random = Random.nextDouble()
    if(floor(random/odd) == 0 ) return true
    else return false
  }
}

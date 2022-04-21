package Clover.data
import scala.util.Random

object Messifier {
  val Demon = new Random()
  //debug main
   /*
  def main(args: Array[String]): Unit = {
    val total = 25
    var count = 0
    while(count<total){
      count+=1
      println(MessString(10))
      println(MessInt(10))
    }
  } // */

  def MessString(size:Int): String ={
    var ressy = ""

    val rolls = Demon.nextInt(size)
    var count = 0
    while(count<rolls){
      ressy+=badString(Demon.nextInt(badString.length))
      count+=1
    }
    if(ressy.trim=="")
      ressy+="c:"

    ressy
  }

  def MessInt(size:Int): Int ={
    var ressy = 0

    val rolls = Demon.nextInt(20)
    var count = 0
    while(count<rolls){
      ressy+=Demon.nextInt(size)
      ressy*=Demon.nextInt(size/2)+1
      ressy-=Demon.nextInt(size)
      ressy/=Demon.nextInt(size/2)+1
      count+=1
    }

    ressy
  }

  val badString = Array("cheetos","spy"," ","","www","free",".net","coo","kie","pants","funny","42","[","]"," ","   ","=","+","=-=",
    "34","8","1","2","3","4","5","6","7","9","0","bean"," "," ","EEE","hero","brine","to","ast","er","uh","um","...","wait","what",
    " ","pasta","lo","luc","re","BUbBLE","13131","435","135","720","999","xX","Xx","X"," ","\\","//",":3",";-;","UWU","OWO","hi",
    "x3","Biscuit","b","{","}","-","/","city","state","City","wowow","ocean","PARA","DISE","pass","word","apples","1-800","44",
    "sh","aaa","626","a","didney","worl","dis","ney","bobble","cake","00","ween","waf","fle","op","en","'","\"")
}

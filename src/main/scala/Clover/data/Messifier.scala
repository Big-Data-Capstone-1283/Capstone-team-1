package Clover.data
import scala.util.Random
import java.sql.Timestamp

object Messifier {
  val Demon = new Random()
  //debug main
  // /*
  def main(args: Array[String]): Unit = {
    val total = 100
    var count = 0
    while(count<total){
      count+=1
      val wow = debugSel(Demon.nextInt(debugSel.length))
      println(s"$wow: ${MessString(wow)}")
      //println(MessInt(10))
      //println(MessDouble(100))
      //println(MessTime())
    }
  } // */

  val debugSel = Array("name","city","country","website","product","category","other")

  def MessString(category:String): String ={
    var ressy = ""
    var cate = category

    if(Demon.nextInt(100)==0)
      cate = "other"

    cate.toLowerCase match {
      case "name" | "names" => {
        val total = Demon.nextInt(2)+2
        var count = 0
        while(count<total){
          count+=1
          ressy+=badNames(Demon.nextInt(badNames.length))+" "
        }
        ressy = ressy.trim
      }
      case "city" | "cities" => ressy=badCities(Demon.nextInt(badCities.length))
      case "country" | "countries" => ressy=badCountries(Demon.nextInt(badCountries.length))
      case "product" | "products" => ressy=badProducts(Demon.nextInt(badProducts.length))
      case "category" | "categories" => ressy=badCategories(Demon.nextInt(badCategories.length))
      case "website" | "websites" | "company" | "companies" => ressy=badWebsites(Demon.nextInt(badWebsites.length))
      case _ => {
        val total = Demon.nextInt(100)+1
        var count = 0
        while(count<total){
          count+=1
          ressy+=badOther(Demon.nextInt(badOther.length))
        }
      }
    }

    ressy
  }

  val badOther = Array("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
    "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
    "1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","-","_","=","+",
    "[","{","]","}","\\","|",";",":","'","\"",",","<",".",">","/","?"," ")
  val badNames = Array("J0hn","J0hn5","1337","Real","Person","null","V3t0","8","Mighty","Courage","The Dog","The Kid","Totally Real",
    "Not A Bot","Scammer","23","49","Robot","Legit","Definite","Trust Me","Not A Liar","Howdy","Hi","Hello","Aloha","7","4","Mr.",
    "Roboto","Ms.","Mrs.","Normalman","Normie","Normal","Not Suspicious","Actual Real","Actual","Spy","Leech","Torrent","Virus",
    "Trojan","Malware","1=1","OR","AND","1/0","Tiny Robot","Cr4b","The Robot","B0B","Ch3r","Cherry","Li'l","Bean","Little","")
  val badCities = Array("New Bark City","Stormwind City","Orgrimmar","Thunder Bluff","Lumiose City","Anastar City","Lively Town",
    "Darnassus","Silvermoon City","Lordaeron","Bilgewater Port","Port Ollivan","Mistral City","Ironforge","Lavender Town","Post Town",
    "Treasure Town","Pokemon Square","Serene Village","Cyber City","Castle Town","Jerry's Tree","Sandgem Town","Jubilife City",
    "Jubilife Village","Hearthglen","Tyr's Hand","Dalaran","Icecrown","New Avalon","Deepholm","Stoneholm","UnLondon","Fire Island Volcano",
    "Snowdin","Waterfall","Hotlands","The Core","Wouldn't you like to know?","")
  val badCountries = Array("Unity Stats","Khaz Modan","Mulgore","Highmountain","Bloodhoof","Alola","Kalos","Unova","Sinnoh",
    "Hoenn","Johto","Kanto","Galar","Hisui","Altarac","Arathor","Stormheim","Durotar","Elwynn","La Noscea","Thanalan","Ishgard",
    "Europa","Wonderland","United Stats","Unity States","Amani","Razorfen","Scarlet","Air Continent","Water Continent","Sand Continent",
    "Grass Continent","The Underground","Undertale","Deltarune","Azeroth","Kalimdor","Eggland","Wouldn't you like to know?","")
  val badProducts = Array("Crumbs","2 bagels","The souls of the innocent","A smoothie","My mixtape","Fire, in handheld form",
    "Malk","Molk","Pocket-sized playground","Your dog","Emu","The entirety of Youtube","Twitch.tv","An NFT","Stuff","A weapon",
    "Air","Air, but in a can","Fire","Water, without a container","A baboon","Gun's Rose","A band of Beatles","Something","Pocket-sized Pony",
    "Pocket-sized Robot Army","A Knife!","The entirety of Egypt","A city","Music","Funky Beats","A Pokeball","The entirety of New Jersey",
    "Religion","A church","Meowster","A bagel","Potassium","Battery Acid","Wouldn't you like to know?","Something or other","A werewolf",
    "Fox Pajamas with built in fox noises","Enough cars to build a pile of cars to the moon","The moon","Jupiter","A star","The sun",
    "Wheatley, from Portal 2","Pocket-sized Egyptian Pyramid","A fan of U2","The rights to the word 'Dance'","Legal fees","Safety",
    "Pictures of Spiderman","A newspaper","Aluminium","The entirety of Github","The entirety of America","Canada","Fruit Salad",
    "Dunno, but they paid with cash!","Some of your favorite things","A surprise","A gift","A box","2 Boxes","The entirety of Europe",
    "Brazil")
  val badCategories = Array("Bombs","Goblin Tech","Escapism","A smoothie","Unreal Objects","Pocket-sized Tech","Pool","Cool Stuff",
    "I dunno, dude.","Fitness 4 U","Stuff down the street","Real Things","<category>","Optical Illusions","Illusory Tech","Bagels",
    "France","The Jungle","Singing or something","NO!","Country-shopping","Eggs","Rhythm Heaven","Pokemon","Olives","Ohio","Cats",
    "Wall-E","GET THE BANANA","Wouldn't you like to know?","Life","I forgot","How am I supposed to know?","Space Exploration",
    "Fazbear Entertainment","Spiderman","Moonbase Tech","Yummy Yummy","The Oscar Awards")
  val badWebsites = Array("Amazonian","Goggle","Ebbay","Welmert","Targy","Stop Shopping","Shopright","OK-Mart","Games Top",
    "Wetzy","We-bay","Wii Shop Channel","The Internet","Wouldn't you like to know?","FazbearEntertainment","TreetopShopbrand",
    "UberShop","Danceparty.WEB","hats","Guthib","a-Website","IMDB")

  def MessInt(vary:Int): Int ={
    var ressy = 0

    val rolls = Demon.nextInt(20)+1
    var count = 0
    while(count<rolls){
      // have a catch thing about preventing /0
      ressy+=Demon.nextInt(vary)
      ressy*=Demon.nextInt(vary/2)+1
      ressy-=Demon.nextInt(vary)
      ressy/=Demon.nextInt(vary/2)+1
      count+=1
    }

    ressy
  }

  def MessDouble(vary:Int): Double ={
    var ressy = 0.0

    var rolls = Demon.nextInt(20)+1
    var count = 0
    while(count<rolls){
      ressy+=Demon.nextDouble()*vary
      ressy*=(Demon.nextDouble()+1)*vary/2
      ressy-=Demon.nextDouble()*vary
      ressy/=(Demon.nextDouble()+1)*vary/2
      count+=1
    }

    BigDecimal(ressy).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def MessTime(): Timestamp ={
    // Not a lot of finesse for this one.
    new Timestamp(Demon.nextLong(915166800000L))
  }
}

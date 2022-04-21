package Clover.data
import scala.util.Random
import java.sql.Timestamp

object Messifier {
  val Demon = new Random()
  //debug main
   /*
  def main(args: Array[String]): Unit = {
    val total = 1000
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

  // val debugSel = Array("name","city","country","website","company","product","category","other")

  def MessString(category:String): String ={
    var ressy = ""
    var cate = category

    if(Demon.nextInt(50)==0)
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
      case "company" | "companies" => ressy=badCompanies(Demon.nextInt(badCompanies.length))
      case "website" | "websites" => ressy=siteStart(Demon.nextInt(siteStart.length))+
        badCompanies(Demon.nextInt(badCompanies.length)).filterNot(_.isWhitespace).toLowerCase+
        siteEnd(Demon.nextInt(siteEnd.length))
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

  //<editor-fold name="Mess Lists"

  val badOther = Array("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
    "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
    "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
    "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
    "1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","-","_","=","+",
    "[","{","]","}","\\","|",";",":","'","\"",",","<",".",">","/","?"," ")

  val badNames = Array("J0hn","J0hn5","1337","Real","Person","null","V3t0","8","Mighty","Courage","The Dog","The Kid","Totally Real",
    "Not A Bot","Scammer","23","49","Robot","Legit","Definite","Trust Me","Not A Liar","Howdy","Hi","Hello","Aloha","7","4","Mr.",
    "Roboto","Ms.","Mrs.","Normalman","Normie","Normal","Not Suspicious","Actual Real","Actual","Spy","Leech","Torrent","Virus",
    "Trojan","Malware","1=1","OR","AND","1/0","Tiny Robot","Cr4b","The Robot","B0B","Ch3r","Cherry","Li'l","Bean","Little","Beep",
    "Boop","","Sneaky","Biscuit","Cookie","Waffle","Cool","Dude","Fall","Out","Boy","Toto","Cave","Johns0n","Meow","Mow","-,.,-",
    "Pikachu","pftq","Waterflame","F-777","Unknown","Stranger","Danger","CL04K","Sn34ky","B34N")

  val badCities = Array("New Bark City","Stormwind City","Orgrimmar","Thunder Bluff","Lumiose City","Anastar City","Lively Town",
    "Darnassus","Silvermoon City","Lordaeron","Bilgewater Port","Port Ollivan","Mistral City","Ironforge","Lavender Town","Post Town",
    "Treasure Town","Pokemon Square","Serene Village","Cyber City","Castle Town","Jerry's Tree","Sandgem Town","Jubilife City",
    "Jubilife Village","Hearthglen","Tyr's Hand","Dalaran","Icecrown","New Avalon","Deepholm","Stoneholm","UnLondon","Fire Island Volcano",
    "Snowdin","Waterfall","Hotlands","The Core","Wouldn't you like to know?","","Magmoor Caverns","New New City","Newest Newer City",
    "Mew York City","Mewami","Not Kansas","Rorikstead","ICameIWentIConquered City","LeoPard Lakeia","Helio Bay","Super Hero Land",
    "Horror Island","Totally Tubular","Hylia","BrightHoof","Terry Town","Town","City Town","Town City","Village Town","Town Village",
    "City Square","Village Town Square City Place","My House","It's a mystery!","We'll never know!","Your other pocket")

  val badCountries = Array("Unity Stats","Khaz Modan","Mulgore","Highmountain","Bloodhoof","Alola","Kalos","Unova","Sinnoh",
    "Hoenn","Johto","Kanto","Galar","Hisui","Altarac","Arathor","Stormheim","Durotar","Elwynn","La Noscea","Thanalan","Ishgard",
    "Europa","Wonderland","United Stats","Unity States","Amani","Razorfen","Scarlet","Air Continent","Water Continent","Sand Continent",
    "Grass Continent","The Underground","Undertale","Deltarune","Azeroth","Kalimdor","Eggland","Wouldn't you like to know?","",
    "Unite Kongdom","Oshawodia","Splaingland","Englance","Austriceland","Hamermany","Rush A","Candia","Canadia","Cancanland",
    "Somewhere, over the rainbow","Mew Zealand","Blazer","Skyrim","Whiterun","Morthal","Darkness","Light","Japina","Japussia","South Alucard",
    "Frankenland","Your house","My house","Someone else's house","The Ant Kingdom","An Anime","Your Pocket","The country of Austin",
    "Someplace where the sun don't shine")

  val badProducts = Array("Crumbs","2 bagels","The souls of the innocent","A smoothie","My mixtape","Fire, in handheld form",
    "Malk","Molk","Pocket-sized playground","Your dog","Emu","The entirety of Youtube","Twitch.tv","An NFT","Stuff","A weapon",
    "Air","Air, but in a can","Fire","Water, without a container","A baboon","Gun's Rose","A band of Beatles","Something","Pocket-sized Pony",
    "Pocket-sized Robot Army","A Knife!","The entirety of Egypt","A city","Music","Funky Beats","A Pokeball","The entirety of New Jersey",
    "Religion","A church","Meowster","A bagel","Potassium","Battery Acid","Wouldn't you like to know?","Something or other","A werewolf",
    "Fox Pajamas with built in fox noises","Enough cars to build a pile of cars to the moon","The moon","Jupiter","A star","The sun",
    "Wheatley, from Portal 2","Pocket-sized Egyptian Pyramid","A fan of U2","The rights to the word 'Dance'","Legal fees","Safety",
    "Pictures of Spiderman","A newspaper","Aluminium","The entirety of Github","The entirety of America","Canada","Fruit Salad",
    "Dunno, but they paid with cash!","Some of your favorite things","A surprise","A gift","A box","2 Boxes","The entirety of Europe",
    "Brazil","Shiny red boots","A sparkly umbrella","BEANS","Pocket-sized pictures of Spiderman","A dragon","A nifty pair of gloves",
    "An old candy bar","","A Spaceship","A rocketship","An escape pod","2 smoothies","The font from Team Fortress 2","Personal Wormhole Generator",
    "Popsickle","Too many apples for one person","A Happy Meal","A dinosaur","An old musical that takes place in a high school",
    "Some vampire story","A robot that creates Eurobeat music","A license to dance","Pocked-sized Dragon Companion","A house","A shed",
    "A house, but it's on wheels","A house, but it floats","A house, but it's in the sky","A house, but there's nothing there","Nothing",
    "A tutorial on making your neighbors submit a noise complaint","A rip-off of Cards Against Humanity","A rip-off of Monopoly",
    "A deck of playing cards, but it's missing the red suits","A deck of playing cards, but it's missing the black suits","An ace of spades",
    "The entirety of Sweden","A flaming sword","A flaming shovel","A pillar of flame","Something on fire","Fire, but it's covering something else",
    "Odds and ends","That sort of thing","Trinkets","The song that never ends","An Aperture Science backed Portal Gun","A Standardized Test",
    "Nonstop Eurobeat","A jumpsuit","w a t e r","Water, but in morse code","A guide on speaking silently","A Magic Show","A typo","A topy",
    "A costume of themself","A nightlight","The entirety of the Atlantic Ocean","Michigan","The number 0","Your self-confidence",
    "A college","A ticket into college","A siren","A siren, but it's the other kind of siren","A pirate","A hearty laugh-track",
    "A pinhead","A tree that fell when nobody could hear it","Static noise","I can't tell, but I don't like it.","What do you think?",
    "A scarf, but it's too big","Poprocks, but only blue raspberry flavor","Actual Blue Raspberries","Adult Carrot","An NFT, but even worse",
    "Clearly bought into a scam","Forgot how to count","A blanket that is literally too thin to touch","pixels","I Forgot",
    "A book explaining what Bad Data is","Very loud EDM","A megaphone","A megaphone specifically for yelling at neighbors",
    "An entire zoo","An entire farm","An entire city","The entirety of the world","We are not worthy to know of such","A Goa Codex... whatever that is",
    "I can't make it out from here","The store's entire stock","Your entire stock","The stock market, yep, all of it","A bank",
    "A bank of pig-like appearance","Oink-maker","A flying pig","A flying car","I think it's real","A Dreamstate Paradox",
    "The last poptart in the store","A deli sandwich","Crisps in the form of Chips","285 Potatoes","A hat","A fancy hat","A funny hat",
    "An ugly hat","Why would you ever pick out this hat","Actually nothing","A smile","The funny")

  val badCategories = Array("Bombs","Goblin Tech","Escapism","A smoothie","Unreal Objects","Pocket-sized Tech","Pool","Cool Stuff",
    "I dunno, dude.","Fitness 4 U","Stuff down the street","Real Things","<category>","Optical Illusions","Illusory Tech","Bagels",
    "France","The Jungle","Singing or something","NO!","Country-shopping","Eggs","Rhythm Heaven","Pokemon","Olives","Ohio","Cats",
    "Wall-E","GET THE BANANA","Wouldn't you like to know?","Life","I forgot","How am I supposed to know?","Space Exploration",
    "Fazbear Entertainment","Spiderman","Moonbase Tech","Yummy Yummy","The Oscar Awards","","Cotton Candyland Place","Northlands",
    "Streamed live on Youtube","Pyrotechnics","Livestreaming","Content Creation","Fishing in Emails","Piratehood","Magic Missles",
    "Sorcery","The Greatest Stuff on Earth","Some spooky stuff","Gear for your Dragon")

  val badCompanies = Array("Amazonian","Goggle","Ebbay","Welmert","Targy","Stop Shopping","Shopright","OK-Mart","GamiesTop",
    "Wetzy","WeBay","Wii Shop Channel","The Internet","Fazbear Entertainment","Treetop Shopbrand","Wow Place","Impressive Sites",
    "UberShop","Danceparty","hats","Guthib","a-Website","IMDB","The Oscars","TheEntireInternet","LostWebsites","A Very Reliable Website",
    "Human Shopping Place","The Fire Shop","Flamelands","Mixer","Nickelbork","freemans","followerbase","Aperture Science","Yarrr-Har",
    "Wizards Alliance","Merica","Meow","Woof","Silent Hill","Umbrella","Dragon Lord","Sewage Inc.","Fontaine Futuristics","Shinra","Rapture Farms",
    "Amazin","WEYLAND-YUTANI","DAHL","Space Pirates llc","Real Dinosaurs R' US","Son Depot","Human Farms","Paper Planet","Supa Corp","CatGonWater Inc")

  val siteStart = Array("wmw.","oop.","hds.","dfs.","sco.","wow.","site.","sit.","pow.","gog.","run.","hii.","hai.","hec.","ded.")
  val siteEnd = Array(".net",".com",".org",".gov",".real",".tru",".tv",".db",".web",".co.uk",".oop",".csv",".away",".bye",".ded")
  //</editor-fold>

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

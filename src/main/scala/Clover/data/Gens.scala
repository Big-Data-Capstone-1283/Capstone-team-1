package Clover.data

import scala.util.Random

object Gens {
  // DEBUG MAIN
  def main(args: Array[String]): Unit = {
    CreatePeople(500)
  }

  def CreatePeople(count:Int): Unit ={
    var test = 0
    while(test<count){
      test+=1
      val country = countries(God.nextInt(countries.length))
      val gender = sexes(God.nextInt(sexes.length))
      var city = ""
      country match {
        case "United States" => city = AmCities(God.nextInt(AmCities.length))
        case "Russia" => city = RsCities(God.nextInt(RsCities.length))
        case "China" => city = ChCities(God.nextInt(ChCities.length))
        case "India" => city = InCities(God.nextInt(InCities.length))
        case "South Africa" => city = SAfCities(God.nextInt(SAfCities.length))
        case "Mexico" => city = MxCities(God.nextInt(MxCities.length))
      }
      println(s"ID:$test | "+Name(country,gender)+s" ($city, $country)")
    }
  }

  // Our faithful creator
  val God = new Random()

  // used for name generation, irrelevant to rest of data.
  val sexes = Array("Male","Female")

  /*
    Country List; (As taken from XCOM's localization code, as were the names)
    America (Am)
    Russia (Rs)
    China (Ch)
    India (In)
    South Africa (Af) (SAf)
    Mexico (Mx)
   */

  // Repeat countries for more weight??
  // If we want any countries to appear more frequently, just put them in the array multiple times.
  val countries = Array("United States","Russia","China","India","South Africa","Mexico")

  //<editor-fold desc="Cities">

  val AmCities = Array("Miami","New York City","Los Angeles","Chicago")
  val RsCities = Array("Moscow","Saint Petersburg","Samara","Chelyabinsk")
  val ChCities = Array("Beijing","Shanghai","Guangzhou","Chengdu")
  val InCities = Array("Delhi","Mumbai","Chennai","Jaipur")
  val SAfCities = Array("Cape Town","Durban","Pretoria","Polokwane")
  val MxCities = Array("Mexico City","Puebla","Monterrey","Mérida")
  //</editor-fold>

  //<editor-fold desc="Names">

  // All names here are just taken from XCOM's character generator.
  val AmMFirstNames = Array("Shane","Rob","Brad","Cameron","Chris","Neal","Jordan","Charlie","Jeff","Caleb","Eric","Samuel","Cory","Neil","Shaun","Nat",
  "Calvin","Bob","James","Joe","Toby","Andy","Gary","Ruben","Matt","Geoff","Sidney","William","Tariq","Christian","Donny","Tim","Ben","Ross","Stephen",
  "Duane","Flynn","Jake","Greg","David","Arthur","Mickey","Daniel","Brian","Mike","Chase","Jeremy","Sam","Nick","Alex","Tom","London","Jack","Derek")
  val AmFFirstNames = Array("Mary","Lisa","Elizabeth","Jennifer","Susan","Karen","Helen","Sandra","Donna","Carol","Sharon","Michelle","Laura","Sarah",
  "Kim","Jessica","Cynthia","Angela","Melissa","Amy","Anne","Rebecca","Kate","Amanda","Christine","Janet","Catharine","Diane","Alice","Julie","Heather","Jill",
  "Joan","Judy","Ashley","kelly","Nicole","Denise","Jane","Laurie","Rachel","Andrea","Marilyn","Bonnie","Tina","Emily","Dawn","Tracy","Tiffany","Wendy",
  "Shannon","Carrie","April","Jamie","Megan","Erin","Sally","Erica","Stacy","Brittany")
  val AmLastNames = Array("Freeman","Jefferson","Washington","Wheeler","Martz","Hudson","Smith","Johnson","Williams","Black","Bradley","Jones","Brown","Davis",
  "Cooke","Cunningham","Wilson","Moore","Taylor","Anderson","Thomas","Jackson","White","Harris","Thompson","Garcia","Gordon","Burton","Robinson","Clark",
  "Welsh","Rodriguez","Meyers","Lewis","Lee","Walker","Martin","Hall","Allen","Murphy","Murray","Young","Hernandez","King","Wright","Perry","Hill","Scott","Green",
  "Adams","Baker","Cartwright","Mitchell","Roberts","Turner","Phillips","Campbell","Parker","Evans","Edwards","Collins","Morris","Rodgers","Reed","Cook","Morgan",
  "Bell","Bailey","Cooper","Richardson","Cox","Ward","Peterson","Gray","James","Watson","Ramirez","Cook","Brooks","Kelly","Sanders","Bennett","Wood","Barnes",
  "Ross","Henderson","Coleman","Jenkins","Powell","Long","Patterson","Flores","Butler","Bryant","Alexander","Russell","Griffin","Hayes","Hamilton","Graham",
  "Sullivan","Woods","Cole","West","Jordan","Owens","Reynolds","Fisher","Ellis","Harrison","Gibson","Marshall","Wells","Simpson","Stevens","Reynolds","Tucker",
  "Porter","Hunter","Hicks","Crawford","Henry","Boyd","Mason","Kennedy","Warren","Dixon","Burns","Gordon","Shaw","Rice","Robertson","Hunt","Daniels","Palmer",
  "Mills","Nichols","Grant","Knight","Ferguson","Rose","Hawkins","Dunn","Perkins","Hudson","Spencer","Gardner","Payne","Pierce","Berry","Matthews","Wagner",
  "Willis","Ray","Watkins","Olson","Carroll","Duncan","Snyder","Hart","Cunningham","Andrews","Harper","Fox","Riley","Armstrong","Carpenter","Weaver","Greene",
  "Lawrence","Elliot","Lane","Nelson","Stewart","Howard","Price","Hughes","Wallace","McDonald","Webb","Holmes","Stone","Arnold")

  val RsMFirstNames = Array("Aleksandr","Sergey","Vladimir","Andrey","Alexei","Dmitriy","Mikhail","Igor","Yuri","Nikolai","Andrey","Anatoly","Ivan","Maxim",
    "Denis","Yegor","Artyom","Viktor","Konstantin","Kirill","Vasily","Stanislav","Pyotr","Vanya","Boris","Kostya")
  val RsFFirstNames = Array("Galina","Elena","Olga","Tatyana","Irina","Natalia","Anna","Svetlana","Maria","Marina","Ludmila","Anastasiya","Alexandra","Polina",
    "Sofia","Yekaterina","Susanna","Eva")
  val RsMLastNames = Array("Smirnov","Ivanov","Kuznetsov","Popov","Sokolov","Lebedev","Kozlov","Novikov","Morozov","Petrov","Volkov","Solovyov","Vasilyev",
    "Zaytsev","Pavlov","Semyenov","Chepurnov","Golubev","Vinogradov","Bogdanov","Vorobyov","Sidorov","Gusev","Dobrynin","Zinchenko","Ignatyev","Ilyushin",
    "Malakhov")
  val RsFLastNames = Array("Smirnova","Ivanova","Kuznetsova","Popova","Sokolova","Lebedeva","Kozlova","Novikova","Morozova","Petrova","Volkova","Solovyova",
    "Vasilyeva","Zaytseva","Pavlova","Semyenova","Chepurnova","Golubeva","Vinogradova","Bogdanova","Vorobyova","Sidorova","Guseva","Dobrynina","Zinchenko",
    "Ignatyeva","Ilyushina","Malakhova")

  val ChMFirstNames = Array("Wei","Hao","Dong","Ming","Tao","Zhuang","Chen","Cheng","Chi","Kong","Fei","Guang","Ho","Jun","Kaun-Yin","Lian","Liang","Lok",
    "Long","On","Park","Shaiming","Shen","Sheng","Sying","Ye")
  val ChFFirstNames = Array("Ying","Ping","Xue","An","Da-Xia","Fang Yin","Feng","Huan Yue","Hui Ying","Jia Li","Jiang Li","Li Mei","Li Ming","Xiao Chen",
    "Yue Wan","Yue Ying","Zhi","Li Wei")
  val ChLastNames = Array("Li","Wang","Zhang","Zhao","Chen","Yang","Wu","Liu","Huang","Zhou","Xu","Zhu","Lin","Sun","Ma","Gao","Hu","Zheng","Guo","Xiao",
    "Ho","Song","Shen","Deng","Liang","Ye","Fong","Cheng","Pan","Yuan","Peng")

  val InMFirstNames = Array("Ananda","Abhijit","Jowar","Karan","Narayan","Navneet","Suresh","Saurabh","Amit","Arpit","Amitabh","Saman","Abhinav","Aditya",
    "Akash","Anish","Arjun","Dhruv","Ishan","Krishna","Mihir","Neel","Nikhil","Pranav","Rahul","Rishi","Rajan","Rohit","Shankar","Varun","Vedant","Yash")
  val InFFirstNames = Array("Arpita","Ekta","Kanyakumari","Kavita","Lakshmi","Noora","Parvati","Priyamvada","Seema","Amani","Amiyah","Ananya","Cali","Diya",
    "Esha","Kalee","Kalia","Leena","Lina","Neha","Priya","Shivani","Shreya","Simran","Tanvi","Varsha")
  val InLastNames = Array("Sharma","Varma","Gupta","Malhotra","Bhatnagar","Saxena","Kapur","Singh","Mehra","Chopra","Sarin","Malik","Chatterjee","Sen",
    "Bose","Sengupta","Das","Dasgupta","Banerjee","Dutta","Nayar","Pillai","Rao","Jayaraman","Venkatesan","Krishnan","Subramanium","Rangan","Rangarajan",
    "Singh","Yadav","Jhadav","Jaiteley","Chauhan","Mistry","Khan","Shah","Mehta","Patel","Patil","Pawar","Gavde","Kadam","Tambe","Chavan")

  val AfMFirstNames = Array("Abimbola","Abioye","Adegoke","Afolabi","Amadi","Ayokunle","Azubuike","Babajide","Babatunde","Berko","Bongani","Bosede",
    "Chidi","Chidubem","Chimeka","Chike","Chima","Chiumbo","Dakarai","Ekwuenme","Emeka","Enitan","Faraji","Femi","Fungai","Gwandoya","Imamu","Isingoma",
    "Jalani","Jengo","Kato","Kgosi","Khamisi","Kibwe","Kofi","Kojo","Kwame","Kwasi","Mamadou","Masambe","Mosi","Nkosana","Ochieng","Olabode","Olufemi",
    "Olujimi","Sefu","Simba","Sizwe","Tafari","Thulani","Wekesa","Zuberi","Thabo")
  val AfFFirstNames = Array("Aba","Abebi","Akili","Amadi","Amina","Arziki","Asha","Aziza","Binta","Bolanle","Bonme","Caimile","Cataval","Chika","Chipo",
    "Dayo","Deka","Delu","Denisha","Dore","Faiza","Fayola","Habika","Hadiya","Halima","Hasina","Iman","Iniko","Isoke","Jamila","Kadija","Kali","Kasindra",
    "Kesia","Habika","Lehana","Maizah","Malika","Mandisa","Marjani","Naja","Neema","Oba","Rafiya","Safara","Shasa","Sika","Simbra","Taja","Takiyah","Tamala",
    "Tanginika","Habika","Tayla","Tendai","Waseme","Xhosa","Zahara","Zalika","Zarina","Zisiwe","Xhosa","Zahara","Zalika","Zarina")
  val AfLastNames = Array("Baloyi","Bapela","Bengu","Bikani","Boroto","Buthelezi","Chikunga","Dambuza","Digkale","Dubazana","Farisani","Gamede","Gasebonwe",
    "Godongwana","Gunda","Hangana","Kekana","Khumalo","Kotsi","Legetho","Luthuli","Akinwande","Abanda","Kebbe","Agooda","Diallo","Fofana","Mensah","Mbeki",
    "Okafor","Yeboah","Cissoko","Dioppe","Okeke","Owusu","Ballo","Jalloh","Nwosu","Okoro","Sesay","Kuumba","Mawakizi","Ondede","Mabaso","Mabuza","Madasa",
    "Mafolo","Makhuba","Manana","Masango","Masutha","Mayunde","Mazibuko","Miambo","Moshodi","Mubu","Muthambi","Ngele")

  val MxMFirstNames = Array("Alejandro","Juan Carlos","Miguel","Eduardo","Fernando","Carlos","Rodrigo","Ricardo","Javier","Jose Luis","Carlos","César",
    "Emilio","Enrique","Ernesto","Félix","Gabriel","Héctor","Humberto","Isidoro","Iván","Jesús","Jorge","Jose","Juan","Osvaldo","Raúl","Román","Rubén",
    "Santiago","Sergio","Víctor")
  val MxFFirstNames = Array("Adríana","Ana","Analucía","Beatriz","Dominga","Elsa","Encarnación","Esmerelda","Gabriela","Graciela","Guadalupe","Inéz",
    "Juanita","Katia","Leticia","María","Maricruz","Patricia","Rosa","Rosario","Silvia","Xenia","Yvonne")
  val MxLastNames = Array("Hernández","García","Martinez","López","González","Rodriguez","Pérez","Sanchez","Ramírez","Márquez","Ruiz","Santiago",
    "Rivera","Torres","Guzmán","Pena","Delgado","Valdéz","Vega","Chávez","Moreno","Medina","Soto","Vargas","Díaz","Flores","Garza","Castillo")
  //</editor-fold>

  def Name(country:String,gender:String):String ={
    var first = ""
    var last = ""
    country match {
      case "United States" => {
        if(gender=="Male"){
          first = AmMFirstNames(God.nextInt(AmMFirstNames.length))
        } else {
          first = AmFFirstNames(God.nextInt(AmFFirstNames.length))
        }
        last = AmLastNames(God.nextInt(AmLastNames.length))
      }
      case "Russia" => {
        if(gender=="Male"){
          first = RsMFirstNames(God.nextInt(RsMFirstNames.length))
          last = RsMLastNames(God.nextInt(RsMLastNames.length))
        }else{
          first = RsFFirstNames(God.nextInt(RsFFirstNames.length))
          last = RsFLastNames(God.nextInt(RsFLastNames.length))
        }
      }
      case "China" => {
        if(gender=="Male"){
          first = ChMFirstNames(God.nextInt(ChMFirstNames.length))
        }else{
          first = ChFFirstNames(God.nextInt(ChFFirstNames.length))
        }
        last = ChLastNames(God.nextInt(ChLastNames.length))
      }
      case "India" => {
        if(gender=="Male"){
          first = InMFirstNames(God.nextInt(InMFirstNames.length))
        }else{
          first = InFFirstNames(God.nextInt(InFFirstNames.length))
        }
        last = InLastNames(God.nextInt(InLastNames.length))
      }
      case "South Africa" => {
        if(gender=="Male"){
          first = AfMFirstNames(God.nextInt(AfMFirstNames.length))
        }else{
          first = AfFFirstNames(God.nextInt(AfFFirstNames.length))
        }
        last = AfLastNames(God.nextInt(AfLastNames.length))
      }
      case "Mexico" => {
        if(gender=="Male"){
          first = MxMFirstNames(God.nextInt(MxMFirstNames.length))
        }else{
          first = MxFFirstNames(God.nextInt(MxFFirstNames.length))
        }
        last = MxLastNames(God.nextInt(MxLastNames.length))
      }
    }
    first+" "+last
  }
}

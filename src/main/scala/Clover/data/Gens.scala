package Clover.data
import scala.util.Random
import java.io.{File,BufferedWriter,FileWriter}

object Gens {
  private var idCount = 0
  private var expo = "" // holds the people until we want to export them
  // DEBUG MAIN

  /**Generates a list of customers then stores them to a file
   * @param total The amount of customers to generate * 1,000
   */
  def GenerateCustomers(total:Int): Unit = {
    //SafeGenerate(3)
    //println("---BREAK---")
    var count = 0
    while(count<total){
      count+=1
      CreatePeople(965)
      SafeGenerate(1) // (35 countries * 1 each)
      if(count==1){
        ExportPeople(false)
      }else{
        ExportPeople(true)
      }
    }
    //ExportPeople()
    //PrintPeople()
  }
  //

  /**Exports the customers to a file
   * @param append Whether to append to the file or not
   */
  private def ExportPeople(append:Boolean=false): Unit ={
    // This exports the current list of people to a file.

    val file = new File("src\\main\\scala\\Clover\\data\\people.csv")
    val bw = new BufferedWriter(new FileWriter(file,append))
    if(append) {
      bw.write("\n"+expo)
    }else {
      bw.write("customer_id,customer_name,city,country\n" + expo)
    }
    bw.close()
    expo = ""
  }

  /**Prints the customers to the console
   */
  private def PrintPeople(): Unit ={
    println(expo)
  }

  /**Ensures that each country has at least one customer
   * @param count The amount to generate per country
   */
  private def SafeGenerate(count:Int): Unit ={
    // Method creates a 'safety net' that makes every country have <count> to <count+15> people.
    countries.foreach(e=>{
      CreatePeople(count,e)
    })
  }

  /**Generates a list of customers from a specific country
   * @param count The amount to generate
   * @param country The country they are from
   */
  private def CreatePeople(count:Int,country:String): Unit ={
    var test = 0
    while(test<count){
      test+=1
      idCount+=1
      val gender = sexes(God.nextInt(sexes.length))
      var city = ""
      country match {
        case "United States" => city = AmCities(God.nextInt(AmCities.length))
        case "Russia" => city = RsCities(God.nextInt(RsCities.length))
        case "China" => city = ChCities(God.nextInt(ChCities.length))
        case "India" => city = InCities(God.nextInt(InCities.length))
        case "South Africa" => city = SAfCities(God.nextInt(SAfCities.length))
        case "Mexico" => city = MxCities(God.nextInt(MxCities.length))
        case "Iran" => city = IAbCities(God.nextInt(IAbCities.length))
        case "United Kingdom" => city = EnCities(God.nextInt(EnCities.length))
        case "France" => city = FrCities(God.nextInt(FrCities.length))
        case "Germany" => city = GmCities(God.nextInt(GmCities.length))
        case "Australia" => city = AuCities(God.nextInt(AuCities.length))
        case "Italy" => city = ItCities(God.nextInt(ItCities.length))
        case "Japan" => city = JpCities(God.nextInt(JpCities.length))
        case "Israel" => city = IsCities(God.nextInt(IsCities.length))
        case "Spain" => city = EsCities(God.nextInt(EsCities.length))
        case "Greece" => city = GrCities(God.nextInt(GrCities.length))
        case "Norway" => city = NwCities(God.nextInt(NwCities.length))
        case "Ireland" => city = IrCities(God.nextInt(IrCities.length))
        case "South Korea" => city = SkCities(God.nextInt(SkCities.length))
        case "Netherlands" => city = DuCities(God.nextInt(DuCities.length))
        case "Scotland" => city = ScCities(God.nextInt(ScCities.length))
        case "Belgium" => city = BgCities(God.nextInt(BgCities.length))
        case "Poland" => city = PlCities(God.nextInt(PlCities.length))
        case "Turkey" => city = TkCities(God.nextInt(TkCities.length))
        case "Ukraine" => city = URsCities(God.nextInt(URsCities.length))
        case "Pakistan" => city = PAbCities(God.nextInt(PAbCities.length))
        case "Nigeria" => city = NAfCities(God.nextInt(NAfCities.length))
        case "Canada" => city = CAmCities(God.nextInt(CAmCities.length))
        case "Egypt" => city = EAbCities(God.nextInt(EAbCities.length))
        case "Colombia" => city = CMxCities(God.nextInt(CMxCities.length))
        case "Argentina" => city = AMxCities(God.nextInt(AMxCities.length))
        case "Brazil" => city = BEsCities(God.nextInt(BEsCities.length))
        case "Indonesia" => city = IAuCities(God.nextInt(IAuCities.length))
        case "Portugal" => city = PEsCities(God.nextInt(PEsCities.length))
        case "Sweden" => city = SNwCities(God.nextInt(SNwCities.length))
        case "Venezuela" => city = VMxCities(God.nextInt(VMxCities.length))
        case _ => println("Unknown country.")
      }

      // Instead of print line, either insert into a table or write to a file that is then inserted to the table.
      //println(s"ID:$test | "+Name(country,gender)+s" ($city, $country)")
      // csv instead of the above.
      if(expo=="") {
        expo += s"$idCount,${Name(country, gender)},$city,$country"
      }else{
        expo += s"\n$idCount,${Name(country, gender)},$city,$country"
      }
    }
    // debug print
    //println(s"Created $test people, total of $idCount people.")
  }

  /**Generates a list of customers with a randomly selected country
   * @param count The amount to generate
   */
  private def CreatePeople(count:Int): Unit ={
    var test = 0
    while(test<count){
      test+=1
      idCount+=1
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
        case "Iran" => city = IAbCities(God.nextInt(IAbCities.length))
        case "United Kingdom" => city = EnCities(God.nextInt(EnCities.length))
        case "France" => city = FrCities(God.nextInt(FrCities.length))
        case "Germany" => city = GmCities(God.nextInt(GmCities.length))
        case "Australia" => city = AuCities(God.nextInt(AuCities.length))
        case "Italy" => city = ItCities(God.nextInt(ItCities.length))
        case "Japan" => city = JpCities(God.nextInt(JpCities.length))
        case "Israel" => city = IsCities(God.nextInt(IsCities.length))
        case "Spain" => city = EsCities(God.nextInt(EsCities.length))
        case "Greece" => city = GrCities(God.nextInt(GrCities.length))
        case "Norway" => city = NwCities(God.nextInt(NwCities.length))
        case "Ireland" => city = IrCities(God.nextInt(IrCities.length))
        case "South Korea" => city = SkCities(God.nextInt(SkCities.length))
        case "Netherlands" => city = DuCities(God.nextInt(DuCities.length))
        case "Scotland" => city = ScCities(God.nextInt(ScCities.length))
        case "Belgium" => city = BgCities(God.nextInt(BgCities.length))
        case "Poland" => city = PlCities(God.nextInt(PlCities.length))
        case "Turkey" => city = TkCities(God.nextInt(TkCities.length))
        case "Ukraine" => city = URsCities(God.nextInt(URsCities.length))
        case "Pakistan" => city = PAbCities(God.nextInt(PAbCities.length))
        case "Nigeria" => city = NAfCities(God.nextInt(NAfCities.length))
        case "Canada" => city = CAmCities(God.nextInt(CAmCities.length))
        case "Egypt" => city = EAbCities(God.nextInt(EAbCities.length))
        case "Colombia" => city = CMxCities(God.nextInt(CMxCities.length))
        case "Argentina" => city = AMxCities(God.nextInt(AMxCities.length))
        case "Brazil" => city = BEsCities(God.nextInt(BEsCities.length))
        case "Indonesia" => city = IAuCities(God.nextInt(IAuCities.length))
        case "Portugal" => city = PEsCities(God.nextInt(PEsCities.length))
        case "Sweden" => city = SNwCities(God.nextInt(SNwCities.length))
        case "Venezuela" => city = VMxCities(God.nextInt(VMxCities.length))
      }

      // Instead of print line, either insert into a table or write to a file that is then inserted to the table.
      //println(s"ID:$test | "+Name(country,gender)+s" ($city, $country)")
      // csv instead of the above.
      if(expo=="") {
        expo += s"$idCount,${Name(country, gender)},$city,$country"
      }else{
        expo += s"\n$idCount,${Name(country, gender)},$city,$country"
      }
    }
    // debug print
    //println(s"Created $test people, total of $idCount people.")
  }

  /**Creates the customer's full name based on country and gender
   * @param country The country the customer is from
   * @param gender The customer's gender
   * @return The string of the customer's name
   */
  private def Name(country:String,gender:String):String ={
    var first = ""
    var last = ""
    country match {
      case "United States" | "Canada" => {
        if(gender=="Male"){
          first = AmMFirstNames(God.nextInt(AmMFirstNames.length))
        } else {
          first = AmFFirstNames(God.nextInt(AmFFirstNames.length))
        }
        last = AmLastNames(God.nextInt(AmLastNames.length))
      }
      case "Russia" | "Ukraine" => {
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
      case "South Africa" | "Nigeria" => {
        if(gender=="Male"){
          first = AfMFirstNames(God.nextInt(AfMFirstNames.length))
        }else{
          first = AfFFirstNames(God.nextInt(AfFFirstNames.length))
        }
        last = AfLastNames(God.nextInt(AfLastNames.length))
      }
      case "Mexico" | "Colombia" | "Argentina" | "Venezuela" => {
        if(gender=="Male"){
          first = MxMFirstNames(God.nextInt(MxMFirstNames.length))
        }else{
          first = MxFFirstNames(God.nextInt(MxFFirstNames.length))
        }
        last = MxLastNames(God.nextInt(MxLastNames.length))
      }
      case "Iran" | "Pakistan" | "Egypt" => {
        if(gender=="Male"){
          first = AbMFirstNames(God.nextInt(AbMFirstNames.length))
        }else{
          first = AbFFirstNames(God.nextInt(AbFFirstNames.length))
        }
        last = AbLastNames(God.nextInt(AbLastNames.length))
      }
      case "United Kingdom" => {
        if(gender=="Male"){
          first = EnMFirstNames(God.nextInt(EnMFirstNames.length))
        }else{
          first = EnFFirstNames(God.nextInt(EnFFirstNames.length))
        }
        last = EnLastNames(God.nextInt(EnLastNames.length))
      }
      case "France" => {
        if(gender=="Male"){
          first = FrMFirstNames(God.nextInt(FrMFirstNames.length))
        }else{
          first = FrFFirstNames(God.nextInt(FrFFirstNames.length))
        }
        last = FrLastNames(God.nextInt(FrLastNames.length))
      }
      case "Germany" => {
        if(gender=="Male"){
          first = GmMFirstNames(God.nextInt(GmMFirstNames.length))
        }else{
          first = GmFFirstNames(God.nextInt(GmFFirstNames.length))
        }
        last = GmLastNames(God.nextInt(GmLastNames.length))
      }
      case "Australia" | "Indonesia" => {
        if(gender=="Male"){
          first = AuMFirstNames(God.nextInt(AuMFirstNames.length))
        }else{
          first = AuFFirstNames(God.nextInt(AuFFirstNames.length))
        }
        last = AuLastNames(God.nextInt(AuLastNames.length))
      }
      case "Italy" => {
        if(gender=="Male"){
          first = ItMFirstNames(God.nextInt(ItMFirstNames.length))
        }else{
          first = ItFFirstNames(God.nextInt(ItFFirstNames.length))
        }
        last = ItLastNames(God.nextInt(ItLastNames.length))
      }
      case "Japan" => {
        if(gender=="Male"){
          first = JpMFirstNames(God.nextInt(JpMFirstNames.length))
        }else{
          first = JpFFirstNames(God.nextInt(JpFFirstNames.length))
        }
        last = JpLastNames(God.nextInt(JpLastNames.length))
      }
      case "Israel" => {
        if(gender=="Male"){
          first = IsMFirstNames(God.nextInt(IsMFirstNames.length))
        }else{
          first = IsFFirstNames(God.nextInt(IsFFirstNames.length))
        }
        last = IsLastNames(God.nextInt(IsLastNames.length))
      }
      case "Spain" | "Portugal" | "Brazil" => {
        if(gender=="Male"){
          first = EsMFirstNames(God.nextInt(EsMFirstNames.length))
        }else{
          first = EsFFirstNames(God.nextInt(EsFFirstNames.length))
        }
        last = EsLastNames(God.nextInt(EsLastNames.length))
      }
      case "Greece" => {
        if(gender=="Male"){
          first = GrMFirstNames(God.nextInt(GrMFirstNames.length))
        }else{
          first = GrFFirstNames(God.nextInt(GrFFirstNames.length))
        }
        last = GrLastNames(God.nextInt(GrLastNames.length))
      }
      case "Norway" | "Sweden" => {
        if(gender=="Male"){
          first = NwMFirstNames(God.nextInt(NwMFirstNames.length))
        }else{
          first = NwFFirstNames(God.nextInt(NwFFirstNames.length))
        }
        last = NwLastNames(God.nextInt(NwLastNames.length))
      }
      case "Ireland" => {
        if(gender=="Male"){
          first = IrMFirstNames(God.nextInt(IrMFirstNames.length))
        }else{
          first = IrFFirstNames(God.nextInt(IrFFirstNames.length))
        }
        last = IrLastNames(God.nextInt(IrLastNames.length))
      }
      case "South Korea" => {
        if(gender=="Male"){
          first = SkMFirstNames(God.nextInt(SkMFirstNames.length))
        }else{
          first = SkFFirstNames(God.nextInt(SkFFirstNames.length))
        }
        last = SkLastNames(God.nextInt(SkLastNames.length))
      }
      case "Netherlands" => {
        if(gender=="Male"){
          first = DuMFirstNames(God.nextInt(DuMFirstNames.length))
        }else{
          first = DuFFirstNames(God.nextInt(DuFFirstNames.length))
        }
        last = DuLastNames(God.nextInt(DuLastNames.length))
      }
      case "Scotland" => {
        if(gender=="Male"){
          first = ScMFirstNames(God.nextInt(ScMFirstNames.length))
        }else{
          first = ScFFirstNames(God.nextInt(ScFFirstNames.length))
        }
        last = ScLastNames(God.nextInt(ScLastNames.length))
      }
      case "Belgium" => {
        if(gender=="Male"){
          first = BgMFirstNames(God.nextInt(BgMFirstNames.length))
        }else{
          first = BgFFirstNames(God.nextInt(BgFFirstNames.length))
        }
        last = BgLastNames(God.nextInt(BgLastNames.length))
      }
      case "Poland" => {
        if(gender=="Male"){
          first = PlMFirstNames(God.nextInt(PlMFirstNames.length))
          last = PlMLastNames(God.nextInt(PlMLastNames.length))
        }else{
          first = PlFFirstNames(God.nextInt(PlFFirstNames.length))
          last = PlFLastNames(God.nextInt(PlFLastNames.length))
        }
      }
      case "Turkey" => {
        if(gender=="Male"){
          first = TkMFirstNames(God.nextInt(TkMFirstNames.length))
          last = TkMLastNames(God.nextInt(TkMLastNames.length))
        }else{
          first = TkFFirstNames(God.nextInt(TkFFirstNames.length))
          last = TkFLastNames(God.nextInt(TkFLastNames.length))
        }
      }
    }
    first+" "+last
  }

  // Our faithful creator
  private val God = new Random()

  // used for name generation, irrelevant to rest of data.
  private val sexes = Array("Male","Female")

  /*
    Country List; (As taken from XCOM's localization code, as were the names)
    United States (Am)
    Russia (Rs)
    China (Ch)
    India (In)
    South Africa (Af) (SAf)
    Mexico (Mx)
    Iran (Ab) (IAb)
    United Kingdom (En)
    France (Fr)
    Germany (Gm)
    Australia (Au)
    Italy (It)
    Japan (Jp)
    Israel (Is)
    Spain (Es)
    Greece (Gr)
    Norway (Nw)
    Ireland (Ir)
    South Korea (Sk)
    Netherlands (Du)
    Scotland (Sc)
    Belgium (Bg)
    Poland (Pl)
    Turkey (Tk)
    Ukraine (Rs) (URs)
    Pakistan (Ab) (PAb)
    Nigeria (Af) (NAf)
    Canada (Am) (CAm)
    Egypt (Ab) (EAb)
    Colombia (Mx) (CMx)
    Argentina (Mx) (AMx)
    Brazil (Es) (BEs)
    Indonesia (Au) (IAu)
    Portugal (Es) (PEs)
    Sweden (Nw) (SNw)
    Venezuela (Mx) (VMx)
   */

  // Repeat countries for more weight??
  // If we want any countries to appear more frequently, just put them in the array multiple times.
  private val countries = Array("United States","Russia","China","India","South Africa","Mexico","Iran","United Kingdom",
    "France","Germany","Australia","Italy","Japan","Israel","Spain","Greece","Norway","Ireland","South Korea","Netherlands",
    "Scotland","Belgium","Poland","Turkey","Ukraine","Pakistan","Canada","Egypt","Colombia","Argentina","Brazil","Indonesia",
    "Portugal","Sweden","Venezuela")

  //<editor-fold desc="Cities">

  val AmCities = Array("Miami","New York City","Los Angeles","Chicago")
  val RsCities = Array("Moscow","Saint Petersburg","Samara","Chelyabinsk")
  val ChCities = Array("Beijing","Shanghai","Guangzhou","Chengdu")
  val InCities = Array("Delhi","Mumbai","Chennai","Jaipur")
  val SAfCities = Array("Cape Town","Durban","Pretoria","Polokwane")
  val MxCities = Array("Mexico City","Puebla","Monterrey","Mérida")
  val IAbCities = Array("Kermanshah","Tehran","Tabriz","Mashhad")
  val EnCities = Array("London","Liverpool","Manchester","Burmingham")
  val FrCities = Array("Paris","Nice","Bordeaux","Dijon")
  val GmCities = Array("Berlin","Munich","Hamburg","Cologne")
  val AuCities = Array("Melbourne","Sidney","Canberra","Newcastle")
  val ItCities = Array("Rome","Florence","Bologna","Pisa")
  val JpCities = Array("Tokyo","Kyoto","Osaka","Yokohama")
  val IsCities = Array("Jerusalem","Acre","Haifa","Petah Tikva")
  val EsCities = Array("Madrid","Barcelona","Valencia","Bilbao")
  val GrCities = Array("Athens","Patras","Corinth","Thessaloniki")
  val NwCities = Array("Osio","Bergen","Stavanger","Trondheim")
  val IrCities = Array("Dublin","Galway","Cork","Limerick")
  val SkCities = Array("Seoul","Busan","Incheon","Daegu")
  val DuCities = Array("Amsterdam","Rotterdam","Delft","Utrecht")
  val ScCities = Array("Edenburgh","Dundee","Aberdeen","Glasgow")
  val BgCities = Array("Brussels","Antwerp","Leuven","Namur")
  val PlCities = Array("Warsaw","Kraków","Wrocław","Lublin")
  val TkCities = Array("Istanbul","Izmir","Antalya","Bursa")
  val URsCities = Array("Kyiv","Mariupol","Odesa","Kharkiv")
  val PAbCities = Array("Lahore","Karachi","Faisalabad","Multan")
  val NAfCities = Array("Lagos","Ibadan","Kano","Port Harcourt")
  val CAmCities = Array("Toronto","Quebec City","Calgary","Vancouver")
  val EAbCities = Array("Cairo","Luxor","Giza","Alexandria")
  val CMxCities = Array("Bogotá","Cartagena","Cali","Santa Marta")
  val AMxCities = Array("Buenos Aires","Mendoza","Córdoba","Rosario")
  val BEsCities = Array("Rio de Janeiro","São Paulo","Manaus","Fortaleza")
  val IAuCities = Array("Jakarta","Bandung","Makassar","Semarang")
  val PEsCities = Array("Lisbon","Porto","Braga","Evora")
  val SNwCities = Array("Stockholm","Gothenburg","Uppsala","Helsingborg")
  val VMxCities = Array("Caracas","Isla Ratón","Guayana City","Maracay")
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

  val AbMFirstNames = Array("Mohammed","Ahmed","Ali","Said","Hamza","Ibrahim","Rachid","Mustapha","Mahmoud","Youssef","Abdullah","Tareq","Hassan",
    "Khaled")
  val AbFFirstNames = Array("Aya","Fatima","Raniya","Khadija","Sarah","Malika","Mariam","Hoda","Karima","Saida","Nasreem","Naima","Safiya")
  val AbLastNames = Array("Haddad","Khoury","Hariri","Kharam","Ajram","Suleiman","Rahman","Amin","Asad","Aziz","Farouk","Ghaffar","Ghalib","Hashim",
    "Haidar","Imad","Isra","Jalaal","Jawahir","Majid","Mansoor","Mufaddal","Nabil","Nasim","Nur","Samad","Shazad","Tariq","Wahid","Turk","Canaan")

  val EnMFirstNames = Array("Jack","Oliver","Harry","Thomas","William","James","Charles","Daniel","Richard","Lewis","George","Adam","Ben","Owen","Paul")
  val EnFFirstNames = Array("Emily","Molly","Sophie","Jessica","Amy","Abigail","Abby","Lauren","Holly","Kate","Lucy","Elizabeth","Leah","Emma","Helen")
  val EnLastNames = Array("Smith","Jones","Williams","Brown","Taylor","Davies","Wilson","Evans","Thomas","Johnson","Roberts","Walker","Wright","Robinson",
    "Thompson","White","Hughes","Edwards","Green","Hall","Wood","Harris","Martin","Jackson","Clarke","Turner","Hill")

  val FrMFirstNames = Array("Bruno","Augustin","Bertrand","Charles","Christophe","Claude","David","Édouard","Émile","Étienne","Eugène","François",
    "Frédéric","Gaston","Georges","Gérard","Gilbert","Grégoire","Guillaume","Gustave","Henri","Jacques","Jean","Julien","Laurent","Louis","Luc","Marc",
    "Marcel","Matthieu","Patrice","Philippe","Pierre","Rémy","René","Richard","Roland","Sébastien","Théodore","Thierry","Thomas","Tristan","Victor","Vincent",
    "Xavier","Yves","Zacharie")
  val FrFFirstNames = Array("Adèle","Agathe","Amélie","Anne","Audrey","Astrid","Bernadette","Brigitte","Camille","Cécile","Claire","Colette","Constance",
    "Dominique","Édith","Élisabeth","Emmanuelle","Gabrielle","Hélène","Isabelle","Jacqueline","Joséphine","Josette","Juliette","Madeleine","Marguerite",
    "Mathilda","Monique","Odette","Renée","Sophie","Simone","Sylvie","Thérèse","Véronique","Valérie","Zoé")
  val FrLastNames = Array("Martine","Bernard","Dubois","Thomas","Richard","Petit","Durand","Leroy","Moreau","Simon","Laurent","Lefèvre","Roux","Fournier",
    "Morel","Girard","André","Mercier")

  val GmMFirstNames = Array("Lukas","Markus","Maximilian","Jonas","Martin","Klaus","Konrad","Dieter","Axel","Bernhard","Karl","Stefan","Heinrich","Ernst")
  val GmFFirstNames = Array("Martha","Frida","Else","Emma","Claudia","Christine","Gertrud","Martina","Ursula","Sophie","Lena","Sarah","Jana")
  val GmLastNames = Array("Müller","Schmidt","Schneider","Fischer","Meyer","Weber","Wagner","Becker","Schulz","Hauffman","Ulrich","Haussman","Richter",
    "Schafer","Bauer","Klein","Wolf","Neumann","Schwartz","Lange","Werner","Krause","Kohler","Konig","Braun","Weiss","Hahn","Vogel")

  val AuMFirstNames = Array("Jack","James","Ian","Thomas","Patrick","William","Benjamin","David","John","Matthew","Paul","Keith","Jay","Steve")
  val AuFFirstNames = Array("Emily","Holly","Sarah","Paula","Amy","Lauren","Erin","Zoe","Beth","Mary","Sarah")
  val AuLastNames = Array("Jones","Williams","Brown","Wilson","Taylor","Johnson","White","Martin","Anderson","Thompson","Thomas","Walker","Harris","Lee",
    "Ryan","Robinson","Kelly","King")

  val ItMFirstNames = Array("Fabrizio","Alessandro","Matteo","Lorenzo","Luca","Paolo","Riccardo","Fabio","Silvio","Andrea","Angelo","Antonio","Marco",
    "Maurizio")
  val ItFFirstNames = Array("Sofia","Alessia","Francesca","Donatella","Roberta","Nicoletta","Angela","Arianna","Alessandra","Isabella","Valentina",
    "Viviana","Elisabetta")
  val ItLastNames = Array("Rossi","Russo","Ferrari","Bianchi","Romano","Ricci","Marino","Greco","Bruno","Gallo","Conti","De Luca","Costa","Giordano",
    "Mancini","Rizzo","Lombardi","Moretti","Simoni","Bettini","Petacchi","Basso","Cancellara","Gasparatto","Sella","Lazzaro","Roberti","Moletta")

  val JpMFirstNames = Array("Daichi","Daisuke","Hiroki","Hiroshi","Kaito","Katsume","Kenji","Kenzo","Makoto","Masahiro","Naoki","Nobu","Riku",
    "Ryo","Shigeru","Shin","Tadashi","Takumi","Yoshio","Yutaka","Hideo")
  val JpFFirstNames = Array("Aiko","Akemi","Ayaka","Chiyoko","Fumiko","Harumi","Hitomi","Kaori","Kasumi","Kazuko","Kazumi","Kiriko","Kumiko",
    "Midori","Misaki","Miu","Natsuki","Rin","Sakura","Yoko","Yukiko","Yumi","Yuzuki")
  val JpLastNames = Array("Sato","Suzuki","Takahashi","Tanaka","Watanabe","Ito","Yamamoto","Nakamura","Kobayashi","Saito","Kato","Yoshida",
    "Yamada","Sasaki","Yamaguchi","Matsumoto","Kimura","Hayashi","Shimizu","Yamazaki","Mori","Ikeda","Hashimoto","Yamashita","Ishikawa",
    "Nakajima","Ogawa","Fujita","Okada","Goto","Hasegawa","Murakami","Sakamoto","Endo","Aoki","Fujii","Nishimura","Fujiwara","Okamoto","Mutsuda",
    "Nakagawa","Nakano","Kojima","Miyamoto")

  val IsMFirstNames = Array("Abraham","Adam","Ari","Asher","Avi","Daniel","David","Eli","Ephraim","Gideon","Hayim","Isaac","Levi","Jacob",
    "Malachi","Mikhael","Mordecai","Moshe","Noam","Ravid","Ronen","Malachi","Mikhael","Mordecai","Moshe","Noam","Ravid","Ronen","Saul","Sol",
    "Yuri","Joshua","Yaron")
  val IsFFirstNames = Array("Adina","Aliza","Ariel","Ayala","Dalia","Dara","Devorah","Eliana","Galia","Hadar","Hadassah","Hannah","Ava","Judith",
    "Kayla","Liora","Marni","Miriam","Naomi","Nava","Nissa","Rachel","Rina","Rebecca","Sarah","Shahar","Shayna","Shiri","Shoshana","Tova")
  val IsLastNames = Array("Cohen","Levi","Mizrachi","Peretz","Ben-David","Bar-Lev","Biton","Daham","Rosenberg","Friedman","Azulai","Eliad",
    "Malcah","Katz","David","Gabai","Amar","Hadad","Yosef","Levin","Moshe","Rabin","Segel","Chazan","Shapira","Meir","Klein","Golan")

  val EsMFirstNames = Array("Adolfo","Bernardo","Carlos","Manolo","Eduardo","Esteban","Felipe","Fernando","Hugo","Javier","Jorge","Juan","Marcos","Miguel",
    "Pablo","Pedro","Ramón","Raúl","Ricardo","Roberto","Javier","Vicente","Victor")
  val EsFFirstNames = Array("Alicia","Ana","Andrea","Carlota","Catalina","Cristina","Daniela","Esperanza","Estela","Eva","Carol","Gabriela","Inés","Isabel",
    "Lucía","Margarita","Julia","María","Raquel","Rosana","Silvia","Teresa","Yolanda")
  val EsLastNames = Array("García","Fernández","González","Rodriguez","López","Martínez","Sánchez","Pérez","Gómez","Ruiz","Hernández","Jiménez","Díaz",
    "Álvarez","Moreno","Muñoz","Alonso","Gutiérrez","Romero","Navarro","Torres","Domínguez","Vázquez","Serrano","Ramos","Blanco","Castro","Suárez")

  val GrMFirstNames = Array("Giorgios","Konstantinos","Dimitrios","Nikolaos","Christos","Evangelos","Alexandros","Giannis","Hector","Piero",
    "Jasen","Ajax","Cyrano","Cyril","Petros","Rasmus","Sabastian","Anatoli","Andreas","Dennis","Dion","Thanos","Theo","Minos","Artemas","Aristo",
    "Eugenios","Atlas","Fedor","Tymon","Yuri","Gregor","Pavlos","Zoltan","Zorba")
  val GrFFirstNames = Array("Maria","Eleni","Katerina","Georgia","Sofia","Anna","Angeliki","Dimitra","Konstantina","Alexandra","Ambrosia",
    "Anastasia","Adrienne","Kassia","Petrine","Philippa","Ariadne","Elissa","Melaina","Theodora","Sibyl","Vesna","Varella","Callia","Isadora",
    "Ophelia","Zena","Zoe")
  val GrLastNames = Array("Manolas","Nicolo","Petridis","Papadakis","Baros","Antinos","Boulos","Cosmos","Demopolous","Galanos","Katsaros","Korba",
    "Kosta","Kakos","Lekas","Manikas","Metaxas","Mikos","Panagakos","Petras","Romanos","Speros","Thanos","Zervas","Xenakis")

  val NwMFirstNames = Array("Anders","Arne","Bjarne","Bjorn","Erik","Finn","Fredrik","Gunnar","Hans","Harald","Thor","Ivar","Jan","Jarle",
    "Jens","Johan","Jorgen","Karl","Lars","Leif","Magnus","Olav","Rolf","Rune","Sigurd","Stig","Svein")
  val NwFFirstNames = Array("Anette","Astrid","Brit","Elin","Else","Hanna","Gretta","Heidi","Hilde","Ingrid","Kari","Karin","Kirsten",
    "Lillian","Lisbeth","Marte","Malin","Mona","Sigrid","Therese")
  val NwLastNames = Array("Hansen","Johansen","Olsen","Larsen","Andersen","Pedersen","Nilsen","Jensen","Karlsen","Johnson","Eriksen",
    "Berg","Haugen","Hagen","Johannessen","Jacobsen","Halvorsen")

  val IrMFirstNames = Array("Jack","Sean","Seamus","Daniel","James","Conor","Ryan","Dylan","Adam","Liam","Patrick","Gerry","Colin",
    "Brendan","Shane","Aidan","Casey","Brennan","Caden","Kieran","Killian","Declan","Kellen")
  val IrFFirstNames = Array("Aileen","Caitlin","Ciara","Erin","Clare","Molly","Mary","Kate","Bridget","Kathleen","Tara","Shawna",
    "Fiona","Maura","Tracy","Dara","Emma","Lucy","Grace")
  val IrLastNames = Array("Murphy","Kelly","O'Sullivan","Walsh","O'Brien","Ryan","O'Connor","O'Neill","O'Reilly","Doyle","McCarthy",
    "Gallagher","O'Doherty","Kennedy","Lynch","Murray","Quinn","Moore","Harrington","McLoughlin","O'Carroll","Connolly","Daly",
    "O'Connell","Keenan","Duff","Kerrigan")

  val SkMFirstNames = Array("Minjoon","Ji Hoon","Minjae","Woojin","Jin","Hyun","Minsu","Sung Min","Min Ho","Hwang","Mal Chin",
    "Man Shik","Moon","Kang Dae","Ryung","Sam","Seung","Won Shik","Yon","Yong Sun")
  val SkFFirstNames = Array("Ji Woo","Ji Min","Ji Yung","Ji Hye","So Ji","Min Ju","Hye Jin","Hyun Jung","Min Ji","Joo","Soo",
    "Soon Bok","Sun Hi","Su Jin","Mi Cha","Mi Hi")
  val SkLastNames = Array("Kim","Lee","Park","Choi","Jeong","Cho","Yoon","Jang","Han","Shin","Kwan","Song","Hong","Yang","Liu","Chun","Chang")

  val DuMFirstNames = Array("Jacob","Johannes","Dirk","Janus","Anton","Gerard","Jan","Hans","Pieter","Willem","Lars","Hendrik")
  val DuFFirstNames = Array("Adriana","Anna","Corrie","Elisabeth","Rika","Johanna","Margreet","Maria","Mina","Emma","Iris","Eva","Lisbet")
  val DuLastNames = Array("Jansen","VanDyke","Visser","Mulder","DeGroot","Peters","Hendriks","Dekker","DeWitt","Smits","De Graf","Vandermeer",
    "Meyer","Bakker","Boor","Meer","Vos","Bos","Buskirk","De Jong")

  val ScMFirstNames = Array("Alan","Hugh","Angus","Aidan","Brendan","Colin","Cameron","Kieron","Dermott","Duncan","Fergus","Ewan","Archie",
    "Gordon","Neil","Oscar","Rory","Charlie","Scottie","Ronan","Robert")
  val ScFFirstNames = Array("Bridget","Lexi","Dolly","Anna","Sarah","Rose","Clara","Charlotte","Alice","Emily","Bessie","Kathleen","Chrissie",
    "Doreen","Fiona","Isabel","Lily","Maggie","Mary","Penny","Joan")
  val ScLastNames = Array("Robertson","Campbell","Stewart","MacDonald","Scott","Murray","Clark","Gray","Burns","Murphy","McKenzie","McIntosh",
    "McGregor","Douglas","Ferguson","McKay","Hunter","Donaldson","McIntyre","MacLeod","Wallace","Gibson","Ross","Hill","Sutherland","Morrison")

  val BgMFirstNames = Array("Lars","Lucas","Noah","Nathan","Arthur","Mathis","Maxime","Jens","Jonas","Jarne","Niels","Adrien")
  val BgFFirstNames = Array("Lina","Clara","Emma","Léa","Marie","Inès","Ella","Camille","Louise","Julie","Amber","Élise","Chloé")
  val BgLastNames = Array("Peeters","Wouters","Jacobs","Willems","Goosens","Mertens","Aerts","Lambert","Dupont","Van Damme","Lemmens","Dumont",
    "Leroy","Verhoeven","Renard","Timmermans","Gerard","Vandenberg","Fontaine","Beckers")

  val PlMFirstNames = Array("Jakub","Szymon","Mateusz","Piotr","Adam","Jan","Igor","Mikołaj","Dominik","Krzysztof","Marcel","Tomasz",
    "Staniłsaw","Rafał","Maciej")
  val PlFFirstNames = Array("Julia","Maja","Edyta","Lena","Nikola","Maria","Milena","Dominika","Daria","Blanka","Martyna","Zuzanna",
    "Amelia","Magdalena","Paulina")
  val PlMLastNames = Array("Kowalski","Nowak","Woźniak","Szymański","Kozłowski","Kaczmarek","Zając","Jabłoński","Dudek","Malinowski",
    "Pawlak","Jaworski","Wróbel","Kowalczyk","Zieliński","Wiśniewski","Mazur","Piotrowski","Kwiatkowski","Grabowski")
  val PlFLastNames = Array("Kowalska","Nowak","Woźniak","Szymańska","Kozłowska","Kaczmarek","Zając","Jabłońska","Dudek","Malinowska",
    "Pawlak","Jaworska","Wróbel","Kowalczyk","Zielińska","Wiśniewski","Mazur","Piotrowski","Kwiatkowski","Grabowski")

  val TkMFirstNames = Array("Yusuf","Berat","Mustafa","Emir","Ahmet","Omer","Mehmet","Muhammed","Emirhan","Eymen","Ali","Huseyin",
    "Hasan","Ibrahim","Ismail","Osman","Behram","Sabri","Sanem","Yasin")
  val TkMLastNames = Array("Yilmaz","Kaya","Demir","Sahin","Adanir","Adem","Adin","Adivar","Aga","Dagdelen","Dagtekin","Agaogla",
    "Bilgili","Bilgin","Agca","Agcay","Ahmad","Altan","Çelik","Yildiz","Yildirim","Öztürk","Aydin","Özdemir","Asker","Aslan","Gul",
    "Altin","Batuk","Basturk","Birkan","Kaplan","Polat","Arat","Altun")
  val TkFFirstNames = Array("Pelin","pek","Nazli","Talya","Yagmur","Derin","Begum","Nilsu","Kayra","Begum","Ilkin","Tuana","Beril",
    "Iglin","Simay","Beren","Mira","Yazmira","Selin","Rana")
  val TkFLastNames = Array("Yilmaz","Kaya","Demir","Sahin","Adanir","Adem","Adin","Adivar","Aga","Dagdelen","Dagtekin","Agaogla",
    "Bilgili","Bilgin","Agca","Agcay","Ahmad","Altan","Çelik","Yildiz","Yildirim","Öztürk","Aydin","Özdemir","Asker","Aslan","Gul",
    "Altin","Batuk","Basturk","Birkan","Kaplan","Polat","Arat","Altun")
  //</editor-fold>

}

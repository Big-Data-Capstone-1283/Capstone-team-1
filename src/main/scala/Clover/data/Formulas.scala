package Clover.data
import scala.util.Random
import scala.math.BigDecimal
import money._
object Formulas {

  //private val InflationRate: Array[Double] = new Array[Double](22)
  private val inflationOccured: Array[Double] = new Array[Double](22)

  def SetInflation(): Unit={
    val rand = new Random()
    var low:Double = 0
    var high:Double = 2.5
    //Key     0       1       2       3       4
    //Values  1.00    1.02    1.05    1.09    1.16
    for(w<- inflationOccured.indices){
      if(w == 0){
        inflationOccured(w) = 1
      }
      else
      {
        if(rand.nextInt(3) >2){
          low = 2.6
          high = 5
        }
        else
        {
          low = 0
          high = 2.5
        }
        inflationOccured(w) = inflationOccured(w - 1)*(1 + rand.between(low,high)/100)
    //        InflationRate(x) = BigDecimal(InflationRate(x-1) + rand.between(low,high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }

    }
    /*for(x<- InflationRate.indices) {
      if(rand.nextInt(3) >2){
        low = 2.6
        high = 5 //Is this setting it universally or locally? Like, it hits it once, and 'high' is 5 forever.
      }
      if(x != 0){
        InflationRate(x) = BigDecimal(InflationRate(x-1) + rand.between(low,high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }
      else {
        InflationRate(x) = BigDecimal(rand.between(low, high)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }
    }*/
    inflationOccured.foreach(println)
  }
  def GetInflation(): Array[Double]={
    //InflationRate
    inflationOccured
  }
  def ApplyInflation(price: Double,year: Int): Double={
    //listedprice*inflationOccured(year-2000) inflationO
    //(price*InflationRate(year-2000))
    BigDecimal(price*inflationOccured(year-2000)).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble //Should be functional. SHOULD
    //price * inflationrate/100
    //price + ^
  }
  def DateRange(): Unit={

  }

  def Convert(): Unit={
    val conversion: Conversion = Map(
    (USD, EUR) -> 0.93, //euro: Spain, Slovenia, Slovakia, Portugal, Belgium, France, Germany, Greece, Ireland, Italy, Netherlands, Portugal, Spain
    (USD, GBP) -> 0.77, // Pound Sterling - Scotland, United Kingdom
    (USD, AFN) -> 87.5, // Afghani
    (USD, JPY) -> 128.73, //yen - Japan
    (USD, XPF) -> 110.94, // CFP Franc - French Polynesia
    (USD, ALL) -> 114.55, // Albanian Lek
    (USD, AOA) -> 415.47, // Kwanza
    (USD, ARS) -> 113.79, // Argentine Peso - Argentina
    (USD, AMD) -> 470.83, // Armenian Dram
    (USD, AWG) -> 1.80, // Aruban Florin
    (USD, AUD) -> 1.36, // Australian Dollar - Australia
    (USD, AZN) -> 1.7, // Azerbaijanian Manat
    (USD, BSD) -> 1, // Bahamian Dollar
    (USD, BHD) -> 0.38, // Bahraini Dinar
    (USD, BBD) -> 2.02, // Barbados Dollar
    (USD, BDT) -> 86.26, // Bangladeshi Taka
    (USD, BZD) -> 2.02, // Belize Dollar
    (USD, BMD) -> 1, // Bermudan Dollar
    (USD, INR) -> 76.43, // Indian Rupee - India
    (USD, BOB) -> 6.87, // Bolivian Boliviano
    (USD, BAM) -> 1.81, //Convertible Mark
    (USD, BWP) -> 11.65, // Pula
    (USD, BRL) -> 4.68, // Brazilian Real - Brazil
    (USD, BND) -> 1.37, // Brunei Dollar
    (USD, BGN) -> 1.81, // Bulgarian Lev
    (USD, BIF) -> 2012, // Burundi Franc
    (USD, XPF) -> 111.1, // CFP Franc
    (USD, KHR) -> 4047.5, // Riel
    (USD, CAD) -> 1.26, // Canadian Dollar - Canada
    (USD, CVE) -> 102.4, // Cabo Verde Escudo
    (USD, KYD) -> 0.83, // Cayman Islands Dollar - Cayman Island
    (USD, CLP) -> 819.49, // Chilean Peso - Chile
    (USD, CNY) -> 6.39, // Yuan Renminbi - China
    (USD, COP) -> 3761.2, //Colombian Peso - Columbia
    (USD, KMF) -> 456.38, // Comoro Franc - Comoros
    (USD, CDF) -> 2012, // Congolese Franc - Congo
    (USD, CRC) -> 652.04, // Costa Rican Colón - Costa Rica
    (USD, HRK) -> 7.01, // Kuna - Croatia
    (USD, CUP) ->24.01, // Cuban Peso - Cuba
    (USD, CZK) -> 22.64, // Czech Koruna -Czech Republic
    (USD, DKK) -> 6.89, // Danish Krone - Denmark
    (USD, DJF) -> 178.1, //Djibouti Franc - Djibouti
    (USD, DOP) -> 55.05, //Dominican Peso- Dominica
    (USD, XCD) -> 2.7, // East Caribbean Dollar - (Anguilla, Antigua, Barbuda, Dominica, Grenada, Monsterrat, Saint Kitts, Nevis, Saint Lucia, Saint Vincent and The Grenadines)
    (USD, EGP) -> 18.47, // Egyptian Pound - Egypt
    (USD, ETB) -> 51.25, // Ethiopian Birr - Ethiopia
    (USD, FJD) -> 2.12, // Fijian Dollar - Fiji
    (USD, GMD) -> 53.93, //Dalasi - Gambia
    (USD, GEL) -> 3.05, // Georgian Lari - Georgia
    (USD, GHS) -> 7.52, // Ghanaian Cedi - Ghana
    (USD, GTQ) -> 7.66, //Quetzal - Guatemala
    (USD, IDR) -> 14364, // Indonesian Rupiah - Indonesia
    (USD, IRR) ->42300, // Iranian Rial - Iran
    (USD, ILS) -> 3.24, //  Israeli New Shekel - Israel
      //jaceguai
    (USD, MOP) -> 8.08, // Macanese Pataca - Macao
    (USD, MKD) -> 57.11, // Macedonian Denar - Macedonia
    (USD, MGA) -> 3999.00, // Malagasy Ariary - Madagascar
    (USD, MWK) -> 811.50, // Malwian Kwacha - Malawi
    (USD, MYR) -> 4.25, // Malaysian Ringgit - Malaysia
    (USD, MVR) -> 15.45, // Maldivian Rufiyaa - Maldives
    (USD, MUR) -> 43.05, // Mauritian Rupee - Mauritius
    (USD, MXN) -> 20.05, // Mexican Peso - Mexico
    (USD, MDL) -> 18.46, // Moldovan Leu - Moldovia
    (USD, MAD) -> 9.85, // Moroccan Dirham -WESTERN SAHARA
    (USD, MZN) -> 63.83, // Mozambican metical - MOZAMBIQUE
    (USD, MMK) -> 1852.31, // Myanmar Kyat - MYANMAR
    (USD, NAD) -> 14.94, // Namibian dollar -NAMIBIA
    (USD, NPR) -> 122.11, // Nepalese Rupee - NEPAL
    (USD, ANG) -> 1.80, // Netherlands Antillean Guilder - CURACAO
    (USD, TWD) -> 29.30, // New Taiwan dollar - TAIWAN
    (USD, NZD) -> 1.49, // New Zealand Dollar - COOK ISLAND
    (USD, NIO) -> 35.71, //Nicaraguan Cordoba Oro - Nicar
    (USD, NGN) -> 414.5, // Naira - Nigeria
    (USD, NOK) -> 8.84, // Norwegian Krone- Norway
    (USD, KPW) -> 899.976, //North korea won
    (USD, OMR) -> 0.38, //oman rial
    (USD, PKR) -> 8.82, //Pakistan Rupee - Pakistan
    (USD, PAB) -> 1.00, //panama balboa
    (USD, PGK) -> 3.50, //papua new guinea kina
    (USD, PYG) -> 6853.53, // paraguay guarani
    (USD, PEN) -> 3.74, // Peru Nuevo Sol
    (USD, PHP) -> 52.53, // Philippines Peso
    (USD, PLN) -> 4.30, // Polish Zloty - Poland
    (USD, QAR) -> 3.64, // Qatar Rial
    (USD, RON) -> 4.58, // Romania New Lei
    (USD, RUB) -> 80.75, // Russia Ruble - Russia
    (USD, RWF) -> 1020.00, // Rwanda Franc
    (USD, WST) -> 2.59851, // samoa tala
    (USD, STD) -> 21443.24, //Sao Tome/Principe Dobra
    (USD, SAR) -> 3.75, //Saudi Arabia Riyal
    (USD, RSD) -> 109.05, //Serbia Dinar
    (USD, SCR) -> 14.42, //Seychelles Rupee
    (USD, SLL) -> 12365.00, //Sierra Leone Leone
    (USD, SGD) -> 1.37, //Singapore Dollar
    (USD, SBD) -> 7.99, //Soloman Islands Dollar
    (USD, SOS) -> 579.50, //Somali Shilling
    (USD, ZAR) -> 14.94, //South Africa Rand - South Africa
    (USD, KRW) -> 1240.68, //South Korea Won - South Korea
    (USD, LKR) -> 330.13, //Sri Lanka Rupee
    (USD, SHP) -> 0.768991, //St Helena Pound
    (USD, SDG) -> 446.50, //Sudan Pound
    (USD, SRD) -> 20.71, //suriname dollar
    (USD, SZL) -> 14.72, //swaziland Lilangeni
    (USD, SEK) -> 9.57, //Sweden Krona - Sweden
    (USD, CHF) -> 0.95, //switzerland franc
    (USD, SYP) -> 2512.03, //syria pound
    (USD, TWD) -> 29.30, //Taiwan Dollar
    (USD, TZS) -> 2322.00, //tanzania shilling
    (USD, THB) -> 33.80, //thailand baht
    (USD, TOP) -> 2.28, //Tonga Pa'anga
    (USD, TTD) -> 6.79, //Trinidad Dollar
    (USD, TND) -> 3.00, //Tunisia Dinar
    (USD, TRY) -> 14.66, //Turkish New Lira - Turkey
    (USD, UGX) -> 3521.54, //uganda shilling
    (USD, UAH) -> 29.54, //Ukraine Hryvnia - Ukraine
    (USD, UYU) -> 41.28, //Uruguay Peso
    (USD, AED) -> 3.67, //united arab emirates dirham
    (USD, VUV) -> 111.5, //Vanuatu Vatu
    (USD, VEF) -> 443153000 , //venezuela bolivar
    (USD, VND) -> 29957.50, //Vietnam dong
    (USD, YER) -> 250.25, //yemen rial
    (USD, ZWD) -> 361.9, //zimbabwe dollar
  )
  implicit val converter = Converter(conversion)
  val USDtoEuro = 100.50 (USD) to EUR
  println(USDtoEuro)
  }

}

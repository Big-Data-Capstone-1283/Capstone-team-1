package Clover.data
import java.sql.Timestamp
/**Class for a complete row for the dataset*/
case class CRow(order_id:Int,customer_id: Int,customer_name: String,product_id:Int,product_name: String,
           product_category: String,payment_type: String,qty: Int,price: Double,datetime: Timestamp,
           country: String,city: String,ecommerce_website_name: String,payment_txn_id: Int,
           payment_txn_success: String,failure_reason: String) extends Serializable{
  def this(){
    this(0,0," ",0," "," "," ",0,0,new Timestamp(50.toLong)," "," "," ",0," "," ")
  }
  def this(customer:Customer,company:Company,product:Product,transaction:Transaction,formula:Formulas){
    this(transaction.order_id,customer.customer_id,customer.customer_name,product.product_id,product.product_name,product.product_category,transaction.payment_type
    ,transaction.qty,formula.Convert(customer.country,product.price,true),transaction.datetime,customer.country,customer.city,company.name,
      transaction.payment_txn_id,transaction.payment_txn_success,transaction.failure_reason)
  }

}

/**Class for individual customers*/
case class Customer(var customer_id:Int,var customer_name:String,var city:String,var country:String){
  def this(){
    this(0," "," "," ")
  }
  def this(split:Array[String]){
    //var random_customer = ""
    this(split(0).toInt,split(1),split(2),split(3))
  }
}
/**Class for individual companies*/
case class Company(name:String,notSellCat: String,notSellCountries: String,prefCountries: String,
                   prefCountriesPer: Double,priceOffset: Double,salesRate: Int)
/**Class for individual products*/
case class Product(var product_id: Int,var product_category: String,var product_name: String,var price: Double){
  def this(){
    this(0," "," ",0)
  }
  def this(split:Array[String],priceOffset:Double){
    this(split(0).toInt,split(1),split(2),split(3).toDouble*priceOffset)
  }
}
/**Class for individual transactions*/
case class Transaction(var payment_type: String,var payment_txn_id: Int,var payment_txn_success: String
                       ,var failure_reason: String,qty: Int,order_id:Int,datetime:Timestamp){
  def this(){
    this(" ",0," "," ",0,0,new Timestamp(0L))
  }
}

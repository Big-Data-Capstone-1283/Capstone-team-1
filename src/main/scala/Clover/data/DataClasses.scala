package Clover.data
import java.sql.Timestamp
/**Class for a complete row for the dataset*/
case class Row(order_id:Int,customer_id: Int,customer_name: String,product_id:Int,product_name: String,
           product_category: String,payment_type: String,qty: Int,price: Double,datetime: Timestamp,
           country: String,city: String,ecommerce_website_name: String,payment_txn_id: Int,
           payment_txn_success: String,failure_reason: String) extends Serializable{
  def this(){
    this(0,0," ",0," "," "," ",0,0,new Timestamp(50.toLong)," "," "," ",0," "," ")
  }
  def this(customer:Customer,company:Company,product:Product,transaction:Transaction){
    this(transaction.order_id,customer.id,customer.name,product.id,product.name,product.category,transaction.payment_type
    ,transaction.qty,product.value,transaction.datetime,customer.country,customer.city,company.name,
      transaction.payment_txn_id,transaction.payment_txn_success,transaction.failure_reason)
  }

}

/*val row = Row(order_id, customer.id, customer.name, product.id, product.name, product.category, transaction.payment_type
, prod_qty, formulas.Convert(customer.country, product.value), new Timestamp(date), customer.country, customer.city
, company.name, transaction.payment_txn_id, transaction.payment_txn_success, transaction.failure_reason)*/

/**Class for individual customers*/
case class Customer(var id:Int,var name:String,var city:String,var country:String){
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
case class Product(var id: Int,var category: String,var name: String,var value: Double){
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

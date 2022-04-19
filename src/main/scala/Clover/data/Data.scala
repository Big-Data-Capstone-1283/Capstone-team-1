package Clover.data
import java.sql.Timestamp
case class Data(var order_id:Int,var customer_id: Int,var customer_name: String,var product_id:Int,var product_name: String,
           var product_category: String,var payment_type: String,var qty: Int,var price: Int,var datetime: Timestamp,
           var country: String,var city: String,var ecommerce_website_name: String,var payment_txn_id: Int,
           var payment_txn_success: String,var failure_reason: String) extends Serializable{

  def this(){
    this(0,0,"",0,"","","",0,0,new Timestamp(50.toLong),"","","",0,"","")
  }

}

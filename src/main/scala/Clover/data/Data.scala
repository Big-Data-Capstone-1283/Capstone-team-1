package Clover.data
import java.sql.Timestamp
case class Row(order_id:Int,customer_id: Int,customer_name: String,product_id:Int,product_name: String,
           product_category: String,payment_type: String,qty: Int,price: Double,datetime: Timestamp,
           country: String,city: String,ecommerce_website_name: String,payment_txn_id: Int,
           payment_txn_success: String,failure_reason: String) extends Serializable{



  def this(){
    this(0,0,"",0,"","","",0,0,new Timestamp(50.toLong),"","","",0,"","")
  }

}
import org.apache.kafka.common.serialization.{IntegerSerializer,StringSerializer,DoubleSerializer,LongSerializer}
class RowSerializer extends org.apache.kafka.common.serialization.Serializer[Row]{
  val integerSerializer = new IntegerSerializer
  val doubleSerializer = new DoubleSerializer
  val stringSerializer = new StringSerializer
  val longSerializer = new LongSerializer
  override def serialize(topic: String, row: Row): Array[Byte] ={
    val oid = integerSerializer.serialize(topic,row.order_id)
    val cid = integerSerializer.serialize(topic,row.customer_id)
    val cnLength = integerSerializer.serialize(topic,row.customer_name.length)
    val cn = stringSerializer.serialize(topic,row.customer_name)
    val pid = integerSerializer.serialize(topic,row.product_id)
    val pnLength = integerSerializer.serialize(topic,row.product_name.length)
    val pn = stringSerializer.serialize(topic,row.product_name)
    val pcLength = integerSerializer.serialize(topic,row.product_category.length)
    val pc = stringSerializer.serialize(topic,row.product_category)
    val ptLength = integerSerializer.serialize(topic,row.payment_type.length)
    val pt = stringSerializer.serialize(topic,row.payment_type)
    val qt = integerSerializer.serialize(topic,row.qty)
    val p = doubleSerializer.serialize(topic,row.price)
    val d = longSerializer.serialize(topic,row.datetime.getTime)
    val cLength = integerSerializer.serialize(topic,row.country.length)
    val c = stringSerializer.serialize(topic,row.country)
    val ciLength = integerSerializer.serialize(topic,row.city.length)
    val ci = stringSerializer.serialize(topic,row.city)
    val ecLength = integerSerializer.serialize(topic,row.ecommerce_website_name.length)
    val ec = stringSerializer.serialize(topic,row.ecommerce_website_name)
    val ti = integerSerializer.serialize(topic,row.payment_txn_id)
    val ts = stringSerializer.serialize(topic,row.payment_txn_success)
    val frLength = integerSerializer.serialize(topic,row.failure_reason.length)
    val fr = stringSerializer.serialize(topic,row.failure_reason)
    Array(oid,cid,cnLength,cn,pid,pnLength,pn,pcLength,pc,ptLength,pt,qt,p,d,cLength,c,ciLength,ci,ecLength,ec,ti,ts,frLength,fr).flatten
  }
}
import org.apache.kafka.common.serialization.{IntegerDeserializer,StringDeserializer,DoubleDeserializer,LongDeserializer}
class RowDeserializer extends org.apache.kafka.common.serialization.Deserializer[Row]{
  val integerDeserializer = new IntegerDeserializer
  val stringDeserializer = new StringDeserializer
  val doubleDeserializer = new DoubleDeserializer
  val longDeserializer = new LongDeserializer
  override def deserialize(topic: String, data: Array[Byte]): Row = {
    val i = (arr: Array[Byte])=> integerDeserializer.deserialize(topic,arr)
    val s = (arr: Array[Byte])=> stringDeserializer.deserialize(topic,arr)
    val d = (arr: Array[Byte])=> doubleDeserializer.deserialize(topic,arr)
    val l = (arr: Array[Byte])=> longDeserializer.deserialize(topic,arr)
    var index = 0
    val get = (start:Int,end:Int) =>{ index = end;(for(x<-start until end) yield data(x)).toArray[Byte]}
    val oid = get(index,index+4)
    val cid = get(index,index+4)
    val cn = get(index,i(get(index,index+4)))
    val pid = get(index,index+4)
    val pn = get(index,i(get(index,index+4)))
    val pc = get(index,i(get(index,index+4)))
    val pt = get(index,i(get(index,index+4)))
    val qt = get(index,index+4)
    val p = 

    new Row()
  }
}

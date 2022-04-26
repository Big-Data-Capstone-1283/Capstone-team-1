package Clover.data

import Clover.Tools.SweepstakesGen
import scala.util.Random

object TransactionsGen extends App  {
  case class Transactions(payment_type: String, payment_txn_id: Int, payment_txn_success: String, failure_reason: String)
  CreateTransaction(1)

  def CreateTransaction(txn_id: Int) : Unit = {

    //<editor-fold desc="Fail Reasons">

    val CreditCardFailReasons = Array("Card Information Incorrect", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Card Expired", "Credit Line Limit Reached")
    val DebitCardFailReasons = Array("Card Information Incorrect", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Card Expired", "Insufficient Funds")
    val CheckFailReasons = Array("Fraud", "Connection Interrupted",
      "Server Maintenance", "Invalid Check Number", "Bank Account Suspended")
    val PayPalFailReasons = Array("PayPal Service Down", "Fraud", "Connection Interrupted",
      "Server Maintenance", "Incorrect Credentials", "Out of Funds")

    //</editor-fold>

    val paymentTypes = Array("Credit Card", "Debit Card", "Check", "Paypal")

    val sweepstakesGen = new SweepstakesGen()

    val payType = paymentTypes(Random.nextInt(paymentTypes.length))
    val isTxnSuccess = sweepstakesGen.shuffle(.95)
    var TxnSuccess = ""
    var TxnFailedReason = ""

    isTxnSuccess match {
      case true => TxnSuccess = "Y"; TxnFailedReason = "None"
      case false =>
        TxnSuccess = "N"
        payType match {
            case "Credit Card" => TxnFailedReason = CreditCardFailReasons(Random.nextInt(CreditCardFailReasons.length))
            case "Debit Card" => TxnFailedReason = DebitCardFailReasons(Random.nextInt(DebitCardFailReasons.length))
            case "Check" => TxnFailedReason = CheckFailReasons(Random.nextInt(CheckFailReasons.length))
            case "Paypal" => TxnFailedReason = PayPalFailReasons(Random.nextInt(PayPalFailReasons.length))
        }
    }

    val txn_final = Transactions(payType, txn_id, TxnSuccess, TxnFailedReason)
    println(txn_final)
  }

}



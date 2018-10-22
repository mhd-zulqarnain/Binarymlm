package com.redcodetechnologies.mlm.models

data class Report(
    var UserName:String?=null,
    var PaymentMethod:String?=null,
    var AccountNumber:String?=null,
    var BankName:String?=null,
    var NetAmountPayble:String?=null,
    var WithdrawalCharges:String?=null,
    var ApprovedRequestDate:String?=null,
    var PaidDate:String?=null

)

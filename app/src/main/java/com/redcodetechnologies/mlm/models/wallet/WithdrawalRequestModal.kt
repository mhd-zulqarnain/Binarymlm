package com.redcodetechnologies.mlm.models.wallet

data class WithdrawalRequestModal (var WithdrawalFundMethod: String? = null,
                                   var AmountPayble: String? = null,
                                   var WithdrawalFundCharge: String? = null,
                                   var RequestedDate:String? = null,
                                   var Username:String? = null,
                                   var wd_requested_date:String? = null
                                   )
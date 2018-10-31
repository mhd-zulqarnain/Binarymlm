package com.redcodetechnologies.mlm.models

data class WithdrawalRequestModal (var wd_username: String? = null,
                                   var wd_paymen_method: String? = null,
                                   var wd_payable:String? = null,
                                   var wd_charges:String? = null,
                                   var wd_requested_date:String? = null
                                   )
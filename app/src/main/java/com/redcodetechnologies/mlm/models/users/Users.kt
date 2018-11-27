package com.redcodetechnologies.mlm.models.users

data class Users(var UserId: String? = null,
                 var Username: String? = null,
                 var Country: String? = null,
                 var Phone: String? = null,
                 var AccountNumber: String? = null,
                 var BankName: String? = null,
                 var SponsorId: String? = null,
                 var PaidAmount: String? = null,
                 var SponsorName: String? = null
)

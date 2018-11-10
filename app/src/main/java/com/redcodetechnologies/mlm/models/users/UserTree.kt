package com.redcodetechnologies.mlm.models.users


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserTree (
        var UserId: String? = null,
        var Name: String? = null,
        var UserName: String? = null,
        var Password: String? = null,
        var Country: Int? = null,
        var CountryName: String? = null,
        var Address: String? = null,
        var Phone: String? = null,
        var Email: String? = null,
        var AccountNumber: String? = null,
        var BankName: String? = null,
        var CNICNumber: String? = null,
        var IsThisFirstUser: String? = null,
        var SponsorId: Int? = null,
        var SponsorName: String? = null,
        var DownlineMemberId: Int? = null,
        var UpperId: Int? = null,
        var PaidAmount: String? = null,
        var CreateDate: String? = null,
        var LastLoginDate: String? = null,
        var UserCode: String? = null,
        var IsUserActive: String? = null,
        var IsNewRequest: String? = null,
        var UserPosition: String? = null,
        var IsEmailConfirmed: String? = null,
        var UserPackage: String? = null,
        var UserPackageName: String? = null,
        var UserPackagePrice: String? = null,
        var DocumentImage: String? = null,
        var IsSleepingPartner: Boolean? = null,
        var IsSalesExecutive: Boolean? = null,
        var UserDesignation: String? = null,
        var IsWithdrawalOpen: Boolean? = null
)



package com.redcodetechnologies.mlm.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewUserRegistration {
    @SerializedName("UserId")
    @Expose
    var userId: Int? = null
    @SerializedName("Name")
    @Expose
    var name: String? = null
    @SerializedName("Username")
    @Expose
    var username: String? = null
    @SerializedName("Password")
    @Expose
    var password: String? = null
    @SerializedName("Country")
    @Expose
    var country: Int? = null
    @SerializedName("Address")
    @Expose
    var address: Any? = null
    @SerializedName("Phone")
    @Expose
    var phone: Any? = null
    @SerializedName("Email")
    @Expose
    var email: String? = null
    @SerializedName("CNIC")
    @Expose
    var cnic: Any? = null
    @SerializedName("AccountNumber")
    @Expose
    var accountNumber: Any? = null
    @SerializedName("BankName")
    @Expose
    var bankName: Any? = null
    @SerializedName("IsThisFirstUser")
    @Expose
    var isThisFirstUser: Any? = null
    @SerializedName("SponsorId")
    @Expose
    var sponsorId: Any? = null
    @SerializedName("DownlineMemberId")
    @Expose
    var downlineMemberId: Any? = null
    @SerializedName("UpperId")
    @Expose
    var upperId: Any? = null
    @SerializedName("PaidAmount")
    @Expose
    var paidAmount: Any? = null
    @SerializedName("CreateDate")
    @Expose
    var createDate: Any? = null
    @SerializedName("UserCode")
    @Expose
    var userCode: Any? = null
    @SerializedName("IsUserActive")
    @Expose
    var isUserActive: Any? = null
    @SerializedName("IsNewRequest")
    @Expose
    var isNewRequest: Any? = null
    @SerializedName("UserPosition")
    @Expose
    var userPosition: Any? = null
    @SerializedName("IsEmailConfirmed")
    @Expose
    var isEmailConfirmed: Any? = null
    @SerializedName("UserPackage")
    @Expose
    var userPackage: Any? = null
    @SerializedName("DocumentImage")
    @Expose
    var documentImage: Any? = null
    @SerializedName("IsSleepingPartner")
    @Expose
    var isSleepingPartner: Any? = null
    @SerializedName("IsSalesExecutive")
    @Expose
    var isSalesExecutive: Any? = null
    @SerializedName("UserDesignation")
    @Expose
    var userDesignation: Any? = null

    override fun toString(): String {
        var username = ""
        if (userDesignation != null) {
            username = userDesignation.toString()!!
        }
        return username
    }

}
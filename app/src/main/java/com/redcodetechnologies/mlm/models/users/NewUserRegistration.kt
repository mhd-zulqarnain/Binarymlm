package com.redcodetechnologies.mlm.models.users


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
    var address: String? = null
    @SerializedName("Phone")
    @Expose
    var phone: String? = null
    @SerializedName("Email")
    @Expose
    var email: String? = null
    @SerializedName("CNIC")
    @Expose
    var cnic: String? = null
    @SerializedName("AccountNumber")
    @Expose
    var accountNumber: String? = null
    @SerializedName("BankName")
    @Expose
    var bankName: String? = null
    @SerializedName("IsThisFirstUser")
    @Expose
    var isThisFirstUser: Boolean? = null
    @SerializedName("SponsorId")
    @Expose
    var sponsorId: Int? = null
    @SerializedName("DownlineMemberId")
    @Expose
    var downlineMemberId: Int? = null
    @SerializedName("UpperId")
    @Expose
    var upperId: Int? = null
    @SerializedName("PaidAmount")
    @Expose
    var paidAmount: Int? = null
    @SerializedName("CreateDate")
    @Expose
    var createDate: String? = null
    @SerializedName("UserCode")
    @Expose
    var userCode: String? = null
    @SerializedName("IsUserActive")
    @Expose
    var isUserActive: Boolean? = null
    @SerializedName("IsNewRequest")
    @Expose
    var isNewRequest: Boolean? = null
    @SerializedName("UserPosition")
    @Expose
    var userPosition: String? = null
    @SerializedName("IsEmailConfirmed")
    @Expose
    var isEmailConfirmed: Boolean? = null
    @SerializedName("UserPackage")
    @Expose
    var userPackage: Int? = null
    @SerializedName("DocumentImage")
    @Expose
    var documentImage: Any? = null
    @SerializedName("IsSleepingPartner")
    @Expose
    var isSleepingPartner: Boolean? = null
    @SerializedName("IsSalesExecutive")
    @Expose
    var isSalesExecutive: Boolean? = null
    @SerializedName("UserDesignation")
    @Expose
    var userDesignation: String? = null
    @SerializedName("IsWithdrawalOpen")
    @Expose
    var isWithdrawalOpen: Boolean? = null
    @SerializedName("NICImage")
    @Expose
    var nicImage: Any? = null
    @SerializedName("NICImage1")
    @Expose
    var nicImage1: Any? = null
    @SerializedName("ProfileImage")
    @Expose
    var profileImage: Any? = null
    @SerializedName("AccountTitle")
    @Expose
    var accountTitle: Any? = null
    @SerializedName("IsVerify")
    @Expose
    var isVerify: Boolean? = null
    @SerializedName("IsBlock")
    @Expose
    var isBlock: Boolean? = null
    @SerializedName("Fcm")
    @Expose
    var fcm: String? = null

    override fun toString(): String {
        var username = ""
        if (userDesignation != null) {
            username = userDesignation.toString()!!
        }
        return username
    }

}



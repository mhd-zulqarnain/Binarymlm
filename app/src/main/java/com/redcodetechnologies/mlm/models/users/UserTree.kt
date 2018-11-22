package com.redcodetechnologies.mlm.models.users


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserTree(){
    @SerializedName("UserId")
    @Expose
    var userId: Int? = null
    @SerializedName("Name")
    @Expose
    var name: Any? = null
    @SerializedName("Username")
    @Expose
    var username: String? = null
    @SerializedName("Password")
    @Expose
    var password: Any? = null
    @SerializedName("Country")
    @Expose
    var country: Int? = null
    @SerializedName("CountryName")
    @Expose
    var countryName: Any? = null
    @SerializedName("Address")
    @Expose
    var address: Any? = null
    @SerializedName("Phone")
    @Expose
    var phone: String? = null
    @SerializedName("Email")
    @Expose
    var email: Any? = null
    @SerializedName("AccountNumber")
    @Expose
    var accountNumber: String? = null
    @SerializedName("BankName")
    @Expose
    var bankName: String? = null
    @SerializedName("CNICNumber")
    @Expose
    var cnicNumber: Any? = null
    @SerializedName("IsThisFirstUser")
    @Expose
    var isThisFirstUser: Boolean? = null
    @SerializedName("SponsorId")
    @Expose
    var sponsorId: Int? = null
    @SerializedName("SponsorName")
    @Expose
    var sponsorName: String? = null
    @SerializedName("DownlineMemberId")
    @Expose
    var downlineMemberId: Any? = null
    @SerializedName("UpperId")
    @Expose
    var upperId: Int? = null
    @SerializedName("PaidAmount")
    @Expose
    var paidAmount: Int? = null
    @SerializedName("CreateDate")
    @Expose
    var createDate: String? = null
    @SerializedName("LastLoginDate")
    @Expose
    var lastLoginDate: String? = null
    @SerializedName("UserCode")
    @Expose
    var userCode: Any? = null
    @SerializedName("IsUserActive")
    @Expose
    var isUserActive: Boolean? = null
    @SerializedName("IsNewRequest")
    @Expose
    var isNewRequest: Boolean? = null
    @SerializedName("UserPosition")
    @Expose
    var userPosition: Any? = null
    @SerializedName("IsEmailConfirmed")
    @Expose
    var isEmailConfirmed: Boolean? = null
    @SerializedName("UserPackage")
    @Expose
    var userPackage: Int? = null
    @SerializedName("UserPackageName")
    @Expose
    var userPackageName: Any? = null
    @SerializedName("UserPackagePrice")
    @Expose
    var userPackagePrice: Int? = null
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
    var userDesignation: Any? = null
    @SerializedName("IsWithdrawalOpen")
    @Expose
    var isWithdrawalOpen: Any? = null
    @SerializedName("EWalletBalance")
    @Expose
    var eWalletBalance: Int? = null
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
    @SerializedName("IsPaidMember")
    @Expose
    var isPaidMember: Any? = null
}

package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.models.*
import com.redcodetechnologies.mlm.models.profile.FcmModel
import com.redcodetechnologies.mlm.models.profile.ProfileSetup
import com.redcodetechnologies.mlm.models.users.DropDownMembers
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.models.users.UserTree
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.models.wallet.WalletSummery
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface RetrofiltService {

    //<editor-fold desc="Authoriztion">
    @FormUrlEncoded
    @POST("token")
    fun verifyEmail(@Field("grant_type") grant_type: String, @Field("username") email: String,
                    @Field("password") password: String): Call<ApiToken>

    @GET("getuser/{username}")
    @Headers("Content-Type:application/json")
    fun getNewRegistoredUser(@Header("Authorization") auth: String, @Path("username") username: String): Call<NewUserRegistration>

    /*forget password*/
    @POST("forgetpassword/{email}/")
    @Headers("Content-Type:application/json")
    fun forgetPassword(@Path("email") email: String): Call<Response>
    //</editor-fold>

    //<editor-fold desc="Dashboard">
    @GET("dashboard/{userid}")
    @Headers("Content-Type:application/json")
    fun getdashboardData(@Header("Authorization") auth: String, @Path("userid") userid: Int): Call<DasboardData>

    @GET("getAds")
    @Headers("Content-Type:application/json")
    fun getCoinData(): Observable<ArrayList<Advertisement>>
    //</editor-fold>

    @GET("getuserunpaidmembersleftlist/{userId}")
    @Headers("Content-Type:application/json")
    fun getUserUnpaidMembersLeft(@Path("userId")  userid: Int): Observable<ArrayList<UserTree>>

    //<editor-fold desc="Make table ">
    @GET("getAllDownlineMembersRight/{userid}")
    @Headers("Content-Type:application/json")
    fun getAllDownlineMembersRight(@Header("Authorization") auth: String, @Path("userid") userid: Int): Observable<ArrayList<Users>>

    @GET("getAllDownlineMembersLeft/{userid}")
    @Headers("Content-Type:application/json")
    fun getAllDownlineMembersLeft(@Header("Authorization") auth: String, @Path("userid") userid: Int): Observable<ArrayList<Users>>

    @GET("dropdownright/{userid}")
    @Headers("Content-Type:application/json")
    fun getdropDownMembersRight(@Header("Authorization") auth: String, @Path("userid") userid: Int): Call<ArrayList<DropDownMembers>>

    @GET("dropdownleft/{userid}")
    @Headers("Content-Type:application/json")
    fun getdropDownMembersLeft(@Header("Authorization") auth: String, @Path("userid") userid: Int): Call<ArrayList<DropDownMembers>>

    @GET("setting/packages")
    @Headers("Content-Type:application/json")
    fun getpackages(): Call<ArrayList<Packages>>

    @GET("maketablemembersright/{userid}")
    @Headers("Content-Type:application/json")
    fun getMakeTableRight(@Header("Authorization") auth: String, @Path("userid") userid: Int): Observable<ArrayList<Users>>

    @GET("maketablemembersleft/{userid}")
    @Headers("Content-Type:application/json")
    fun getMakeTableLeft(@Header("Authorization") auth: String, @Path("userid") userid: Int): Observable<ArrayList<Users>>

    @GET("maketabledetails/{userid}")
    @Headers("Content-Type:application/json")
    fun getdownlineData(@Header("Authorization") auth: String, @Path("userid") userid: Int): Call<MakeTableData>


    @GET("getuserdownlinmembers/{userid}")
    @Headers("Content-Type:application/json")
    fun getMaketableData(@Header("Authorization") auth: String, @Path("userid") userid: Int): Call<MakeTableData>


    //</editor-fold>

    @GET("GetUserReferedMembers/{userid}")
    @Headers("Content-Type:application/json")
    fun getUserReferedMembers(@Header("Authorization") auth: String, @Path("userid") userid: Int): Call<ArrayList<Users>>


    //<editor-fold desc="Adding memeber in tree">
    @POST("addleftmembers/{userid}")
    @Headers("Content-Type:application/json")
    fun addLeftMember(@Header("Authorization") auth: String, @Path("userid") userid: Int, @Body obj: UserTree): Call<Response>

    @POST("addrightmembers/{userid}")
    @Headers("Content-Type:application/json")
    fun addRightMember(@Header("Authorization") auth: String, @Path("userid") userid: Int, @Body obj: UserTree): Call<Response>
    //</editor-fold>

    //<editor-fold desc="Transaction">
    @GET("wallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getOverAllTransation(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>

    @GET("wallet/thismonth/{userId}")
    @Headers("Content-Type:application/json")
    fun getMonthlyTransation(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    // <editor-fold desc="E wallet Credit">
    @GET("ewalletcredit/overall/{userId}")
    @Headers("Content-Type:application/json")
    fun getOverAllEWalletCredit(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>

    @GET("ewalletcredit/thismonth/{userId}")
    @Headers("Content-Type:application/json")
    fun getMonthlyEWalletCredit(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    // <editor-fold desc="E wallet Debit">
    @GET("ewalletdebit/overall/{userId}")
    @Headers("Content-Type:application/json")
    fun getOverAllEWalletDebit(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>

    @GET("ewalletdebit/thismonth/{userId}")
    @Headers("Content-Type:application/json")
    fun getMonthlyEWalletDebit(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    //<editor-fold desc="summery wallet">
    @GET("ewalletsummary/summary/{userId}/")
    @Headers("Content-Type:application/json")
    fun getSummery(@Path("userId") userId: String): Call<WalletSummery>

    @GET("ewalletsummary/summarymonthly/{userId}/")
    @Headers("Content-Type:application/json")
    fun getSummerythisYear(@Path("userId") userId: String): Call<WalletSummery>

    @GET("ewalletsummary/summaryyearly/{userId}/")
    @Headers("Content-Type:application/json")
    fun getSummerythismonth(@Path("userId") userId: String): Call<WalletSummery>
    //</editor-fold>

    //<editor-fold desc="Wallet request">
    @GET("GetEWalletPendingWithdrawalRequests/{userId}")
    @Headers("Content-Type:application/json")
    fun getPendingWdRequest(@Path("userId") userId: Int): Observable<ArrayList<WithdrawalRequestModal>>

    @GET("GetEWalletApprovedRequestPendingPayment/{userId}")
    @Headers("Content-Type:application/json")
    fun getApprovedPendingWdRequest(@Path("userId") userId: Int): Observable<ArrayList<WithdrawalRequestModal>>

    @GET("GetEWalletApprovedRequestPaidPayment/{userId}")
    @Headers("Content-Type:application/json")
    fun getApprovedPaid(@Path("userId") userId: Int): Observable<ArrayList<WithdrawalRequestModal>>

    @GET("GetEWalletRejectedWithdrawalRequests/{userId}")
    @Headers("Content-Type:application/json")
    fun getRejectedRequest(@Path("userId") userId: Int): Observable<ArrayList<WithdrawalRequestModal>>
    //</editor-fold>

    //<editor-fold desc="Profile update">
    @POST("addprofilesetup/{userId}")
    @Headers("Content-Type:application/json")
    fun updateProfile(@Header("Authorization") auth: String, @Path("userId") userId: Int, @Body obj: ProfileSetup): Call<Response>

    @POST("newusername/{username}/{userId}")
    @Headers("Content-Type:application/json")
    fun updateUserName(@Header("Authorization") auth: String, @Path("username") username: String, @Path("userId") userId: Int): Call<Response>
    //</editor-fold>

    //<editor-fold desc="Geneology">
    @GET("GetUserCommission/{userId}")
    @Headers("Content-Type:application/json")
    fun getMyPackageComission(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>

    @GET("GetUserDirectCommission/{userId}")
    @Headers("Content-Type:application/json")
    fun getMyDirectCommsionList(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>

    @GET("GetUserMatchingCommission/{userId}")
    @Headers("Content-Type:application/json")
    fun getMyTableCommsionList(@Path("userId") userId: Int): Observable<ArrayList<TransactionModal>>

    //</editor-fold>

    //<editor-fold desc="Category option">
    @POST("approvesaleexecutive/{userId}")
    @Headers("Content-Type:application/json")
    fun approvesaleexecutive(@Header("Authorization") auth: String, @Path("userId") userId: Int): Call<Response>

    @POST("approvesleepingpartner/{userId}")
    @Headers("Content-Type:application/json")
    fun approvesleepingpartner(@Header("Authorization") auth: String, @Path("userId") userId: Int): Call<Response>
    //</editor-fold>

    //<editor-fold desc="status of members">
    @GET("getuserPaidmembersrightlist/{userId}")
    @Headers("Content-Type:application/json")
    fun getuserPaidmembersrightlist(@Path("userId") userId: Int): Observable<ArrayList<Users>>


    @GET("getuserpaidmembersleftlist/{userId}")
    @Headers("Content-Type:application/json")
    fun getuserpaidmembersleftlist(@Path("userId") userId: Int): Observable<ArrayList<Users>>


    @GET("getuserunpaidmembersleftlist/{userId}")
    @Headers("Content-Type:application/json")
    fun getuserunpaidmembersleftlist(@Path("userId") userId: Int): Observable<ArrayList<Users>>

    @GET("getuserunpaidmembersrightlist/{userId}")
    @Headers("Content-Type:application/json")
    fun getuserunpaidmembersrightlist(@Path("userId") userId: Int): Observable<ArrayList<Users>>
    //</editor-fold>

    //<editor-fold desc="Notification ">
    @POST("notificationsetup/{userid}")
    @Headers("Content-Type:application/json")
    fun saveNotification( @Path("userid") userid: Int,@Body obj: MyNotification): Call<Response>

    @GET("getnotificationlistbyuser/{userId}")
    @Headers("Content-Type:application/json")
    fun getNotificationList(@Path("userId") userId: Int): Observable<ArrayList<MyNotification>>
    //</editor-fold>



    //<editor-fold desc="sponser messages">
    @POST("deleteinboxmsg/{userid}")
    @Headers("Content-Type:application/json")
    fun deleteSponserinboxmsg( @Path("userid") userid: Int): Call<Response>

    @POST("inboxsponsersupport")
    @Headers("Content-Type:application/json")
    fun newMessageSponser( @Body obj:Messages): Call<Response>

    @POST("replymessagesponsorsupport/{u_id}/{msg}/{userId}/{username}")
    @Headers("Content-Type:application/json")
    fun replymessagesponsor( @Path("u_id") sponsorId: Int, @Path("msg") msg: String,
                             @Path("userId") userid: Int,
                             @Path("username") username: String): Call<Response>

    @GET("viewallreadmessage/{userId}")
    @Headers("Content-Type:application/json")
    fun viewallmessagesupport(@Path("userId") userId: Int): Observable<ArrayList<Messages>>

    @GET("getsentmessagessponsorsupport/{userId}")
    @Headers("Content-Type:application/json")
    fun getsentmessagessponsorsupport(@Path("userId") userId: Int): Observable<ArrayList<Messages>>
    //</editor-fold>

    @GET("viewallreadmessageitsupport/{userId}")
    @Headers("Content-Type:application/json")
    fun viewallmessageItsupport(@Path("userId") userId: Int): Observable<ArrayList<Messages>>

    @GET("getsentmessagesitsupport/{userId}")
    @Headers("Content-Type:application/json")
    fun getsentmessagesitsupport(@Path("userId") userId: Int): Observable<ArrayList<Messages>>

    @POST("inboxitsupport")
    @Headers("Content-Type:application/json")
    fun newMessageItSupport( @Body obj:Messages): Call<Response>

    @POST("updateFcm")
    @Headers("Content-Type:application/json")
    fun updateUserFcm(@Body obj: FcmModel): Call<Response>

}

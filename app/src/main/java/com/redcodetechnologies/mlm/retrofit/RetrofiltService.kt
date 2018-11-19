package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.models.*
import com.redcodetechnologies.mlm.models.users.DropDownMembers
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.models.users.UserTree
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface RetrofiltService {

    //<editor-fold desc="Authoriztion">
    @FormUrlEncoded
    @POST("token")
    fun verifyEmail(@Field("grant_type")grant_type: String,@Field("username")email: String,
                    @Field("password")password: String):  Call<ApiToken>

    @GET("getuser/{username}")
    @Headers("Content-Type:application/json")
    fun getNewRegistoredUser(@Header("Authorization")auth:String,@Path("username") username: String): Call<NewUserRegistration>

    /*forget password*/
    @GET("forgetpassword/{email}/")
    @Headers("Content-Type:application/json")
    fun forgetPassword(@Path("email") email: String): Call<Response>
    //</editor-fold>

    //<editor-fold desc="Dashboard">
    @GET("dashboard/{userid}")
    @Headers("Content-Type:application/json")
    fun getdashboardData(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<DasboardData>
    @GET("getAds")
    @Headers("Content-Type:application/json")
    fun getCoinData(): Observable<ArrayList<Advertisement>>
    //</editor-fold>

    //<editor-fold desc="Make table ">
    @GET("getAllDownlineMembersRight/{userid}")
    @Headers("Content-Type:application/json")
    fun getAllDownlineMembersRight(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<Users>>

    @GET("getAllDownlineMembersLeft/{userid}")
    @Headers("Content-Type:application/json")
    fun getAllDownlineMembersLeft(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<Users>>

    @GET("dropdownright/{userid}")
    @Headers("Content-Type:application/json")
    fun getdropDownMembersRight(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<DropDownMembers>>

    @GET("dropdownleft/{userid}")
    @Headers("Content-Type:application/json")
    fun getdropDownMembersLeft(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<DropDownMembers>>

    @GET("setting/packages")
    @Headers("Content-Type:application/json")
    fun getpackages():  Call<ArrayList<Packages>>

    @GET("maketablemembersright/{userid}")
    @Headers("Content-Type:application/json")
    fun getMakeTableRight(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<Users>>

    @GET("maketablemembersleft/{userid}")
    @Headers("Content-Type:application/json")
    fun getMakeTableLeft(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<Users>>

    @GET("maketabledetails/{userid}")
    @Headers("Content-Type:application/json")
    fun getMaketableData(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<MakeTableData>




    //</editor-fold>

    //<editor-fold desc="Adding memeber in tree">
    @POST("maketabledetails/{userid}")
    @Headers("Content-Type:application/json")
    fun addLeftMember(@Header("Authorization")auth:String,@Path("userid") userid: Int,@Body obj: UserTree):  Call<Response>
    @POST("maketabledetails/{userid}")
    @Headers("Content-Type:application/json")
    fun addRightMember(@Header("Authorization")auth:String,@Path("userid") userid: Int,@Body  obj: UserTree):  Call<Response>
    //</editor-fold>

    //<editor-fold desc="Transaction">
    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getOverAllTransation(): Observable<ArrayList<TransactionModal>>

    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getMonthlyTransation(): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    // <editor-fold desc="E wallet Credit">
    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getOverAllEWalletCredit(): Observable<ArrayList<TransactionModal>>

    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getMonthlyEWalletCredit(): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    // <editor-fold desc="E wallet Debit">
    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getOverAllEWalletDebit(): Observable<ArrayList<TransactionModal>>

    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getMonthlyEWalletDebit(): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    //<editor-fold desc="Geneology">
    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getMyPackageComission(): Observable<ArrayList<TransactionModal>>

    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getMyDirectCommsionList(): Observable<ArrayList<TransactionModal>>

    @GET ("gwallet/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getMyTableCommsionList(): Observable<ArrayList<TransactionModal>>
    //</editor-fold>

    @GET ("gpenwdreq/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getPendingWdRequest(): Observable<ArrayList<WithdrawalRequestModal>>

    @GET ("gapppenwdreq/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getApprovedPendingWdRequest(): Observable<ArrayList<WithdrawalRequestModal>>

    @GET ("getapppaid/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getApprovedPaid(): Observable<ArrayList<WithdrawalRequestModal>>

    @GET ("rejreq/overalllist/{userId}")
    @Headers("Content-Type:application/json")
    fun getRejectedRequest(): Observable<ArrayList<WithdrawalRequestModal>>



}

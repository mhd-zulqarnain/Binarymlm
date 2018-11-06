package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.models.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import java.lang.reflect.Array

interface RetrofiltService {

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

    @GET("maketabledetails/{userid}")
    @Headers("Content-Type:application/json")
    fun getMaketableData(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<MakeTableData>

    @GET("getAllDownlineMembersRight/{userid}")
    @Headers("Content-Type:application/json")
    fun getAllDownlineMembersRight(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<Users>>

    @GET("getAllDownlineMembersLeft/{userid}")
    @Headers("Content-Type:application/json")
    fun getAllDownlineMembersLeft(@Header("Authorization")auth:String,@Path("userid") userid: Int):  Call<ArrayList<Users>>

    @GET("getAds")
    @Headers("Content-Type:application/json")
    fun getAdvertisment():  Call<ArrayList<Advertisement>>

    @GET("getAds")
    @Headers("Content-Type:application/json")
    fun getCoinData(): Observable<ArrayList<Advertisement>>

}

//"irelease/{ord_id}/{utfee}/{utamount}/{uobitamount}/{uoamount}/{ut_id}"
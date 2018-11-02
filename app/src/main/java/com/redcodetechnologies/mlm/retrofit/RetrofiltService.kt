package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.models.ApiToken
import com.redcodetechnologies.mlm.models.MakeTableData
import com.redcodetechnologies.mlm.models.NewUserRegistration
import com.redcodetechnologies.mlm.models.Response
import retrofit2.Call
import retrofit2.http.*

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

}

//"irelease/{ord_id}/{utfee}/{utamount}/{uobitamount}/{uoamount}/{ut_id}"
package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.models.ApiToken
import com.redcodetechnologies.mlm.models.NewUserRegistration
import com.redcodetechnologies.mlm.models.Response
import retrofit2.Call
import retrofit2.http.*

interface RetrofiltService {

    @FormUrlEncoded
    @POST("token")
    fun verifyEmail(@Field("grant_type")grant_type: String,@Field("username")email: String,
                    @Field("password")password: String):  Call<ApiToken>

    @GET("account/getuser/{userid}")
    @Headers("Content-Type:application/json")
    fun getUser(@Header("Authorization")auth:String,@Path("userid") userid: String):  Call<NewUserRegistration>

    @GET("getuser/{username}")
    @Headers("Content-Type:application/json")
    fun getNewRegistoredUser(@Header("Authorization")auth:String,@Path("username") username: String): Call<NewUserRegistration>

    /*forget password*/
    @GET("forgetpassword/{email}/")
    @Headers("Content-Type:application/json")
    fun forgetPassword(@Path("email") email: String): Call<Response>

}

//"irelease/{ord_id}/{utfee}/{utamount}/{uobitamount}/{uoamount}/{ut_id}"
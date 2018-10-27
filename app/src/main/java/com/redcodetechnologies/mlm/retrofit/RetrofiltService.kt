package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.models.ApiToken
import com.redcodetechnologies.mlm.models.NewUserRegistration
import retrofit2.Call
import retrofit2.http.*

interface RetrofiltService {

    @FormUrlEncoded
    @POST("/mlmapi/token")
    fun verifyEmail(@Field("grant_type")grant_type: String,@Field("username")email: String,
                    @Field("password")password: String):  Call<ApiToken>


    @GET("/mlmapi/api/account/getuser/{userid}")
    @Headers("Content-Type:application/json")
    fun getUser(@Header("Authorization")auth:String,@Path("userid") userid: String):  Call<NewUserRegistration>

}

//"irelease/{ord_id}/{utfee}/{utamount}/{uobitamount}/{uoamount}/{ut_id}"
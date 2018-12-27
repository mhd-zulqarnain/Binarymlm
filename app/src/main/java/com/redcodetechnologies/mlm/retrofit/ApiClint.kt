package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.xml.datatype.DatatypeConstants.SECONDS
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class ApiClint private constructor() {
     var client = OkHttpClient.Builder()
             .connectTimeout(100, TimeUnit.SECONDS)
             .readTimeout(100, TimeUnit.SECONDS).build()
     private var mRetrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client)
            .build()

    private var rService :RetrofiltService?  =null

    companion object {
        var apiClint: ApiClint? = null

        fun getInstance(): ApiClint? {
            if (apiClint == null)
                return ApiClint()
            else
                return apiClint

        }
    }

    init {
        rService = mRetrofit.create(RetrofiltService::class.java)
    }

    fun getService():RetrofiltService?{
        return  rService
    }


}

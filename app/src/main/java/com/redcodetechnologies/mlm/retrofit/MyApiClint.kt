package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 class MyApiClint private constructor() {

     private var mRetrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var rService :RetrofiltService?  =null

    companion object {
        var apiClint: MyApiClint? = null

        fun getInstance(): MyApiClint? {
            if (apiClint == null)
                return MyApiClint()
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
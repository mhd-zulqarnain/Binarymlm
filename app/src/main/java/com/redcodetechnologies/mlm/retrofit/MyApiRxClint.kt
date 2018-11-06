package com.redcodetechnologies.mlm.retrofit

import com.redcodetechnologies.mlm.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

 class MyApiRxClint private constructor() {

     private var mRetrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private var rService :RetrofiltService?  =null

    companion object {
        var apiClint: MyApiRxClint? = null

        fun getInstance(): MyApiRxClint? {
            if (apiClint == null)
                return MyApiRxClint()
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
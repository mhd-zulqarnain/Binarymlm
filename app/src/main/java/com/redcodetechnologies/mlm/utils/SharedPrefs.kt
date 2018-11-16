package com.redcodetechnologies.mlm.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.redcodetechnologies.mlm.models.ApiToken
import com.redcodetechnologies.mlm.models.users.NewUserRegistration

private const val USER_TOKEN = "usertoken"
private const val USER_REGISTRATION = "newUserRegistration"

class SharedPrefs private constructor(){
    var mPrefs:SharedPreferences?= null

    companion object {
        private var sharedPrefs:SharedPrefs?=null
        fun getInstance():SharedPrefs?{
            if (sharedPrefs==null)
                sharedPrefs = SharedPrefs();
            return  sharedPrefs
        }
    }

    fun setToken(context: Context,apiToken: ApiToken){
        mPrefs = context.getSharedPreferences(USER_TOKEN,Context.MODE_PRIVATE)
        val editor =mPrefs!!.edit();
        var obj = Gson().toJson(apiToken)
        editor.putString("usertoken",obj)
        editor.apply()
    }
    fun getToken(context: Context):ApiToken{
        mPrefs = context.getSharedPreferences(USER_TOKEN,Context.MODE_PRIVATE)
        val objString= mPrefs!!.getString("usertoken",null)
        if (objString == null)
            return ApiToken()
        var obj = Gson().fromJson<ApiToken>(objString,ApiToken::class.java)
        return  obj
    }
    fun clearToken(context: Context){
        mPrefs = context.getSharedPreferences(USER_TOKEN,Context.MODE_PRIVATE)
        var editor = mPrefs!!.edit()
        editor.clear()
        editor.apply()
    }

    fun setUser(context: Context,newUserRegistration: NewUserRegistration){
        mPrefs = context.getSharedPreferences(USER_REGISTRATION,Context.MODE_PRIVATE)
        val editor =mPrefs!!.edit();
        var obj = Gson().toJson(newUserRegistration)
        editor.putString(USER_REGISTRATION,obj)
        editor.apply()
    }
    fun getUser(context: Context): NewUserRegistration {
        mPrefs = context.getSharedPreferences(USER_REGISTRATION,Context.MODE_PRIVATE)
        val str= mPrefs!!.getString(USER_REGISTRATION,null)
        if(str==null)
            return NewUserRegistration()
        var obj = Gson().fromJson<NewUserRegistration>(str, NewUserRegistration::class.java)
        return  obj
    }

    fun clearUser(context: Context){
        mPrefs = context.getSharedPreferences(USER_REGISTRATION,Context.MODE_PRIVATE)
        var editor = mPrefs!!.edit()
        editor.clear()
        editor.apply()
    }

}
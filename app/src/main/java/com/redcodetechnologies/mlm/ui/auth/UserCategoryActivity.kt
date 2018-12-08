package com.redcodetechnologies.mlm.ui.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.MakeTableData
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback

class UserCategoryActivity : AppCompatActivity() {

    var btn_sales_executive : Button? = null
    var btn_sleeping_partner : Button? = null
    var progressdialog: android.app.AlertDialog? = null

    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_category)

        prefs = SharedPrefs.getInstance()!!
        progressdialog = SpotsDialog.Builder()
                .setContext(this@UserCategoryActivity)
                .setMessage("Loading please wait!!")
                .setTheme(R.style.CustomProgess)
                .build()
        if (prefs.getUser(this).userId != null) {
            id = prefs.getUser(this).userId
            token = prefs.getToken(this).accessToken!!
        }

        btn_sales_executive = findViewById(R.id.btn_sales_executive)
        btn_sleeping_partner = findViewById(R.id.btn_sleeping_partner)

        btn_sales_executive!!.setOnClickListener(View.OnClickListener {
            salesExecutive()
        })

        btn_sleeping_partner!!.setOnClickListener(View.OnClickListener {
            sleepingPatener()
        })
    }




    fun salesExecutive(){
        if (!Apputils.isNetworkAvailable(this@UserCategoryActivity)) {
            Toast.makeText(this@UserCategoryActivity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.approvesaleexecutive("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()
                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        var msg = response.message()!!

                        if (code == 401) {
                            Apputils.showMsg(this@UserCategoryActivity, "Token Expired")
                            tokenExpire();

                        }
                        progressdialog!!.dismiss()
                        if (code == 200) {
                            var obj:NewUserRegistration = prefs.getUser(this@UserCategoryActivity)
                            obj.isSalesExecutive = true
                            prefs.setUser(this@UserCategoryActivity,obj)
                            var i :Intent  = Intent(this@UserCategoryActivity, DrawerActivity::class.java)
                            i.putExtra("Category","Sales")
                            startActivity(i)
                            finish()
                        }
                        else{
                            Apputils.showMsg(this@UserCategoryActivity, msg)
                        }

                    }
                })
    }

    fun sleepingPatener(){
        if (!Apputils.isNetworkAvailable(this@UserCategoryActivity)) {
            Toast.makeText(this@UserCategoryActivity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.approvesleepingpartner("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()
                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        var msg = response.message()!!
                        progressdialog!!.dismiss()
                        if (code == 401) {
                            Apputils.showMsg(this@UserCategoryActivity, "Token Expired")
                            tokenExpire();
                        }

                        if (code == 200) {
                            var i :Intent  = Intent(this@UserCategoryActivity, DrawerActivity::class.java)
                            var obj:NewUserRegistration = prefs.getUser(this@UserCategoryActivity)
                            obj.isSleepingPartner = true
                            prefs.setUser(this@UserCategoryActivity,obj)

                            i.putExtra("Category","Sleeping")
                            startActivity(i)
                            finish()
                        }
                        else{
                            Apputils.showMsg(this@UserCategoryActivity, msg)
                        }

                    }
                })
    }


    fun tokenExpire() {
        prefs.clearToken(this@UserCategoryActivity)
        prefs.clearUser(this@UserCategoryActivity)
        startActivity(Intent(this@UserCategoryActivity, SignInActivity::class.java))
       finish()

    }

    override fun onStart() {
        prefs = SharedPrefs.getInstance()!!
        var obj = prefs!!.getUser(this@UserCategoryActivity);
        if(obj.isSleepingPartner == true ){
            var i :Intent  = Intent(this@UserCategoryActivity, DrawerActivity::class.java)
            i.putExtra("Category","Sleeping")
            startActivity(i)
            finish()
        }else if(obj.isSalesExecutive == true){
            var i :Intent  = Intent(this@UserCategoryActivity, DrawerActivity::class.java)
            i.putExtra("Category","Sales")
            startActivity(i)
            finish()
        }
        super.onStart()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_layout)
        fragment!!.onActivityResult(requestCode, resultCode, data)
    }

}

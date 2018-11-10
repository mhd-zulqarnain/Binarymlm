package com.redcodetechnologies.mlm.ui.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.utils.Apputils

import com.redcodetechnologies.mlm.models.ApiToken
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.utils.ServiceError
import com.redcodetechnologies.mlm.utils.ServiceListener
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback

class SignInActivity : AppCompatActivity() {

    var progressdialog: android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val udata = "Forget Your Password"
        val content = SpannableString(udata)

        content.setSpan(UnderlineSpan(), 0, 20, 0)
        progressdialog = SpotsDialog.Builder()
                .setContext(this@SignInActivity)
                .setMessage("Authenticating please wait!!")
                .setTheme(R.style.CustomProgess)
                .build()

        tv_forgetpassword.setText(content)

        btn_submit.setOnClickListener(View.OnClickListener {

            Log.wtf("", "Validiate password!")

            if (ed_username.text.toString().trim(' ').length < 1) {
                ed_username.error = Html.fromHtml("<font color='#E0796C'>User name could not be empty</font>")
                ed_username.requestFocus()
            } else if (ed_password.text.toString().trim(' ').length < 1) {
                ed_password.error = Html.fromHtml("<font color='#E0796C'>Password could not be empty</font>")
                ed_password.requestFocus()
            } else if (ed_password.text.toString().trim(' ').length < 8) {
                ed_password.error = Html.fromHtml("<font color='#E0796C'>Password must contain 8 characters</font>")
                ed_password.requestFocus()
            } else {


                getuserData(object : ServiceListener<ApiToken> {
                    override fun success(obj: ApiToken) {
                        print("success")
                        var pref = SharedPrefs.getInstance()
                        pref!!.setToken(this@SignInActivity, obj)
                        getUserObject(ed_username!!.text.toString())
                    }

                    override fun fail(error: ServiceError) {
                        Apputils.showMsg(this@SignInActivity, "Wrong password or username")
                    }
                })

            }

        })

        tv_forgetpassword.setOnClickListener(View.OnClickListener {

            Log.wtf("", "forget Password")
            showSendDialog()

        })

    }

    private fun showSendDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialogue_forget_password, null)
        val alertBox = AlertDialog.Builder(this)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        var ed_email_address: EditText = view.findViewById(R.id.ed_email)
        var button_submit: Button = view.findViewById(R.id.btn_submit)

        button_submit.setOnClickListener {
            if (ed_email_address.text.toString().trim(' ').length < 1) {
                ed_email_address.error = Html.fromHtml("<font color='#E0796C'>Email address cant be null</font>")
                ed_email_address.requestFocus()
            } else if (!Apputils.isValidEmail(ed_email_address.text.toString())) {
                ed_email_address.error = Html.fromHtml("<font color='#E0796C'>Please enter correct Email Address</font>")
                ed_email_address.requestFocus()
            } else {

                forgetPassword(ed_email_address.text.toString(), object : ServiceListener<Response> {
                    override fun fail(error: ServiceError) {
                        dialog.dismiss()
                        Apputils.showMsg(this@SignInActivity,"Email not exists")
                    }
                    override fun success(obj: Response) {
                        Apputils.showMsg(this@SignInActivity,obj.message.toString())
                        dialog.dismiss()
                    }
                })
            }
        }
        dialog.show()
    }

    private fun getuserData(serviceListener: ServiceListener<ApiToken>) {
        if (!Apputils.isNetworkAvailable(this@SignInActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        ApiClint.getInstance()?.getService()?.verifyEmail("password", ed_username.text.toString(), ed_password.text.toString())
                ?.enqueue(object : Callback<ApiToken> {
                    override fun onFailure(call: Call<ApiToken>?, t: Throwable?) {

                        println("error")

                    }

                    override fun onResponse(call: Call<ApiToken>?, response: retrofit2.Response<ApiToken>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            serviceListener.success(response.body()!!)
                            print("success")
                        } else {
                            serviceListener.fail(ServiceError())

                        }
                    }
                })
    }

    private fun getUserObject(username: String) {
        var token = SharedPrefs.getInstance()!!.getToken(this@SignInActivity).accessToken

        if (!Apputils.isNetworkAvailable(this@SignInActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.getNewRegistoredUser("bearer " + token!!, username)
                ?.enqueue(object : Callback<NewUserRegistration> {
                    override fun onFailure(call: Call<NewUserRegistration>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }
                    override fun onResponse(call: Call<NewUserRegistration>?, response: retrofit2.Response<NewUserRegistration>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            print("success")
                            var obj: NewUserRegistration = response.body()!!
                            SharedPrefs.getInstance()!!.setUser(this@SignInActivity, obj)
                            if (obj.isSleepingPartner == true)
                                sleepingPatnerActivity()
                            else if (obj.isSalesExecutive == true)
                                salesExecutiveActivity()
                            else {
                                progressdialog!!.dismiss()
                                var i: Intent = Intent(this@SignInActivity, UserCategoryActivity::class.java)
                                startActivity(i)
                                finish()
                            }
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                    }
                })
    }
    //Forget Password
    private fun forgetPassword(email: String, serviceListener: ServiceListener<Response>) {

        if (!Apputils.isNetworkAvailable(this@SignInActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.forgetPassword(email)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()
                    }
                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        var code: Int = response!!.code()
                        if (code == 200) {
                            print("success")
                            serviceListener.success(response.body()!!)

                        } else {
                            serviceListener.fail(ServiceError())
                        }
                        progressdialog!!.dismiss()
                    }
                })
    }

    fun salesExecutiveActivity() {
        var i: Intent = Intent(this@SignInActivity, DrawerActivity::class.java)
        i.putExtra("Category", "Sales")
        progressdialog!!.dismiss()
        startActivity(i)
        finish()
    }

    fun sleepingPatnerActivity() {
        var i: Intent = Intent(this@SignInActivity, DrawerActivity::class.java)
        i.putExtra("Category", "Sleeping")
        progressdialog!!.dismiss()
        startActivity(i)
        finish()
    }

    override fun onStart() {
        super.onStart()
        var pref = SharedPrefs.getInstance()
        var obj = pref!!.getUser(this@SignInActivity)
        if (obj.userCode != null) {

            if (obj.isSleepingPartner == true)
                sleepingPatnerActivity()
            else if (obj.isSalesExecutive == true)
                salesExecutiveActivity()
            else {
                var i: Intent = Intent(this@SignInActivity, UserCategoryActivity::class.java)
                startActivity(i)
                finish()
            }
        }

    }

}

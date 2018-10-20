package com.redcodetechnologies.mlm

import android.content.Context
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
import com.redcodetechnologies.mlm.utills.Apputils
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
   // var  ctx: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val udata = "Forget Your Password"
        val content = SpannableString(udata)
        content.setSpan(UnderlineSpan(), 0, 20, 0)

        tv_forgetpassword.setText(content)


        btn_submit.setOnClickListener(View.OnClickListener {

            Log.wtf("","Validiate password!")

            if(ed_username.text.toString().trim(' ').length < 1){
                ed_username.error = Html.fromHtml("<font color='#E0796C'>User name could not be empty</font>")
                ed_username.requestFocus()
            }
            else if(ed_password.text.toString().trim(' ').length < 1){
                ed_password.error = Html.fromHtml("<font color='#E0796C'>Password could not be empty</font>")
                ed_password.requestFocus()
            }
           else if(ed_password.text.toString().trim(' ').length < 8){
                ed_password.error = Html.fromHtml("<font color='#E0796C'>Password must contain 8 characters</font>")
                ed_password.requestFocus()
            }
            else{

                    val intent = Intent(this, DrawerActivity::class.java)
                    startActivity(intent)

            }


        })

        tv_forgetpassword.setOnClickListener(View.OnClickListener {

            Log.wtf("", "forget Password")
            showSendDialog()

        })






    }// onCreate();

    fun signIn(v:View){
        startActivity(Intent(this@SignInActivity,DrawerActivity::class.java))
        finish()
    }

    private fun showSendDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialogue_forget_password, null)
        val alertBox = AlertDialog.Builder(this)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()

         dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        var ed_email_address: EditText =view.findViewById(R.id.ed_email)
        var button_submit: Button = view.findViewById(R.id.btn_submit)

        button_submit.setOnClickListener {
            if(ed_email_address.text.toString().trim(' ').length < 1){
                ed_email_address.error = Html.fromHtml("<font color='#E0796C'>Email address cant be null</font>")
                ed_email_address.requestFocus()
            }
            else if(!Apputils.isValidEmail(ed_email_address.text.toString())){
                ed_email_address.error = Html.fromHtml("<font color='#E0796C'>Please enter correct Email Address</font>")
                ed_email_address.requestFocus()
            }
            else{
                (Toast.makeText(this, "Under Process", Toast.LENGTH_SHORT).show())
            }
        }

        dialog.show()

    }

}

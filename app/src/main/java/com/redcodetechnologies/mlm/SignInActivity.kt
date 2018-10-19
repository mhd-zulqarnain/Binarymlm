package com.redcodetechnologies.mlm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val forgettext = findViewById(R.id.forgettext) as TextView

        val udata = "Forget Your Password"
        val content = SpannableString(udata)
        content.setSpan(UnderlineSpan(), 0, 20, 0)

        forgettext.setText(content)

    }

    fun signIn(v:View){
        startActivity(Intent(this@SignInActivity,DrawerActivity::class.java))
        finish()
    }
}

package com.redcodetechnologies.mlm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView

class Activity_SignIn : AppCompatActivity() {

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val forgettext = findViewById(R.id.forgettext) as TextView

        val udata = "Forget Your Password"
        val content = SpannableString(udata)
        content.setSpan(UnderlineSpan(), 0, 20, 0)

        forgettext.setText(content)

    }
}

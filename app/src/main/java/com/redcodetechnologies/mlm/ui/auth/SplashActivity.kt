package com.redcodetechnologies.mlm.ui.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.redcodetechnologies.mlm.R
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

         Handler().postDelayed(object: Runnable {
             override fun run() {
                 startActivity( Intent(this@SplashActivity, SignInActivity::class.java))
                 finish(); }

        }, 1000);

    }


}

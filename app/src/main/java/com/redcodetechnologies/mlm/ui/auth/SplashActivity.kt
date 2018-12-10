package com.redcodetechnologies.mlm.ui.auth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.redcodetechnologies.mlm.R
import gr.net.maroulis.library.EasySplashScreen

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val config = EasySplashScreen(this@SplashActivity)
                .withFullScreen()
                .withTargetActivity(SignInActivity::class.java)
                .withSplashTimeOut(2000)
                .withLogo(R.drawable.sleepinglogo)

        val view = config.create()
        setContentView(view)


    }
}

package com.redcodetechnologies.mlm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import gr.net.maroulis.library.EasySplashScreen

class Activity_Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val config = EasySplashScreen(this@Activity_Splash)
                .withFullScreen()
                .withTargetActivity(Activity_SignIn::class.java)
                .withSplashTimeOut(3000)
                .withLogo(R.drawable.sleepinglogo)

        val view = config.create()
        setContentView(view)


    }
}

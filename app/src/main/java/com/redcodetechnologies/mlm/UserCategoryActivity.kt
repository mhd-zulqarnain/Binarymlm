package com.redcodetechnologies.mlm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class UserCategoryActivity : AppCompatActivity() {

    var btn_sales_executive : Button? = null
    var btn_sleeping_partner : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_category)
        btn_sales_executive = findViewById(R.id.btn_sales_executive)
        btn_sleeping_partner = findViewById(R.id.btn_sleeping_partner)


        btn_sales_executive!!.setOnClickListener(View.OnClickListener {
            var i :Intent  = Intent(this@UserCategoryActivity,DrawerActivity::class.java)
            i.putExtra("Category","Sales")
            startActivity(i)
            finish()

        })

        btn_sleeping_partner!!.setOnClickListener(View.OnClickListener {
            var i :Intent  = Intent(this@UserCategoryActivity,DrawerActivity::class.java)
            i.putExtra("Category","Sleeping")
            startActivity(i)
            finish()

        })

    }

}

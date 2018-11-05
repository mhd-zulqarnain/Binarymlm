package com.redcodetechnologies.mlm.ui.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.utils.SharedPrefs

class UserCategoryActivity : AppCompatActivity() {

    var btn_sales_executive : Button? = null
    var btn_sleeping_partner : Button? = null
    var prefs :SharedPrefs? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_category)
        btn_sales_executive = findViewById(R.id.btn_sales_executive)
        btn_sleeping_partner = findViewById(R.id.btn_sleeping_partner)

        btn_sales_executive!!.setOnClickListener(View.OnClickListener {
            salesExecutiveActivity()
        })

        btn_sleeping_partner!!.setOnClickListener(View.OnClickListener {
            SleepingPatnerActivity()
        })
    }

    fun salesExecutiveActivity(){
        var i :Intent  = Intent(this@UserCategoryActivity, DrawerActivity::class.java)
        i.putExtra("Category","Sales")
        startActivity(i)
        finish()
    }
    fun SleepingPatnerActivity(){
        var i :Intent  = Intent(this@UserCategoryActivity, DrawerActivity::class.java)
        i.putExtra("Category","Sleeping")
        startActivity(i)
        finish()
    }

    override fun onStart() {
        prefs = SharedPrefs.getInstance()
        var obj = prefs!!.getUser(this@UserCategoryActivity);
        if(obj.isSleepingPartner==1 ){
            SleepingPatnerActivity()
        }else if(obj.isSalesExecutive== 1){
            salesExecutiveActivity()
        }
        super.onStart()
    }

}

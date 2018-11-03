package com.redcodetechnologies.mlm.ui.network
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Users
import kotlinx.android.synthetic.main.activity_member_detail.*

class MemberDetailActivity : AppCompatActivity() {
     var user: Users?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var json = intent.getStringExtra("object")
        if(json!=null)
        user = Gson().fromJson(json,Users::class.java);

        setContentView(R.layout.activity_member_detail)
        initView()

        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun initView() {
        if(user!=null) {
            tv_username.text = user!!.UserName
            tv_phone.setText( "Phone:" + user!!.Phone)
            tv_BankName.setText("Bank: "+ user!!.BankName)
            tv_SponsorName.setText("Sponser: "+user!!.SponsorName)
        }
    }
}

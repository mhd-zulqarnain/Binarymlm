package com.redcodetechnologies.mlm.ui.network

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.network.adapter.DialogMemberAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_member_detail.*
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList

class MemberDetailActivity : AppCompatActivity() {
    var user: Users? = null;
    var id: Int? = null
    lateinit var token: String
    lateinit var prefs: SharedPrefs
    var progressdialog: android.app.AlertDialog? = null
    var userList: ArrayList<Users> = ArrayList()
    var adapter: DialogMemberAdapter? = null
    var tv_no_data: TextView? = null
    var progressBar: LinearLayout? = null
    var layout_add_right: LinearLayout?=null
    var layout_add_left: LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var json = intent.getStringExtra("object")
        prefs = SharedPrefs.getInstance()!!

        if (json != null)
            user = Gson().fromJson(json, Users::class.java);

        setContentView(R.layout.activity_member_detail)
        initView()
        btn_back.setOnClickListener {
            finish()
        }
        view_show_tree.setOnClickListener {
            showSendDialog()
        }
    }

    private fun initView() {

        progressdialog = SpotsDialog.Builder()
                .setContext(this@MemberDetailActivity)
                .setMessage("Loading!!")
                .setTheme(R.style.CustomProgess)
                .build()

        if (user != null) {
            id = user!!.UserId!!.toInt()
            token = prefs.getToken(this@MemberDetailActivity).accessToken!!
            tv_username.text = user!!.Username
            tv_phone.setText("Phone:" + user!!.Phone)
            tv_BankName.setText("Bank: " + user!!.BankName)
            tv_SponsorName.setText("Sponser: " + user!!.SponsorName)
        }


    }

    private fun showSendDialog() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_member_tree, null)
        val alertBox = AlertDialog.Builder(this)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        tv_no_data = view.findViewById(R.id.tv_no_data)
        progressBar = view.findViewById(R.id.progressBar)

         layout_add_right = view.findViewById(R.id.layout_add_right)
         layout_add_left = view.findViewById(R.id.layout_add_left)
        val recylcer_dialog_member: RecyclerView = view.findViewById(R.id.recylcer_dialog_member)

        recylcer_dialog_member!!.layoutManager = LinearLayoutManagerWrapper(this, LinearLayout.VERTICAL, false)
        adapter = DialogMemberAdapter(this@MemberDetailActivity, userList)
        recylcer_dialog_member!!.adapter = adapter

        getAllDownlineMembersLeft()
        layout_add_left!!.setOnClickListener {
            layout_add_right!!.setBackgroundResource(R.color.colorGray);
            layout_add_left!!.setBackgroundResource(R.color.colorRed);
            getAllDownlineMembersLeft()
        }
        layout_add_right!!.setOnClickListener {
            layout_add_left!!.setBackgroundResource(R.color.colorGray);
            layout_add_right!!.setBackgroundResource(R.color.colorRed);
            getAllDownlineMembersRight()
        }

        dialog.show()

    }

    fun showPrgressbar(){
        layout_add_right!!.setClickable(false);
        layout_add_left!!.setClickable(false);
        progressBar!!.visibility= View.VISIBLE
        tv_no_data!!.visibility= View.GONE
    }
    fun hideprogressbar(){
        layout_add_right!!.setClickable(true);
        layout_add_left!!.setClickable(true);
        progressBar!!.visibility= View.GONE
//        tv_no_data!!.visibility= View.VISIBLE
    }
    //getDownLineRight
    fun getAllDownlineMembersRight() {

        if (!Apputils.isNetworkAvailable(this@MemberDetailActivity)) {
            Toast.makeText(this@MemberDetailActivity, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        userList.clear()
        showPrgressbar()
        ApiClint.getInstance()?.getService()?.getAllDownlineMembersRight("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<ArrayList<Users>> {
                    override fun onFailure(call: Call<ArrayList<Users>>?, t: Throwable?) {
                        println("error")
                        //progressdialog!!.hide();
                        hideprogressbar()

                    }

                    override fun onResponse(call: Call<ArrayList<Users>>?, response: retrofit2.Response<ArrayList<Users>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(this@MemberDetailActivity, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                userList.add(user)
                            }
                            adapter!!.notifyDataSetChanged()
                            if(response.body()!!.size==0){
                                tv_no_data!!.visibility = View.VISIBLE
                            }
                            else
                                tv_no_data!!.visibility = View.GONE
                        }
                        hideprogressbar()

                        // progressdialog!!.hide();


                    }
                })
    }

    //getDownLineRight
    fun getAllDownlineMembersLeft() {

        if (!Apputils.isNetworkAvailable(this@MemberDetailActivity)) {
            Toast.makeText(this@MemberDetailActivity, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        userList.clear()
        showPrgressbar()

        ApiClint.getInstance()?.getService()?.getAllDownlineMembersLeft("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<ArrayList<Users>> {
                    override fun onFailure(call: Call<ArrayList<Users>>?, t: Throwable?) {
                        println("error")
                        hideprogressbar()


                    }

                    override fun onResponse(call: Call<ArrayList<Users>>?, response: retrofit2.Response<ArrayList<Users>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(this@MemberDetailActivity, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                userList.add(user)
                            }
                            if(response.body()!!.size==0){
                                tv_no_data!!.visibility = View.VISIBLE
                            }
                            else
                                tv_no_data!!.visibility = View.GONE

                            adapter!!.notifyDataSetChanged()
                        }
                        hideprogressbar()


                    }
                })
    }
    fun tokenExpire() {
        prefs.clearToken(this@MemberDetailActivity)
        prefs.clearUser(this@MemberDetailActivity)
        startActivity(Intent(this@MemberDetailActivity, SignInActivity::class.java))
       finish()
    }

}

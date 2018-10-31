package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Typeface
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.InputType
import android.widget.*
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.adapter.DashboardAdapter
import com.redcodetechnologies.mlm.models.AddsModal


class DashBoardFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var tv:CardView?=null
    var click:Boolean=true;
    var recycler_adds : RecyclerView? = null
    var list: ArrayList<AddsModal> = ArrayList()
    var adapter: DashboardAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  =inflater.inflate(R.layout.fragment_dashboard, container, false)
        tv = view.findViewById(R.id.dashboardbalance) as CardView;

        list.add(AddsModal("One Plus"))
        list.add(AddsModal("I phone"))
        list.add(AddsModal("Black berry"))
        list.add(AddsModal("Oppo"))
        recycler_adds = view.findViewById(R.id.recylcer_adds)
        val manager = GridLayoutManager(activity!!, 2)
        recycler_adds!!.layoutManager =manager
        adapter = DashboardAdapter(activity!!,frgement_type, list)
        recycler_adds!!.adapter = adapter

        tv!!.setOnClickListener{
            if(click){
                showSendDialog(view)

            }


        }

        return view

    }

    private fun showSendDialog(v1:View) {
        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialogue_forget_password, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        var tvtitle: TextView = view .findViewById(R.id.tvtitle)
        var tvdescription: TextView = view .findViewById(R.id.tvdescription)
        tvtitle.text = "Enter Password"
        tvdescription.layoutParams = LinearLayout.LayoutParams(10, 10, 1.75f)
        tvdescription.text=""

        var ed_email_address: EditText =view.findViewById(R.id.ed_email)
        ed_email_address.visibility=View.GONE
        var pass: EditText =view.findViewById(R.id.ed_pass)
        pass.visibility=View.VISIBLE
        var button_submit: Button = view.findViewById(R.id.btn_submit)

        button_submit.setOnClickListener {
            if(pass.text.toString().trim(' ').length < 1){
                pass.error = Html.fromHtml("<font color='#E0796C'>Password cant be null</font>")
                pass.requestFocus()
            }

            else{
                Toast.makeText(activity!!, "Under Process", Toast.LENGTH_SHORT).show()

var sucess:Boolean=false;


                sucess=true;
                if(sucess) {
                    var tv_wallet_balance: TextView = v1.findViewById(R.id.tv_wallet_balance)
                    tv_wallet_balance.visibility = View.VISIBLE
                    click=false
                } else{
                    Toast.makeText(activity!!, "Wrong-Password", Toast.LENGTH_SHORT).show()

                }
                dialog.dismiss()

            }
        }

        dialog.show()

    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Dashboard")
    }


}

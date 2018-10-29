package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.R

class SleepingDashboardFragment : Fragment() {
    var tv: CardView?=null
    var click:Boolean=true;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  =inflater.inflate(R.layout.fragment_sleeping_dashboard, container, false)
        tv = view.findViewById(R.id.dashboardbalance) as CardView;
        tv!!.setOnClickListener{
            if(click){
                showSendDialog(view)


            }


        }

        return view

    }

    private fun showSendDialog(v1: View) {
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
        ed_email_address.visibility= View.GONE
        var pass: EditText =view.findViewById(R.id.ed_pass)
        pass.visibility= View.VISIBLE
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

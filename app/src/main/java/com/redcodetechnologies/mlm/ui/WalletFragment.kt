package com.redcodetechnologies.mlm.ui

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.WalletSummery
import kotlinx.android.synthetic.main.fragment_ewalletsummary.*

class WalletFragment : Fragment(){
    var timeType: String = "Year"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_wallet, container, false)
        initView(view)
        return view
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    fun initView(view:View){
        var wData = ArrayList<WalletSummery>()
        wData.add(WalletSummery("Bonus","10PKR"))
        wData.add(WalletSummery("WithDrawl","20PKR"))
        var timeStr= arrayOf("All","By Year" , "By Month")

        spin_time.adapter= ArrayAdapter(activity!!,android.R.layout.simple_spinner_dropdown_item, timeStr)
        spin_time!!.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item = parent!!.getItemAtPosition(position);
                 timeType = item as String
            }
        }
    }

}



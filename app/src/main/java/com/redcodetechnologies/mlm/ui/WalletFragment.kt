package com.redcodetechnologies.mlm.ui
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.WalletSummeryAdapter
import com.redcodetechnologies.mlm.models.WalletSummery
import kotlinx.android.synthetic.main.fragment_ewalletsummary.*
class WalletFragment : Fragment() {

    var timeType: String = "All"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_ewalletsummary, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {

        var wData = ArrayList<WalletSummery>()
        wData.add(WalletSummery("Bonus", "10P KR"))
        wData.add(WalletSummery("WithDrawl", "20 PKR"))
        var tv_bonus: TextView? = null
        var tv_debit: TextView? = null
        var Nbonus: TextView? = null
        var Ndebit: TextView? = null

        tv_bonus = view!!.findViewById(R.id.wallet_Bcurrency)
        tv_debit = view!!.findViewById(R.id.wallet_Dcurrency)
        Nbonus = view!!.findViewById(R.id.wallet_bonus)
        Ndebit = view!!.findViewById(R.id.wallet_debit)

        tv_bonus!!.setText(wData.get(0).Balance.plus(" ").toString())
        tv_debit!!.setText(wData.get(1).Balance.plus(" ").toString())
        Nbonus!!.setText(wData.get(0).Catagory.toString())
        Ndebit!!.setText(wData.get(1).Catagory.toString())

    }
}


        //Log.d("Arif",wData.get(1).toString())

//        var recyclerView = view!!.findViewById(R.id.wallet_summery_recycler) as RecyclerView
//            recyclerView!!.layoutManager=  LinearLayoutManager(activity!!,LinearLayout.VERTICAL,false)
//
//         var wAdapter = WalletSummeryAdapter(activity!!,wData)
//            recyclerView!!.adapter=wAdapter
//

  //  }
//}



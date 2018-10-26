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
    private fun initView(view: View?) {
        list.add(WalletModal("Checque", "HBL", "9000", "12-10-2018"))
        list.add(WalletModal("Checque", "ABL", "9500", "17-10-2018"))
        list.add(WalletModal("Checque", "NBP", "9030", "19-10-2018"))
        recylcer_wallet = view!!.findViewById(R.id.recylcer_down)
        tv_header = view!!.findViewById(R.id.tv_walt_header)
        tv_source = view!!.findViewById(R.id.tv_walt_source)
        tv_name = view!!.findViewById(R.id.tv_walt_name)
        tv_amount = view!!.findViewById(R.id.tv_walt_amount)
        tv_date = view!!.findViewById(R.id.tv_walt_date)

        recylcer_wallet!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = PackageCommisionListAdapter(activity!!,frgement_type, list)
        recylcer_wallet!!.adapter = adapter



        showViews()
    }
    fun showViews() {
        if (frgement_type == "MyPackageCommisionList") {
            tv_action!!.visibility = View.GONE
            tv_name!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_source!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_amount!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_date!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_action!!.layoutParams = LinearLayout.LayoutParams(0, 0, 0f)

            tv_header!!.text = " My Package Commision List"
        }
        else if (frgement_type == "MyDirectCommisionList") {
            tv_action!!.visibility = View.VISIBLE
            tv_header!!.text = " My Direct Commision List"
        }
        else {
            tv_action!!.visibility = View.VISIBLE
            tv_header!!.text = " My Table Commision List"

        }
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



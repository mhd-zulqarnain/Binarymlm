package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.PackageCommisionListAdapter
import com.redcodetechnologies.mlm.models.PackageCommisionList
import kotlinx.android.synthetic.main.fragment_geneologytable.*
import java.util.ArrayList

class GeneologyTableFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var recylcer_down: RecyclerView? = null
    var adapter: PackageCommisionListAdapter? = null
    var list: ArrayList<PackageCommisionList> = ArrayList()
    val REQUSET_GALLERY_CODE: Int = 43
    var tv_action: TextView? = null
    var tv_header: TextView? = null
    var tv_source: TextView? = null
    var tv_name: TextView? = null
    var tv_amount: TextView? = null
    var tv_date: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_geneologytable, container, false)
        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view
}
    private fun initView(view: View?) {
        list.add(PackageCommisionList("Checque", "HBL", "9000", "12-10-2018"))
        list.add(PackageCommisionList("Checque", "ABL", "9500", "17-10-2018"))
        list.add(PackageCommisionList("Checque", "NBP", "9030", "19-10-2018"))
        recylcer_down = view!!.findViewById(R.id.recylcer_down)
        tv_action = view!!.findViewById(R.id.tv_tran_action)
        tv_header = view!!.findViewById(R.id.tv_header)
        tv_source = view!!.findViewById(R.id.tv_tran_source)
        tv_name = view!!.findViewById(R.id.tv_tran_name)
        tv_amount = view!!.findViewById(R.id.tv_tran_amount)
        tv_date = view!!.findViewById(R.id.tv_tran_date)

        recylcer_down!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = PackageCommisionListAdapter(activity!!,frgement_type, list)
        recylcer_down!!.adapter = adapter



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

}
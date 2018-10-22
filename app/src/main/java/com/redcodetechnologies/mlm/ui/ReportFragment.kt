package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.support.v7.widget.SearchView
import com.evrencoskun.tableview.TableView
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.models.Users
import kotlinx.android.synthetic.main.fragment_report.view.*
import java.util.*


class ReportFragment : Fragment() {
    var recylcer_down_member: RecyclerView? = null
    var adapter: DownMemberAdapter? = null
    var layout_add_right: LinearLayout? = null
    var layout_add_left: LinearLayout? = null
    var add_left: ImageView? = null
    var add_right: ImageView? = null
    var list: ArrayList<Users> = ArrayList()
    val REQUSET_GALLERY_CODE: Int = 43

    var dialog_title: TextView? = null
    var ed_name: EditText? = null
    var ed_uname: EditText? = null
    var ed_pass: EditText? = null
    var ed_phone: EditText? = null
    var ed_address: EditText? = null
    var ed_email: EditText? = null
    var ed_account: EditText? = null
    var ed_bank_name: EditText? = null
    var ed_cnic: EditText? = null
    var search_view: SearchView? = null
    var dialog: AlertDialog? = null
    var content_container_table_view: TableView? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_report, container, false)
        /*recylcer_down_member = view.findViewById(R.id.recylcer_down_member)
        recylcer_down_member!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
*/
        return view
    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Report")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }

}

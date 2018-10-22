package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.support.v7.widget.SearchView
import android.util.AttributeSet
import com.company.redcode.royalcryptoexchange.utils.Apputils
import com.redcodetechnologies.mlm.AddMemberActivity
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.models.Users
import java.util.*


class NetworkFragment : Fragment() {
    var recylcer_down_member: RecyclerView? = null
    var adapter: DownMemberAdapter? = null
    var layout_add_right: LinearLayout? = null
    var layout_add_left: LinearLayout? = null
    var add_left: ImageView? = null
    var add_right: ImageView? = null
    var list: ArrayList<Users> = ArrayList()
    val REQUSET_GALLERY_CODE: Int = 43
    var search_view: SearchView? = null
    var fragment_title: TextView? = null
    var frgement_type = "MakeTable"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_network, container, false)
        initView(view)
        frgement_type = arguments?.getString("Fragment").toString();
        return view

    }

    private fun initView(view: View?) {
        list.add(Users("3", "ali", "234234", "1231", "hbl", "200"))
        list.add(Users("3", "ahmed", "234234", "12312", "hbl", "100"))
        list.add(Users("3", "ahmed", "234234", "123123", "hbl", "100"))


        layout_add_right = view!!.findViewById(R.id.layout_add_right)
        layout_add_left = view!!.findViewById(R.id.layout_add_left)
        add_right = view!!.findViewById(R.id.add_right)
        add_left = view!!.findViewById(R.id.add_left)
        recylcer_down_member = view.findViewById(R.id.recylcer_down_member)
        search_view = view.findViewById(R.id.search_view)
        fragment_title = view.findViewById(R.id.fragment_title)

        recylcer_down_member!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = DownMemberAdapter(activity!!, list)
        recylcer_down_member!!.adapter = adapter
        layout_add_left!!.setOnClickListener {
            layout_add_right!!.setBackgroundResource(R.color.colorGray);
            layout_add_left!!.setBackgroundResource(R.color.colorRed);
        }
        layout_add_right!!.setOnClickListener {
            layout_add_left!!.setBackgroundResource(R.color.colorGray);
            layout_add_right!!.setBackgroundResource(R.color.colorRed);
        }

        add_left!!.setOnClickListener {
            var intent = Intent(activity!!, AddMemberActivity::class.java)
            intent.putExtra("type","left")
            activity!!.startActivity(intent)
//            showDialog("left")
        }
        add_right!!.setOnClickListener {
            var intent = Intent(activity!!, AddMemberActivity::class.java)
            intent.putExtra("type","right")
            activity!!.startActivity(intent)

//            showDialog("right")
        }
//        search_view.set

        search_view!!.setOnClickListener {
            search_view!!.setIconified(false)
        }

        search_view!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                adapter!!.getFilter().filter(query)
                return false
            }
        })

        showViews()
    }

    fun showViews() {
        if (frgement_type == "MakeTable") {
            add_left!!.visibility = View.VISIBLE
            add_right!!.visibility = View.VISIBLE
            fragment_title!!.text = "Down-line Members List"
        } else if (frgement_type == "DownlineMembers") {
            add_left!!.visibility = View.GONE
            add_right!!.visibility = View.GONE
            fragment_title!!.text = "Down-line Members List"
        } else {
            add_left!!.visibility = View.GONE
            add_right!!.visibility = View.GONE
            fragment_title!!.text = "Refered Members List"

        }
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Make Table")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }

}

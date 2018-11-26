package com.redcodetechnologies.mlm.ui.network

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.support.v7.widget.SearchView
import com.google.gson.Gson
import com.redcodetechnologies.mlm.*
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.ui.network.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.models.MakeTableData
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
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
    var progressdialog: android.app.AlertDialog? = null
    var tv_leftRemaingAmount: TextView? = null;
    var tv_rightRemaingAmount: TextView? = null;
    var tv_totalLeftUsers: TextView? = null;
    var tv_totalRightUsers: TextView? = null;
    var tv_totalAmountRightUsers: TextView? = null;
    var tv_totalAmountLeftUsers: TextView? = null;
    var tv_sponser: TextView? = null;
    var tv_no_data: TextView? = null;
    var tv_total: TextView? = null;

    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String
    var total: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        prefs = SharedPrefs.getInstance()!!
        var view = inflater.inflate(R.layout.fragment_network, container, false)
        frgement_type = arguments?.getString("Fragment").toString();

        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId
            token = prefs.getToken(activity!!).accessToken!!
        }
        initView(view)

        return view

    }

    private fun initView(view: View?) {

        progressdialog = SpotsDialog.Builder()
                .setContext(activity!!)
                .setMessage("Loading!!")
                .setTheme(R.style.CustomProgess)
                .build()

        layout_add_right = view!!.findViewById(R.id.layout_add_right)
        layout_add_left = view!!.findViewById(R.id.layout_add_left)
        add_right = view!!.findViewById(R.id.add_right)
        add_left = view!!.findViewById(R.id.add_left)
        recylcer_down_member = view.findViewById(R.id.recylcer_down_member)
        search_view = view.findViewById(R.id.search_view)
        fragment_title = view.findViewById(R.id.fragment_title)
        tv_no_data = view.findViewById(R.id.tv_no_data)
        tv_total = view.findViewById(R.id.tv_total)

        tv_leftRemaingAmount = view.findViewById(R.id.tv_leftRemaingAmount)
        tv_rightRemaingAmount = view.findViewById(R.id.tv_rightRemaingAmount)
        tv_totalLeftUsers = view.findViewById(R.id.tv_totalLeftUsers)
        tv_totalRightUsers = view.findViewById(R.id.tv_totalRightUsers)
        tv_totalAmountRightUsers = view.findViewById(R.id.tv_totalAmountRightUsers)
        tv_totalAmountLeftUsers = view.findViewById(R.id.tv_totalAmountLeftUsers)
        tv_sponser = view.findViewById(R.id.tv_sponser)

        getviewData()

        recylcer_down_member!!.layoutManager = LinearLayoutManagerWrapper(activity!!, LinearLayout.VERTICAL, false)
        adapter = DownMemberAdapter(activity!!, list, frgement_type) { obj ->
            if (frgement_type != "MakeTable") {
                var intent = Intent(activity!!, MemberDetailActivity::class.java)
                var json = Gson().toJson(obj)
                intent.putExtra("object", json)
                startActivity(intent)
            }
        }

        recylcer_down_member!!.adapter = adapter

        layout_add_left!!.setOnClickListener {
            layout_add_right!!.setBackgroundResource(R.color.colorGray);
            layout_add_left!!.setBackgroundResource(R.color.colorRed);
            total = 0.0
            if (frgement_type == "MakeTable")
                getMakeTableLeft()
            else {
                getAllDownlineMembersLeft()
            }

        }
        layout_add_right!!.setOnClickListener {
            layout_add_left!!.setBackgroundResource(R.color.colorGray);
            layout_add_right!!.setBackgroundResource(R.color.colorRed);
            total = 0.0
            if (frgement_type == "MakeTable")
                getMakeTableRight()
            else
                getAllDownlineMembersRight()
        }

        add_left!!.setOnClickListener {
            var intent = Intent(activity!!, AddMemberActivity::class.java)
            intent.putExtra("type", "left")

            activity!!.startActivity(intent)
//            showDialog("left")
        }
        add_right!!.setOnClickListener {
            var intent = Intent(activity!!, AddMemberActivity::class.java)
            intent.putExtra("type", "right")
            activity!!.startActivity(intent)

        }

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
        if (frgement_type == "MakeTable")
            getMakeTableLeft()
        else {
            getAllDownlineMembersLeft()

        }

    }

    fun showViews() {
        if (frgement_type == "MakeTable") {
            add_left!!.visibility = View.VISIBLE
            add_right!!.visibility = View.VISIBLE
            tv_sponser!!.setTextColor(getResources().getColor(R.color.colorWhite));
            fragment_title!!.text = "Add new Member"
            (activity as DrawerActivity).getSupportActionBar()?.setTitle("Add new Member")

        } else if (frgement_type == "DownlineMembers") {
            add_left!!.visibility = View.GONE
            add_right!!.visibility = View.GONE

            tv_sponser!!.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            fragment_title!!.text = "Down-line Members "
            (activity as DrawerActivity).getSupportActionBar()?.setTitle("Down-line Members")

        } else {
            add_left!!.visibility = View.GONE
            add_right!!.visibility = View.GONE
            tv_sponser!!.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            fragment_title!!.text = "Direct Members"
            (activity as DrawerActivity).getSupportActionBar()?.setTitle("Direct Members")

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }

    fun getviewData() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()

        ApiClint.getInstance()?.getService()?.getMaketableData("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<MakeTableData> {
                    override fun onFailure(call: Call<MakeTableData>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss();

                    }

                    override fun onResponse(call: Call<MakeTableData>?, response: retrofit2.Response<MakeTableData>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            tokenExpire();

                        }
                        if (code == 200) {
                            print("success")
                            var obj: MakeTableData = response.body()!!

                            if (obj.leftRemaingAmount != null)
                                tv_leftRemaingAmount!!.text = obj.leftRemaingAmount!!.split(".")[0];

                            if (obj.rightRemaingAmount != null)
                                tv_rightRemaingAmount!!.text = obj.rightRemaingAmount!!.split(".")[0]

                            if (obj.totalLeftUsers != null)
                                tv_totalLeftUsers!!.text = obj.totalLeftUsers!!.split(".")[0]

                            if (obj.totalRightUsers != null)
                                tv_totalRightUsers!!.text = obj.totalRightUsers!!.split(".")[0]
                            if (obj.totalAmountLeftUsers != null)
                                tv_totalAmountRightUsers!!.text = obj.totalAmountLeftUsers!!.split(".")[0]
                            if (obj.totalAmountRightUsers != null)
                                tv_totalAmountLeftUsers!!.text = obj.totalAmountRightUsers!!.split(".")[0]

                        }
                        progressdialog!!.hide();


                    }
                })
    }

    //<editor-fold desc="Downline member">
    fun getAllDownlineMembersRight() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        list.clear()
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.getAllDownlineMembersRight("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<ArrayList<Users>> {
                    override fun onFailure(call: Call<ArrayList<Users>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss();

                    }

                    override fun onResponse(call: Call<ArrayList<Users>>?, response: retrofit2.Response<ArrayList<Users>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                list.add(user)
                                total += user.PaidAmount!!.toInt()
                            }
                            adapter!!.notifyDataSetChanged()

                            if (list.size == 0) {
                                recylcer_down_member!!.visibility = View.GONE
                                tv_no_data!!.visibility = View.VISIBLE
                                tv_total!!.text = "Total: 0"
                            } else {
                                tv_no_data!!.visibility = View.GONE
                                recylcer_down_member!!.visibility = View.VISIBLE

                            }
                        }
                        progressdialog!!.dismiss();


                    }
                })
    }

    fun getAllDownlineMembersLeft() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        list.clear()
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.getAllDownlineMembersLeft("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<ArrayList<Users>> {
                    override fun onFailure(call: Call<ArrayList<Users>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss();

                    }

                    override fun onResponse(call: Call<ArrayList<Users>>?, response: retrofit2.Response<ArrayList<Users>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                list.add(user)
                                total += user.PaidAmount!!.toInt()
                            }
                            adapter!!.notifyDataSetChanged()

                            if (list.size == 0) {
                                recylcer_down_member!!.visibility = View.GONE
                                tv_no_data!!.visibility = View.VISIBLE
                                tv_total!!.text = "Total: 0"
                            } else {
                                tv_no_data!!.visibility = View.GONE
                                recylcer_down_member!!.visibility = View.VISIBLE

                            }
                        }
                        progressdialog!!.dismiss();


                    }
                })
    }
    //</editor-fold>

    //<editor-fold desc="Make Table">
    fun getMakeTableRight() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        list.clear()
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.getMakeTableRight("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<ArrayList<Users>> {
                    override fun onFailure(call: Call<ArrayList<Users>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss();

                    }

                    override fun onResponse(call: Call<ArrayList<Users>>?, response: retrofit2.Response<ArrayList<Users>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                list.add(user)
                                total += user.PaidAmount!!.toDouble()
                            }
                            adapter!!.notifyDataSetChanged()

                            if (list.size == 0) {
                                recylcer_down_member!!.visibility = View.GONE
                                tv_no_data!!.visibility = View.VISIBLE
                                tv_total!!.text = "Total: 0"
                            } else {
                                tv_no_data!!.visibility = View.GONE
                                recylcer_down_member!!.visibility = View.VISIBLE

                            }
                        }
                        progressdialog!!.hide();


                    }
                })
    }

    fun getMakeTableLeft() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        list.clear()
        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.getMakeTableLeft("bearer " + token!!, id!!)
                ?.enqueue(object : Callback<ArrayList<Users>> {
                    override fun onFailure(call: Call<ArrayList<Users>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.hide();

                    }

                    override fun onResponse(call: Call<ArrayList<Users>>?, response: retrofit2.Response<ArrayList<Users>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                list.add(user)
                                total += user.PaidAmount!!.toDouble()
                            }
                            adapter!!.notifyDataSetChanged()

                            if (list.size == 0) {
                                recylcer_down_member!!.visibility = View.GONE
                                tv_no_data!!.visibility = View.VISIBLE
                                tv_total!!.text = "Total: 0"
                            } else {
                                tv_no_data!!.visibility = View.GONE
                                recylcer_down_member!!.visibility = View.VISIBLE

                            }
                        }
                        progressdialog!!.hide();


                    }
                })
    }
    //</editor-fold>

    //token expire
    fun tokenExpire() {
        prefs.clearToken(activity!!)
        prefs.clearUser(activity!!)
        startActivity(Intent(activity!!, SignInActivity::class.java))
        activity!!.finish()

    }
}

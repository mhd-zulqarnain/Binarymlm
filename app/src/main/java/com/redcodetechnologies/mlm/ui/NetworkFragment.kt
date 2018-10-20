package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.app.Dialog
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
import com.company.redcode.royalcryptoexchange.utils.Apputils
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_network, container, false)
        initView(view)
        return view

    }

    private fun initView(view: View?) {
        list.add(Users("3", "ali", "234234", "123123123", "hbl", "200"))
        list.add(Users("3", "ahmed", "234234", "123123123", "hbl", "100"))
        list.add(Users("3", "basit", "234234", "123123123", "hbl", "300"))

        layout_add_right = view!!.findViewById(R.id.layout_add_right)
        layout_add_left = view!!.findViewById(R.id.layout_add_left)
        add_right = view!!.findViewById(R.id.add_right)
        add_left = view!!.findViewById(R.id.add_left)
        recylcer_down_member = view.findViewById(R.id.recylcer_down_member)
        search_view = view.findViewById(R.id.search_view)

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
            showDialog("left")
        }
        add_right!!.setOnClickListener {
            showDialog("right")
        }
//        search_view.set
        search_view!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                //FILTER AS YOU TYPE
                adapter!!.getFilter().filter(query)
                return false
            }
        })
    }


    private fun showDialog(type: String) {

        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_add_downline, null)
        val alertBox = AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        dialog = alertBox.create()
        var saveable = false;
        dialog!!.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        var button_ok: Button = view.findViewById(R.id.btn_ok)
        var button_cancel: Button = view.findViewById(R.id.btn_cancel)
        var btn_add_image: Button = view.findViewById(R.id.btn_add_image)

        dialog_title = view.findViewById(R.id.dialog_title)
        ed_name = view.findViewById(R.id.ed_name)
        ed_uname = view.findViewById(R.id.ed_uname)
        ed_pass = view.findViewById(R.id.ed_pass)
        ed_phone = view.findViewById(R.id.ed_phone)
        ed_address = view.findViewById(R.id.ed_address)
        ed_account = view.findViewById(R.id.ed_account)
        ed_bank_name = view.findViewById(R.id.ed_bank_name)
        ed_cnic = view.findViewById(R.id.ed_cnic)
        ed_email = view.findViewById(R.id.ed_email)

        ed_name!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {


                if (!ed_name!!.text.toString().trim().isEmpty() && ed_name!!.text.toString().trim() != "") {

                    var name = ed_name!!.text.toString().trim()
                    name = name.replace(" ", "");
                    val r = Random(System.currentTimeMillis())
                    var id = (1 + r.nextInt(2)) * 10000 + r.nextInt(10000)
                    name = name + id
                    var ran = UUID.randomUUID().toString()
                    var pas = ran.split("-")
                    ed_pass!!.setText(pas[0])
                    ed_uname!!.setText(name)


                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        if (type == "right") {
            dialog_title!!.setText("Add Right Member")
        } else
            dialog_title!!.setText("Add Left Member")

        button_ok.setOnClickListener {

            dialogValidation()
        }
        button_cancel.setOnClickListener {
            dialog!!.dismiss()
        }

        btn_add_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            activity!!.startActivityForResult(intent, REQUSET_GALLERY_CODE)
        }

        dialog!!.show()

    }

    private fun dialogValidation() {
        if (ed_name!!.text.toString().trim() == "") {
            ed_name!!.error = Html.fromHtml("<font color='white'>Enter user name</font>")
            ed_name!!.requestFocus()
            return
        }

        if (!Apputils.isValidEmail(ed_email!!.text.toString())) {
            ed_email!!.error = Html.fromHtml("<font color='#E0796C'>Please enter correct Email Address</font>")
            ed_email!!.requestFocus()
            return

        }

        if (ed_phone!!.text.toString().trim() == "" || ed_phone!!.text.toString().trim { it <= ' ' }.length < 11) {
            ed_phone!!.error = Html.fromHtml("<font color='white'>Invalid entry</font>")
            ed_phone!!.requestFocus()
            return

        }

        if (ed_address!!.text.toString().trim() == "") {
            ed_address!!.error = Html.fromHtml("<font color='white'>This field could not be empty</font>")
            ed_address!!.requestFocus()
            return

        }

        if (ed_account!!.text.toString().trim() == "" || ed_account!!.text.toString().trim { it <= ' ' }.length < 18) {
            ed_account!!.error = Html.fromHtml("<font color='white'>Invalid entry</font>")
            ed_account!!.requestFocus()
            return

        }

        if (ed_bank_name!!.text.toString().trim() == "") {
            ed_bank_name!!.error = Html.fromHtml("<font color='white'>Please Enter bank name!</font>")
            ed_bank_name!!.requestFocus()
            return

        }

        if (ed_bank_name!!.text.toString().trim() == "") {
            ed_bank_name!!.error = Html.fromHtml("<font color='white'>Please Enter bank name!</font>")
            ed_bank_name!!.requestFocus()
            return

        }

        if (ed_cnic!!.text.toString().trim() == "" || ed_cnic!!.text.toString().trim { it <= ' ' }.length < 12) {
            ed_cnic!!.error = Html.fromHtml("<font color='white'>Invalid entry</font>")
            ed_cnic!!.requestFocus()
            return

        }
        dialog!!.dismiss()
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Make Table")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }

}

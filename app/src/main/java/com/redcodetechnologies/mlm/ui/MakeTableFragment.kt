package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.models.Users
import java.util.*






class MakeTableFragment : Fragment() {
    var recylcer_down_member: RecyclerView? = null
    var adapter:DownMemberAdapter? = null
    var layout_add_right:LinearLayout? = null
    var layout_add_left:LinearLayout? = null
    var add_left:ImageView? = null
    var add_right:ImageView? = null
    var list:ArrayList<Users>  = ArrayList()
    val REQUSET_GALLERY_CODE:Int =43
    val ALLOWED_CHARACTERS = "0123456789qwertyuiopaFThjCVvbnm"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_make_table, container, false)
        initView(view)
        return view

    }

    private fun initView(view: View?) {
        list.add(Users("3","ali","234234","123123123","hbl","200"))
        list.add(Users("3","ahmed","234234","123123123","hbl","100"))
        list.add(Users("3","basit","234234","123123123","hbl","300"))

        layout_add_right = view!!.findViewById(R.id.layout_add_right)
        layout_add_left = view!!.findViewById(R.id.layout_add_left)
        add_right = view!!.findViewById(R.id.add_right)
        add_left = view!!.findViewById(R.id.add_left)
        recylcer_down_member = view!!.findViewById(R.id.recylcer_down_member)

        recylcer_down_member!!.layoutManager = LinearLayoutManager(activity!!,LinearLayout.VERTICAL,false)
        adapter = DownMemberAdapter(activity!!,list)
        recylcer_down_member!!.adapter = adapter
        layout_add_left!!.setOnClickListener{
            layout_add_right!!.setBackgroundResource(R.color.colorGray);
            layout_add_left!!.setBackgroundResource(R.color.colorRed);
        }
        layout_add_right!!.setOnClickListener{
            layout_add_left!!.setBackgroundResource(R.color.colorGray);
            layout_add_right!!.setBackgroundResource(R.color.colorRed);
        }

        add_left!!.setOnClickListener{
            showDialog("left")
        }
        add_right!!.setOnClickListener{
            showDialog("right")
        }
    }


    private fun showDialog(type:String) {
        val view: View = LayoutInflater.from(activity!!!!).inflate(R.layout.dialog_add_downline, null)
        val alertBox = AlertDialog.Builder(activity!!!!)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        var button_ok: Button = view.findViewById(R.id.btn_ok)
        var button_cancel: Button = view.findViewById(R.id.btn_cancel)
        var btn_add_image: Button = view.findViewById(R.id.btn_add_image)

        var dialog_title: TextView = view.findViewById(R.id.dialog_title)
        var ed_name: EditText = view.findViewById(R.id.ed_name)
        var ed_uname: EditText = view.findViewById(R.id.ed_uname)
        var ed_pass: EditText = view.findViewById(R.id.ed_pass)

        ed_name!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {


                if (!ed_name!!.text.toString().trim().isEmpty() && ed_name!!.text.toString().trim() != "") {

                    var name = ed_name.text.toString().trim()
                    name = name.replace(" ", "");
                    val r = Random(System.currentTimeMillis())
                    var id= (1 + r.nextInt(2)) * 10000 + r.nextInt(10000)
                    name = name+id
                    var ran =UUID.randomUUID().toString()
                    var pas = ran.split("-")
                    ed_pass.setText(pas[0])
                    ed_uname.setText(name)


                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        if(type=="right"){
            dialog_title.setText("Add Right Member")
        }else
            dialog_title.setText("Add Left Member")

        button_ok.setOnClickListener {

                dialog.cancel()

        }
        button_cancel.setOnClickListener{
            dialog.dismiss()
        }

        btn_add_image.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            activity!!.startActivityForResult(intent, REQUSET_GALLERY_CODE)
        }

        dialog.show()

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Make Table")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data "+data.data)
        }
    }

}

package com.redcodetechnologies.mlm

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.company.redcode.royalcryptoexchange.utils.Apputils
import kotlinx.android.synthetic.main.activity_add_member.*
import java.util.*

class AddMemberActivity : AppCompatActivity() {
    val REQUSET_GALLERY_CODE: Int = 43
    var type: String = "right"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_member)
        var toolbar : Toolbar = findViewById(R.id.toolbar_top)

        type = intent.getStringExtra("type")
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

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        if (type == "right") {
            dialog_title!!.setText("Add Right Member")
        } else
            dialog_title!!.setText("Add Left Member")
        btn_add_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUSET_GALLERY_CODE)
        }

        spinner_package.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                layout_package.visibility = View.GONE
            }
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
            }
        })
        btn_back.setOnClickListener {
            finish()
        }

        btn_ok.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }
}

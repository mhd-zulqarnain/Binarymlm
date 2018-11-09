package com.redcodetechnologies.mlm.ui.network

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Packages
import com.redcodetechnologies.mlm.models.users.DropDownMembers
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.network.adapter.DownlinerSpinnerAdapter
import com.redcodetechnologies.mlm.ui.network.adapter.PackageSpinnerAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add_member.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import kotlin.collections.ArrayList

class AddMemberActivity : AppCompatActivity() {
    val REQUSET_GALLERY_CODE: Int = 43
    lateinit var type: String;
    var spinner_country: SearchableSpinner? = null
    var listdownliner: ArrayList<DropDownMembers> = ArrayList()
    var listPackages: ArrayList<Packages> = ArrayList()

    lateinit var downlinerAdapter: DownlinerSpinnerAdapter;
    lateinit var packageAdapter: PackageSpinnerAdapter;
    var id: Int? = null
    lateinit var prefs: SharedPrefs
    var progressdialog: android.app.AlertDialog? = null
    lateinit var token: String

    var userdocumentImage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        var toolbar: Toolbar = findViewById(R.id.toolbar_top)
        spinner_country = findViewById(R.id.spinner_country)
        type = intent.getStringExtra("type")

        initView()

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

    fun initView() {
        progressdialog = SpotsDialog.Builder()
                .setContext(this@AddMemberActivity)
                .setMessage("Loading please wait!!")
                .setTheme(R.style.CustomProgess)
                .build()

        prefs = SharedPrefs.getInstance()!!
        token = prefs.getToken(this@AddMemberActivity).accessToken!!
        id = prefs.getUser(this@AddMemberActivity).userId!!

        var arrayAdapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.support_simple_spinner_dropdown_item)
        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");

        spinner_country!!.setSelection(166)
        spinner_country!!?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }

        downlinerAdapter = DownlinerSpinnerAdapter(this@AddMemberActivity, listdownliner)
        spinner_downliner.adapter = downlinerAdapter;
        getdownliner()

        packageAdapter = PackageSpinnerAdapter(this@AddMemberActivity, listPackages)
        spinner_package.adapter = packageAdapter;
        getPackages()

        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");
    }

    private fun getPackages() {
        listPackages.add(Packages("1", "--select--"))
        ApiClint.getInstance()?.getService()?.getpackages()
                ?.enqueue(object : Callback<java.util.ArrayList<Packages>> {
                    override fun onFailure(call: Call<java.util.ArrayList<Packages>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.hide();
                    }

                    override fun onResponse(call: Call<java.util.ArrayList<Packages>>?, response: retrofit2.Response<java.util.ArrayList<Packages>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(this@AddMemberActivity, "Token Expired")
                            tokenExpire();
                        }
                        if (code == 200) {
                            response?.body()?.forEach { user ->
                                listPackages.add(user)
                            }
                            downlinerAdapter.notifyDataSetChanged()
                            if (response.body()!!.size == 0) {
                                //nnnn   listdownliner.add(DropDownMembers(0,"None"))

                            }
                        }

                        progressdialog!!.hide();


                    }
                })
    }

    private fun getdownliner() {

        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(this@AddMemberActivity, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show();
        listdownliner.clear()
        listdownliner.add(DropDownMembers(0, "--select--"))

        if (type == "right") {
            ApiClint.getInstance()?.getService()?.getdropDownMembersRight("bearer " + token!!, id!!)
                    ?.enqueue(object : Callback<java.util.ArrayList<DropDownMembers>> {
                        override fun onFailure(call: Call<java.util.ArrayList<DropDownMembers>>?, t: Throwable?) {
                            println("error")
                            progressdialog!!.hide();

                        }

                        override fun onResponse(call: Call<java.util.ArrayList<DropDownMembers>>?, response: retrofit2.Response<java.util.ArrayList<DropDownMembers>>?) {
                            print("object success ")
                            var code: Int = response!!.code()

                            if (code == 401) {
                                Apputils.showMsg(this@AddMemberActivity, "Token Expired")
                                tokenExpire();
                            }
                            if (code == 200) {
                                response?.body()?.forEach { user ->
                                    listdownliner.add(user)
                                }
                                downlinerAdapter.notifyDataSetChanged()
                                if (response.body()!!.size == 0) {
                                    //  listdownliner.add(DropDownMembers(0,"--select--"))

                                }
                            }

                            progressdialog!!.hide();


                        }
                    })

        } else {
            //getDownLineLeft
            ApiClint.getInstance()?.getService()?.getdropDownMembersLeft("bearer " + token!!, id!!)
                    ?.enqueue(object : Callback<java.util.ArrayList<DropDownMembers>> {
                        override fun onFailure(call: Call<java.util.ArrayList<DropDownMembers>>?, t: Throwable?) {
                            println("error")
                            progressdialog!!.hide();
                        }

                        override fun onResponse(call: Call<java.util.ArrayList<DropDownMembers>>?, response: retrofit2.Response<java.util.ArrayList<DropDownMembers>>?) {
                            print("object success ")
                            var code: Int = response!!.code()

                            if (code == 401) {
                                Apputils.showMsg(this@AddMemberActivity, "Token Expired")
                                tokenExpire();
                            }
                            if (code == 200) {
                                response?.body()?.forEach { user ->
                                    listdownliner.add(user)
                                }
                                downlinerAdapter.notifyDataSetChanged()
                                if (response.body()!!.size == 0) {
                                    //nnnn   listdownliner.add(DropDownMembers(0,"None"))

                                }
                            }

                            progressdialog!!.hide();


                        }
                    })
        }
    }

    fun tokenExpire() {
        prefs.clearToken(this@AddMemberActivity)
        prefs.clearUser(this@AddMemberActivity)
        startActivity(Intent(this@AddMemberActivity, SignInActivity::class.java))
        finish()
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
        if (btn_add_image!!.text.toString().trim() == "") {
            btn_add_image!!.error = Html.fromHtml("<font color='white'>Please Upload document image!</font>")
            btn_add_image!!.requestFocus()
            return
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
            val imageUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this@AddMemberActivity.getContentResolver(), imageUri)
            userdocumentImage= Apputils.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG,100)

        }
    }


}

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
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Packages
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.users.DropDownMembers
import com.redcodetechnologies.mlm.models.users.UserTree
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class AddMemberActivity : AppCompatActivity() {
    val REQUSET_GALLERY_CODE: Int = 43
    lateinit var type: String;
    var spinner_country: SearchableSpinner? = null
    var spinner_package: Spinner? = null
    var spinner_downliner: Spinner? = null
    var listdownliner: ArrayList<DropDownMembers> = ArrayList()
    var listPackages: ArrayList<Packages> = ArrayList()
    var userModel: UserTree = UserTree()
    var downlinerAdapter: DownlinerSpinnerAdapter?=null;
    var packageAdapter: PackageSpinnerAdapter?=null;

    var id: Int? = null
    lateinit var prefs: SharedPrefs
    var progressdialog: android.app.AlertDialog? = null

    lateinit var token: String

    var userdocumentImage: String? = null
    var package_price: String? = null
    var userPackage: String? = null
    var downlineMemberId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        var toolbar: Toolbar = findViewById(R.id.toolbar_top)
        spinner_country = findViewById(R.id.spinner_country)
        spinner_downliner = findViewById(R.id.spinner_downliner)
        spinner_package = findViewById(R.id.spinner_package)

        if (intent.getStringExtra("type") == null) {
            type = "right"
        } else
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
        spinner_package!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
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

        getdownliner()
        getPackages()
    }

    //<editor-fold desc="Spinner package and downliner">
    private fun getPackages() {
        listPackages.add(Packages("1", "--select--"))

        ApiClint.getInstance()?.getService()?.getpackages()
                ?.enqueue(object : Callback<java.util.ArrayList<Packages>> {
                    override fun onFailure(call: Call<java.util.ArrayList<Packages>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss();
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
                            if (response.body()!!.size == 0) {
                                //nnnn   listdownliner.add(DropDownMembers(0,"None"))

                            }
                        }
                        setpackagepinner()
                        progressdialog!!.dismiss();


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
                            progressdialog!!.dismiss();

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

                                if (response.body()!!.size == 0) {
                                    //  listdownliner.add(DropDownMembers(0,"--select--"))

                                }
                            }
                            progressdialog!!.dismiss();
                            setdownlinerspinner()


                        }
                    })

        } else {
            //getDownLineLeft
            ApiClint.getInstance()?.getService()?.getdropDownMembersLeft("bearer " + token!!, id!!)
                    ?.enqueue(object : Callback<java.util.ArrayList<DropDownMembers>> {
                        override fun onFailure(call: Call<java.util.ArrayList<DropDownMembers>>?, t: Throwable?) {
                            println("error")
                            progressdialog!!.dismiss();
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
                                if (response.body()!!.size == 0) {
                                    //nnnn   listdownliner.add(DropDownMembers(0,"None"))

                                }
                            }

                            progressdialog!!.dismiss();

                            setdownlinerspinner()
                        }
                    })
        }
    }
    fun setdownlinerspinner() {

        spinner_downliner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (pos != 0) {
                    var obj: DropDownMembers = spinner_downliner!!.getSelectedItem() as DropDownMembers
                    downlineMemberId = obj.UserId
                }else{
                    downlineMemberId=null
                }
            }
        })
        downlinerAdapter = DownlinerSpinnerAdapter(this@AddMemberActivity, listdownliner)
        spinner_downliner!!.adapter = downlinerAdapter;
    }
    fun setpackagepinner() {

        packageAdapter = PackageSpinnerAdapter(this@AddMemberActivity, listPackages)
        spinner_package!!.adapter = packageAdapter;

        spinner_package!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (pos != 0) {
                    var obj: Packages = spinner_package!!.getSelectedItem() as Packages
                    package_price = obj.PackagePrice
                    userPackage = obj.PackageId
                }else{
                    package_price = null
                    userPackage = null
                }
            }
        })

    }
    //</editor-fold>

    fun tokenExpire() {
        prefs.clearToken(this@AddMemberActivity)
        prefs.clearUser(this@AddMemberActivity)
        startActivity(Intent(this@AddMemberActivity, SignInActivity::class.java))
        finish()
    }

    private fun validation() {
        if (ed_name.text.toString().trim() == "") {
            ed_name.error = Html.fromHtml("<font color='white'>Enter user name</font>")
            ed_name.requestFocus()
            return
        }

        if (!Apputils.isValidEmail(ed_email!!.text.toString())) {
            ed_email.error = Html.fromHtml("<font color='#E0796C'>Please enter correct Email Address</font>")
            ed_email.requestFocus()
            return
        }

        if (ed_phone.text.toString().trim() == "" || ed_phone!!.text.toString().trim { it <= ' ' }.length < 11) {
            ed_phone.error = Html.fromHtml("<font color='white'>Invalid entry</font>")
            ed_phone.requestFocus()
            return
        }

        if (userdocumentImage==null) {
            btn_add_image.error = Html.fromHtml("<font color='white'>Please Upload document image!</font>")
            btn_add_image.requestFocus()
            return
        }

        if (package_price == null || userPackage == null) {
            Apputils.showMsg(this@AddMemberActivity, "Please select package")
            return
        }

        if (downlineMemberId == null) {
            if (listdownliner.size != 1) {
                Apputils.showMsg(this@AddMemberActivity, "Please select downliner")
                return
            } else
                downlineMemberId = 1////memeber id spinner

        }

        var countryIndex = 0;
        if(spinner_country!!.getSelectedItemPosition()!=0){
            countryIndex =spinner_country!!.getSelectedItemPosition() - 1
        }
        userModel.Name = ed_name.text.toString()
        userModel.Username = ed_uname.text.toString()
        userModel.Password = ed_pass.text.toString()
        userModel.Country = countryIndex
        userModel.Address = ""
        userModel.Phone = ed_phone.text.toString()
        userModel.Email = ed_email.text.toString()
        userModel.AccountNumber = ""
        userModel.Phone = ed_phone.text.toString()
        userModel.DownlineMemberId = downlineMemberId
        userModel.PaidAmount = package_price///package price
        userModel.UserPackage = userPackage //from spinner
        userModel.DocumentImage = userdocumentImage //from spinner


        if (type == "right") {
            addRightMember()
        } else {
            addLeftMember()
        }

    }

    private fun addLeftMember() {
        var token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.addLeftMember("bearer " + token!!, id!!, userModel)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                    }
                })
    }

    private fun addRightMember() {
        var token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.addRightMember("bearer " + token!!, id!!, userModel)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
            val imageUri = data.data
            if (data.data != null) {
                val f = File(imageUri.getPath())
                val imageName = f.getPath().split(":")[1]
                btn_add_image.setText(imageName)
                userdocumentImage = imageTostring(MediaStore.Images.Media.getBitmap(baseContext.getContentResolver(), data.data))
            }


        }
    }

    private fun imageTostring(bitmap: Bitmap): String {
        val outStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream)
        val imageBytes = outStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

}

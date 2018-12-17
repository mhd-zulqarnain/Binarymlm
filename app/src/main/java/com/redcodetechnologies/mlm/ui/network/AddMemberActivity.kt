package com.redcodetechnologies.mlm.ui.network

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
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
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class AddMemberActivity : AppCompatActivity() {
    val REQUSET_GALLERY_CODE: Int = 43
    lateinit var type: String;
    var spinner_country: SearchableSpinner? = null
    var spinner_downliner: Spinner? = null
    var listdownliner: ArrayList<DropDownMembers> = ArrayList()
    var listPackages: ArrayList<Packages> = ArrayList()
    var userModel: UserTree = UserTree()
    var downlinerAdapter: DownlinerSpinnerAdapter? = null;
    var packageAdapter: PackageSpinnerAdapter? = null;

    var id: Int? = null
    lateinit var prefs: SharedPrefs
    var progressdialog: android.app.AlertDialog? = null

    lateinit var token: String

    var userdocumentImage: String? = null
    var downlineMemberId: Int? = null
    var downlineMemberName = "none"

    var isVerified = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        prefs = SharedPrefs.getInstance()!!
        if (prefs.getUser(this@AddMemberActivity).userId != null) {
            token = prefs.getToken(this@AddMemberActivity).accessToken!!
            id = prefs.getUser(this@AddMemberActivity).userId!!
        }


        if (intent.getStringExtra("type") == null) {
            type = "right"
        } else
            type = intent.getStringExtra("type")

        val isNewRequest = prefs.getUser(this@AddMemberActivity).isNewRequest!!

        if (isNewRequest)
            alert_text.setText("Account is not active")

        initView()
    }

    fun initView() {

        spinner_country = findViewById(R.id.spinner_country)
        spinner_downliner = findViewById(R.id.spinner_downliner)

        progressdialog = SpotsDialog.Builder()
                .setContext(this@AddMemberActivity)
                .setMessage("Loading please wait!!")
                .setTheme(R.style.CustomProgess)
                .build()

        var arrayAdapter = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.support_simple_spinner_dropdown_item)
        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");
        spinner_country!!.setSelection(166)

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
            checkIfNewMemeberRight(id!!)
        } else {
            dialog_title!!.setText("Add Left Member")
            checkIfNewMemeberLeft(id!!)

        }
        btn_add_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUSET_GALLERY_CODE)
        }

        btn_back.setOnClickListener {
            finish()
        }
        val isNewRequest = prefs.getUser(this@AddMemberActivity).isNewRequest!!

        btn_ok.setOnClickListener {
            if (isNewRequest) {
                Apputils.showMsg(this@AddMemberActivity, "Account is not active")
            } else
                validation()
        }


        ed_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 1 && s.toString().startsWith("0")) {
                    s!!.clear();
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
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
                        //                        setpackagepinner()
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
        listdownliner.add(DropDownMembers(0, "--select Downliner--"))

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
            ApiClint.getInstance()?.getService()?.getdropDownMembersLeft("bearer " + token, id!!)
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
                    downlineMemberName = obj.Username!!
                    if (isVerified)
                        if (type == "right") {
                            checkIfNewMemeberRightChild(downlineMemberId!!)
                        } else {
                            checkIfNewMemeberLeftChild(downlineMemberId!!)
                        }
                } else {
                    downlineMemberId = null
                    downlineMemberName = "none"
                }
            }
        })
        downlinerAdapter = DownlinerSpinnerAdapter(this@AddMemberActivity, listdownliner)
        spinner_downliner!!.adapter = downlinerAdapter;
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

        if (ed_phone.text.toString().trim() == "" || ed_phone!!.text.toString().trim { it <= ' ' }.length < 10) {
            ed_phone.error = Html.fromHtml("<font color='white'>Invalid mobile number</font>")
            ed_phone.requestFocus()
            return
        }

        if (userdocumentImage == null) {
            btn_add_image.error = Html.fromHtml("<font color='white'>Please Upload document image!</font>")
            btn_add_image.requestFocus()
            return
        }

        /*if (package_price == null || userPackage == null) {
            Apputils.showMsg(this@AddMemberActivity, "Please select package")
            return
        }*/

        if (downlineMemberId == null) {
            if (listdownliner.size > 2) {
                Apputils.showMsg(this@AddMemberActivity, "Please select downliner")
                return
            } else
                downlineMemberId = null////memeber id spinner

        }

        var countryIndex = 0;

        countryIndex = spinner_country!!.getSelectedItemPosition() + 1

        var mobile = "+92" + ed_phone.text.toString()
        userModel.name = ed_name.text.toString()
        userModel.username = ed_uname.text.toString()
        userModel.password = ed_pass.text.toString()
        userModel.country = countryIndex
        userModel.address = ""
        userModel.phone = mobile
        userModel.email = ed_email.text.toString()
        userModel.accountNumber = ""
        userModel.downlineMemberId = if (downlineMemberId == null) null else downlineMemberId.toString()
        userModel.documentImage = userdocumentImage!! //from spinner

        confirmationDialog()

    }

    //<editor-fold desc="Adding memeber functions">
    private fun addLeftMember() {
        val token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
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
                        val code: Int = response!!.code()
                        val status = response.body()!!.success
                        val msg = response.body()!!.message
                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                        progressdialog!!.dismiss()
                        if (status!!) {
                            finish()
                        }

                        Apputils.showMsg(this@AddMemberActivity, msg!!)
                    }
                })
    }

    private fun addRightMember() {
        val token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
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
                        var status = response.body()!!.success
                        var msg = response.body()!!.message
                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                        progressdialog!!.dismiss()
                        if (status!!) {
                            finish()
                        }

                        Apputils.showMsg(this@AddMemberActivity, msg!!)

                    }
                })
    }
    //</editor-fold>

    //<editor-fold desc="Check if downline is eligible">
    private fun checkIfNewMemeberRightChild(userId: Int) {
        var token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog = SpotsDialog.Builder()
                .setContext(this@AddMemberActivity)
                .setMessage("Validating")
                .setTheme(R.style.CustomProgess)
                .build()

        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.checkIfNewMemeberRightChild(userId)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        val obj: Response = response.body()!!
                        var msg = obj.message
                        var status = obj.success

                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                        progressdialog!!.dismiss()
                        if (status!!) {
                            alert_text.visibility = View.GONE
                            btn_ok.isEnabled = true

                        } else {
                            btn_ok.isEnabled = false
                            alert_text.visibility = View.VISIBLE
                            val mmsg = "Already exists on $msg of this person"
                            alert_text.text = "($mmsg)"
                            Apputils.showMsg(this@AddMemberActivity, mmsg!!)

                        }


                    }
                })
    }

    private fun checkIfNewMemeberLeftChild(userId: Int) {
        var token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog = SpotsDialog.Builder()
                .setContext(this@AddMemberActivity)
                .setMessage("Validating")
                .setTheme(R.style.CustomProgess)
                .build()

        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.checkIfNewMemeberLeftChild(userId)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        val obj: Response = response.body()!!
                        var msg = obj.message
                        var status = obj.success

                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                        progressdialog!!.dismiss()
                        if (status!!) {
                            alert_text.visibility = View.GONE
                            btn_ok.isEnabled = true

                        } else {
                            alert_text.visibility = View.VISIBLE
                            btn_ok.isEnabled = false
                            val mmsg = "Already exists on $msg of this person"
                            alert_text.text = "($mmsg)"
                            Apputils.showMsg(this@AddMemberActivity, mmsg!!)

                        }


                    }
                })
    }
    //</editor-fold>

    private fun checkIfNewMemeberRight(userId: Int) {
        var token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog = SpotsDialog.Builder()
                .setContext(this@AddMemberActivity)
                .setMessage("Validating")
                .setTheme(R.style.CustomProgess)
                .build()

        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.checkIfNewMemeberRight(userId)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        val obj: Response = response.body()!!
                        var msg = obj.message
                        var status = obj.success

                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }
                        progressdialog!!.dismiss()
                        if (status!!) {

                            alert_text.visibility = View.GONE
                            btn_ok.isEnabled = true

                        } else {
                            isVerified = false
                            btn_ok.isEnabled = false
                            alert_text.visibility = View.VISIBLE
                            val mmsg = "Account not verfied"
                            alert_text.text = "($mmsg)"
                            Apputils.showMsg(this@AddMemberActivity, mmsg!!)
                        }

                    }
                })
    }

    private fun checkIfNewMemeberLeft(userId: Int) {
        var token = SharedPrefs.getInstance()!!.getToken(this@AddMemberActivity).accessToken
        if (!Apputils.isNetworkAvailable(this@AddMemberActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog = SpotsDialog.Builder()
                .setContext(this@AddMemberActivity)
                .setMessage("Validating")
                .setTheme(R.style.CustomProgess)
                .build()

        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.checkIfNewMemeberLeft(userId)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        val obj: Response = response.body()!!
                        var msg = obj.message
                        var status = obj.success

                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                        progressdialog!!.dismiss()
                        if (status!!) {
                            alert_text.visibility = View.GONE
                            btn_ok.isEnabled = true

                        } else {
                            isVerified = false
                            btn_ok.isEnabled = false
                            alert_text.visibility = View.VISIBLE

                            val mmsg = "Account not verfied"
                            alert_text.text = "($mmsg)"
                            Apputils.showMsg(this@AddMemberActivity, mmsg!!)
                        }

                    }
                })
    }

    private fun confirmationDialog() {
        val view: View = LayoutInflater.from(this@AddMemberActivity).inflate(R.layout.add_member_confirmation_dialog, null)
        val alertBox = AlertDialog.Builder(this@AddMemberActivity)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()

        val btn_confirm = view.findViewById<Button>(R.id.btn_confirm)
        val btn_cancel = view.findViewById<Button>(R.id.btn_cancel)
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_uname = view.findViewById<TextView>(R.id.tv_uname)
        val tv_email = view.findViewById<TextView>(R.id.tv_email)
        val tv_mbl = view.findViewById<TextView>(R.id.tv_mbl)
        val tv_downliner = view.findViewById<TextView>(R.id.tv_downliner)

        tv_name.setText(userModel.name.toString())
        tv_uname.setText(userModel.username.toString())
        tv_email.setText(userModel.email.toString())
        tv_mbl.setText(userModel.phone.toString())
        tv_downliner.setText(downlineMemberName)

        btn_confirm.setOnClickListener {
            if (type == "right") {
                addRightMember()
            } else {
                addLeftMember()
            }
            dialog.dismiss()

        }
        btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

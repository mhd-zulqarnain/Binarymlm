package com.redcodetechnologies.mlm.ui.network


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Packages
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.network.adapter.PackageAdapter
import com.redcodetechnologies.mlm.ui.network.adapter.PackageSpinnerAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.util.ArrayList

class UpgradePackageFragment : Fragment() {

    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String
    var total: Double = 0.0
    var recylcer_pkg_list: RecyclerView? = null

    lateinit var transaction_options: RadioGroup;
    lateinit var wallet_radio: RadioButton;
    lateinit var bank_radio: RadioButton;
    lateinit var bank_addition: TextView;
    lateinit var spinner_package: Spinner;
    lateinit var pkg_percent: TextView;
    lateinit var pkg_price: TextView;
    lateinit var pkg_validity: TextView;
    lateinit var pkg_wallet: TextView;
    lateinit var btn_save: Button;
    lateinit var des_bank_slip: TextView;
    lateinit var view_bank_script: LinearLayout;
    val SELECT_SUPPORT_PHOTO: Int = 32

    var listPackages: ArrayList<Packages> = ArrayList()
    var listPackageItem: ArrayList<Packages> = ArrayList()
    var packageAdapter: PackageSpinnerAdapter? = null;
    var packageListAdapter: PackageAdapter? = null;
    var progressdialog: android.app.AlertDialog? = null

    //---
    var isBank = true
    var packageId = 10
    var bankslipImage = ""
    val BankAccount = "Bank Account"
    val EWalletBalance = "E-Wallet Balance"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        prefs = SharedPrefs.getInstance()!!
        var view = inflater.inflate(R.layout.fragment_updgrade_package, container, false)

        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId
            token = prefs.getToken(activity!!).accessToken!!
        }
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Upgrade Package")

        initView(view)
        return view
    }

    private fun initView(view: View) {
        progressdialog = SpotsDialog.Builder()
                .setContext(activity!!)
                .setMessage("Loading please wait!!")
                .setTheme(R.style.CustomProgess)
                .build()

        transaction_options = view.findViewById(R.id.transaction_options)
        bank_addition = view.findViewById(R.id.bank_addition)
        bank_radio = view.findViewById(R.id.bank_radio)
        wallet_radio = view.findViewById(R.id.wallet_radio)
        bank_addition = view.findViewById(R.id.bank_addition)
        spinner_package = view.findViewById(R.id.spinner_package)
        btn_save = view.findViewById(R.id.btn_save)
        recylcer_pkg_list = view.findViewById(R.id.recylcer_pkg_list)

        packageListAdapter = PackageAdapter(activity!!, listPackageItem)
        recylcer_pkg_list!!.layoutManager = LinearLayoutManagerWrapper(activity!!, LinearLayout.VERTICAL, false)
        recylcer_pkg_list!!.adapter = packageListAdapter;

        pkg_percent = view.findViewById(R.id.pkg_percent)
        pkg_price = view.findViewById(R.id.pkg_price)
        pkg_validity = view.findViewById(R.id.pkg_validity)
        pkg_wallet = view.findViewById(R.id.pkg_wallet)
        des_bank_slip = view.findViewById(R.id.des_bank_slip)
        view_bank_script = view.findViewById(R.id.view_bank_script)

        setSpinnerPackages()
        getPackages()
        getusercurrentpackageslist()

        btn_save.setOnClickListener {
            ewalletupgradeinvestment()
        }
        bank_addition.setOnClickListener {
            pickImage(SELECT_SUPPORT_PHOTO)
        }
        transaction_options.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkId ->
                    if (wallet_radio.isChecked) {
                        view_bank_script.visibility = View.GONE
                        isBank = false
                    }
                    if (bank_radio.isChecked) {
                        view_bank_script.visibility = View.VISIBLE
                        isBank = true

                    }
                })

    }

    fun setSpinnerPackages() {
        spinner_package.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val obj: Packages = spinner_package.getSelectedItem() as Packages
                pkg_percent.text = obj.PackagePercent + "%"
                pkg_price.text = obj.PackagePrice!!.split(".")[0] + " PKR"
                pkg_validity.text = obj.PackageValidity
                pkg_wallet.text = obj.PackageMaxWithdrawalAmount!!.split(".")[0]
                packageId = obj.PackageId!!.toInt()
            }
        })
        packageAdapter = PackageSpinnerAdapter(activity, listPackages)
        spinner_package.adapter = packageAdapter;
    }

    private fun getPackages() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClint.getInstance()?.getService()?.getpackages()
                ?.enqueue(object : Callback<java.util.ArrayList<Packages>> {
                    override fun onFailure(call: Call<java.util.ArrayList<Packages>>?, t: Throwable?) {
                        println("error")
                    }

                    override fun onResponse(call: Call<java.util.ArrayList<Packages>>?, response: retrofit2.Response<java.util.ArrayList<Packages>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 200) {
                            response?.body()?.forEach { pkg ->
                                listPackages.add(pkg)
                            }
                            if (response.body()!!.size == 0) {
                            }
                        }
                        packageAdapter!!.notifyDataSetChanged()


                    }
                })
    }

    private fun ewalletupgradeinvestment() {


        if (isBank) {
            if (bankslipImage == "") {
                Apputils.showMsg(activity!!, "Please Upload bank Script")
                return
            }
        }

        val packages = Packages()
        packages.PackageId = packageId.toString()
        packages.PackagePurchaseMethod = if (isBank == true) BankAccount else EWalletBalance
        packages.BankSlipImage = bankslipImage

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.ewalletupgradeinvestment(id!!, packages)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            Toast.makeText(activity!!, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }

                        if (code != 200) {
                            Toast.makeText(activity!!, " Failed ", Toast.LENGTH_SHORT).show()

                        }

                    }
                })
    }


    private fun getusercurrentpackageslist() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()

        ApiClint.getInstance()?.getService()?.getusercurrentpackageslist(id!!)
                ?.enqueue(object : Callback<java.util.ArrayList<Packages>> {
                    override fun onFailure(call: Call<java.util.ArrayList<Packages>>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss();
                    }

                    override fun onResponse(call: Call<java.util.ArrayList<Packages>>?, response: retrofit2.Response<java.util.ArrayList<Packages>>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 200) {
                            response?.body()?.forEach { pkg ->
                                listPackageItem.add(pkg)
                            }
                            if (response.body()!!.size == 0) {
                            }
                        }
                        packageListAdapter!!.notifyDataSetChanged()
                        progressdialog!!.dismiss();


                    }
                })
    }

    //<editor-fold desc="support image handling">
    private fun imageTostring(bitmap: Bitmap): String {
        val outStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream)
        val imageBytes = outStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    fun pickImage(code: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, code)
    }
    //</editor-fold>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            var filename = "No Image found"

            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.getContentResolver(), imageUri);
            try {
                val arr = getRealPathFromURI(activity!!, imageUri).split("/")
                filename = arr[arr.size - 1]
            } catch (e: Exception) {
            }

            des_bank_slip!!.setText(filename)
            bankslipImage = imageTostring(bitmap)


        }

    }


}

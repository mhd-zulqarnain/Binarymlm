package com.redcodetechnologies.mlm.ui.network.downliners


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Packages
import com.redcodetechnologies.mlm.models.users.DropDownMembers
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.network.adapter.DownlinerAdapter
import com.redcodetechnologies.mlm.ui.network.adapter.DownlinerSpinnerAdapter
import com.redcodetechnologies.mlm.ui.network.adapter.PackageSpinnerAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList

class UpgradePackageFragment : Fragment() {

    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String
    var total: Double = 0.0
    var recylcer_down: RecyclerView? = null

    lateinit var  transaction_options:RadioGroup;
    lateinit var  wallet_radio:RadioButton;
    lateinit var  bank_radio: RadioButton;
    lateinit var  bank_addition: LinearLayout;
    lateinit var  spinner_package: Spinner;
    var listPackages: ArrayList<Packages> = ArrayList()


    var packageAdapter: PackageSpinnerAdapter? = null;
    var progressdialog: android.app.AlertDialog? = null


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
        setSpinnerPackages()
        getPackages()
        transaction_options!!.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkId ->
                    if (wallet_radio!!.isChecked ) {
                     /*   transactionList.clear()
                        if(overAllDisposable!=null)
                            overAllDisposable!!.dispose()
                        if(frgement_type=="wallet_transactions"){
                            getThisMonthTransactionList()
                        }else if(frgement_type=="wallet_credits"){
                            getThisMonthEWalletCreditList()
                        }else if(frgement_type=="wallet_debits"){
                            getThisMonthEWalletDebitList()
                        }
*/
                        bank_addition.visibility=View.GONE
                    }
                    if (bank_radio!!.isChecked) {
                        bank_addition.visibility=View.VISIBLE

                        /*  transactionList.clear()
                          if(mothlyDisposable!=null)
                              mothlyDisposable!!.dispose()
                          if(frgement_type=="wallet_transactions"){
                              getOverAllTransactionList()
                          }else if(frgement_type=="wallet_credits"){
                              getOverAllEWalletCreditList()
                          }else if(frgement_type=="wallet_debits"){
                              getOverAllEWalletDebittList()
                          }*/

                    }
                })

    }

    fun setSpinnerPackages() {

        spinner_package!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                    var obj: Packages = spinner_package!!.getSelectedItem() as Packages

            }
        })
        packageAdapter = PackageSpinnerAdapter(activity, listPackages)
        spinner_package!!.adapter = packageAdapter;
    }

    private fun getPackages() {

        ApiClint.getInstance()?.getService()?.getpackages()
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
                                listPackages.add(pkg)
                            }
                            if (response.body()!!.size == 0) {
                            }
                        }
                        packageAdapter!!.notifyDataSetChanged()
                        progressdialog!!.dismiss();


                    }
                })
    }


}

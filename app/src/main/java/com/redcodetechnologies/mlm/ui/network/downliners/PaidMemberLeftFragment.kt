package com.redcodetechnologies.mlm.ui.network.downliners


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.models.users.UserTree
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.network.adapter.PaidUnpaidAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PaidMemberLeftFragment : Fragment() {

    lateinit var prefs: SharedPrefs
    var adsdisposable: Disposable? = null
    var downList: ArrayList<UserTree> = ArrayList()
    var adapter: PaidUnpaidAdapter? = null
    var id: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        prefs = SharedPrefs.getInstance()!!
        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId
        }
        getDownliners()
        return inflater.inflate(R.layout.fragment_paid_member_left, container, false)

    }

    private fun getDownliners() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }

        val adsObserver = getDownlinerObserver(id!!)
        var adsObservable: Observable<ArrayList<UserTree>> = MyApiRxClint.getInstance()?.getService()?.getUserUnpaidMembersLeft(id!!)!!
        adsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adsObserver)
    }

    private fun getDownlinerObserver(a : Int): Observer<ArrayList<UserTree>> {
        return object : Observer<ArrayList<UserTree>> {
            override fun onComplete() {
                println("completed")

            }

            override fun onSubscribe(d: Disposable) {
                adsdisposable = d

            }

            override fun onError(e: Throwable) {
                Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()

            }

            override fun onNext(response: ArrayList<UserTree>) {

                response?.forEach { ads ->
                    downList.add(ads)
                }
                adapter!!.notifyDataSetChanged()
            }

        }
    }
}

package com.redcodetechnologies.mlm.ui.videos.vediopack

import android.content.Intent
import io.reactivex.Observable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.VideoView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VedioCategory
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class VideoCategoryFragment : Fragment() {
    var adapter: VideoCategoryAdapter? = null
    var list: ArrayList<VedioCategory> = ArrayList()
    private var recyclerView: RecyclerView? = null
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragement_vedio_category, container, false)

        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Videos pack")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)

        initView(view)
        return view

    }


    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recylcer_videos_pkg)
        recyclerView!!.layoutManager = LinearLayoutManagerWrapper(activity!!, LinearLayout.VERTICAL, false)
        adapter = VideoCategoryAdapter(activity!!, list) { obj ->
            val intent = Intent(activity!!,VideoDetailActivity::class.java)
            intent.putExtra("categoryId",obj.Id)
            startActivity(intent)
        }

        recyclerView!!.adapter = adapter
        recyclerView!!.setItemAnimator(null);
        makeVideos()
    }

    fun makeVideos() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        if (!list.isEmpty())
            list.clear()


        val observer = getObserver()
        val observable: Observable<ArrayList<VedioCategory>> = MyApiRxClint.getInstance()!!.getService()!!.getvideocategories("bearer ")
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    fun getObserver(): Observer<ArrayList<VedioCategory>> {

        return object : Observer<ArrayList<VedioCategory>> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<VedioCategory>) {
                t.forEach { obj ->
                    list.add(obj)

                }
                adapter!!.notifyDataSetChanged()

                if (t.size == 0) {
                    recyclerView!!.visibility = View.GONE
                } else {
                    recyclerView!!.visibility = View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                print("error")
            }
        }
    }

    override fun onDestroyView() {
        if (disposable != null)
            disposable!!.dispose()
        super.onDestroyView()
    }

}
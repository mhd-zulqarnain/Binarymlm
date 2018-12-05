package com.redcodetechnologies.mlm.ui.videos.vediopack

import io.reactivex.Observable
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_videos_list.*
import java.util.*

class VideoPackFragment : Fragment() {
    var adapter: VideoPackAdapter? = null
    var list: ArrayList<VideosModal> = ArrayList()
    private var recyclerView : RecyclerView? = null
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {

        val view : View =inflater.inflate(R.layout.fragment_videos_list, container, false)

        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Tutorials")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)

        initView(view)
        return view

    }



    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recylcer_videos)
        recyclerView!!.layoutManager = LinearLayoutManagerWrapper(activity!!, LinearLayout.VERTICAL, false)
        adapter = VideoPackAdapter(activity!!, list) { obj ->


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
        val observable: Observable<ArrayList<VideosModal>> = MyApiRxClint.getInstance()!!.getService()!!.getVideosData("bearer ")
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    fun getObserver(): Observer<ArrayList<VideosModal>> {

        return object : Observer<ArrayList<VideosModal>> {
            override fun onComplete() {


            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<VideosModal>) {

                t.forEach { obj ->
                    list.add(obj)

                }
                adapter!!.notifyDataSetChanged()
                adapter!!.notifyDataSetChanged()

                if (t.size == 0) {
                    recylcer_videos!!.visibility = View.GONE
                } else {
                    recylcer_videos!!.visibility = View.VISIBLE
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
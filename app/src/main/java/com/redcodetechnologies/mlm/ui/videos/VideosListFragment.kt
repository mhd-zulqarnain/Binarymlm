package com.redcodetechnologies.mlm.ui.videos

import io.reactivex.Observable
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.Gson
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.network.MemberDetailActivity
import com.redcodetechnologies.mlm.ui.network.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_videos_list.*
import java.util.*
import javax.security.auth.callback.Callback

class VideosListFragment : Fragment() {
    var adapter: VideosAdapter? = null
    var list: ArrayList<VideosModal> = ArrayList()
    private var recyclerView : RecyclerView? = null
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        var view : View =inflater.inflate(R.layout.fragment_videos_list, container, false)

        initView(view)
        return view

    }



    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recylcer_videos)
        recyclerView!!.layoutManager = LinearLayoutManagerWrapper(activity!!, LinearLayout.VERTICAL, false)
        adapter = VideosAdapter(activity!!, list) { obj ->


        }

        recyclerView!!.adapter = adapter
        recyclerView!!.setItemAnimator(null);

        recyclerView!!.addOnItemTouchListener(RecyclerViewOnClickListener(activity!!, object : RecyclerViewOnClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                //start youtube player activity by passing selected video id via intent
                startActivity(Intent(activity, YoutubePlayerActivity::class.java)
                        .putExtra("video_id", list.get(position).TrainingVideoURL))
            }
        }))
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
                Apputils.showMsg(activity!!, "Token Expired")
                tokenExpire();
            }
        }
    }

    //token expire


    override fun onDestroyView() {
        if (disposable != null)
            disposable!!.dispose()
        super.onDestroyView()
    }

    fun tokenExpire() {
        startActivity(Intent(activity!!, SignInActivity::class.java))
        activity!!.finish()

    }
}
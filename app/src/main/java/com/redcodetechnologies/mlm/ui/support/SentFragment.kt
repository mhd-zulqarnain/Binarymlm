package com.redcodetechnologies.mlm.ui.support
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.SentBox

class SentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_sent, container, false)
        initView(view)
        return view
    }

    fun initView(view: View) {
        var sentbox = ArrayList<SentBox>()
        sentbox.add(SentBox("Zulqurnain", "I recieve domain", "Delivred", "12/06/18"))
        sentbox.add(SentBox("Basit", "Messege Not in Responce", "Delivred", "13/05/18"))
        sentbox.add(SentBox("Murtaza", "Notwek Failed", "Delivred", "19/08/18"))
        sentbox.add(SentBox("Arif", "I Balnce Recvd", "Delivred", "13/06/18"))

        var recyclerView = view!!.findViewById(R.id.sent_box_recycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?
        //  var report = SentboxAdapter(activity!!, sentbox)
        //recyclerView!!.adapter = report
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Sent Box")
    }
}
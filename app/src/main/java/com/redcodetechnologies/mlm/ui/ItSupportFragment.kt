package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.ItsupportAdopter
import com.redcodetechnologies.mlm.models.Inbox
import kotlinx.android.synthetic.main.fragment_it_support.view.*
import java.util.*
import com.mancj.materialsearchbar.MaterialSearchBar

class ItSupportFragment : Fragment() {
    var ed_search: EditText? =null;
    var searchBar:EditText?=null;
    var itsupportAdapater: ItsupportAdopter? = null
    var inboxAdapater: ItsupportAdopter? = null
    var inboxList: ArrayList<Inbox> = ArrayList()
    var inbox_recycler: RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view:View =inflater.inflate(R.layout.fragment_it_support, container, false)
        initView(view)
        return view

    }

    private fun initView(view:View) {

        var data = ArrayList<Inbox>()
        data.add(Inbox("Basit","How r u Admin","Yes","16/04.2018"))
        data.add(Inbox("Arif Bozdar","How r u Ali","Yes","15/04.2018"))
        data.add(Inbox("Shakoor","How r u Z.Baliti","Y","15/04.2018"))
        data.add(Inbox("Ali","How r u Arif","Yes","11/04.2018"))
        data.add(Inbox("Basit","How r u Admin","Yes","16/04.2018"))
        data.add(Inbox("Arif Bozdar","How r u Ali","Yes","15/04.2018"))
        data.add(Inbox("Shakoor","How r u Z.Baliti","Y","15/04.2018"))
        data.add(Inbox("Ali", "How r u Arif", "Yes", "11/04.2018"))

        var recyclerView = view!!.findViewById(R.id.unread_inbox_recycler) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(context,LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager?
        var adopter = ItsupportAdopter(data)
        recyclerView.adapter=adopter
        searchBar = view!!.findViewById(R.id.ed_search_bar) as EditText
        searchBar!!.setHint("Search..")

    }

}

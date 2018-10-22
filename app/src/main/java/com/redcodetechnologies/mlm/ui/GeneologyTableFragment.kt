package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R

class GeneologyTableFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_geneologytable, container, false)
        // initView(view)
       var fragment = arguments!!.getString("Fragment")
        if(fragment== "MyPackageCommisionList") {   // status visibility gone

        } else if(fragment== "MyDirectCommisionList" || fragment== "MyTableCommisionList") {
            //view all
        }
        return view
}

}

package com.redcodetechnologies.mlm.ui.network.adapter

import android.widget.Filter;
import com.redcodetechnologies.mlm.ui.network.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.models.users.Users


class DirectMemberSearch(var filterList: ArrayList<Users>, var adapter: StatusAdapter): Filter() {
    override fun performFiltering(constraints: CharSequence?): Filter.FilterResults {
        var constraint = constraints
        val results = Filter.FilterResults()
        if (constraint != null && constraint.length > 0) {
            constraint = constraint.toString().toUpperCase()
            val filtered = ArrayList<Users>()

            for (i in 0 until filterList.size) {
                if (filterList.get(i).Username?.toUpperCase()!!.contains(constraint)) {
                    filtered.add(filterList.get(i))
                }
            }
            results.count = filtered.size
            results.values = filtered
        } else {
            results.count = filterList.size
            results.values = filterList
        }

        return results
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {

        adapter.list = results.values as ArrayList<Users>
        adapter.notifyDataSetChanged()
    }
}
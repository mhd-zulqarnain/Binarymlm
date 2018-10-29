package com.redcodetechnologies.mlm.utils


import android.widget.Filter;
import com.redcodetechnologies.mlm.adapter.InboxAdapter
import com.redcodetechnologies.mlm.models.Inbox


class InboxSearch(var filterList: java.util.ArrayList<Inbox>, var adapter: InboxAdapter): Filter() {
    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        var constraint = constraint
        val results = Filter.FilterResults()
        if (constraint != null && constraint.length > 0) {
            constraint = constraint.toString().toUpperCase()
            val filtered = ArrayList<Inbox>()

            for (i in 0 until filterList.size) {
                //CHECK
                if (filterList.get(i).Sender_Name?.toUpperCase()!!.contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
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

        adapter.datalist = results.values as ArrayList<Inbox>
        adapter.notifyDataSetChanged()
    }
}

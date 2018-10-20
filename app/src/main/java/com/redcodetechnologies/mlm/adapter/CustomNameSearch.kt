package com.redcodetechnologies.mlm.adapter

import android.widget.Filter;
import com.redcodetechnologies.mlm.models.Users
import java.nio.file.Files.size



class CustomNameSearch(var filterList :ArrayList<Users>,var adapter: DownMemberAdapter): Filter() {
    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        var constraint = constraint
        val results = Filter.FilterResults()
        if (constraint != null && constraint.length > 0) {
            constraint = constraint.toString().toUpperCase()
            val filtered = ArrayList<Users>()

            for (i in 0 until filterList.size) {
                //CHECK
                if (filterList.get(i).name?.toUpperCase()!!.contains(constraint)) {
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

        adapter.list = results.values as ArrayList<Users>
        adapter.notifyDataSetChanged()
    }
}
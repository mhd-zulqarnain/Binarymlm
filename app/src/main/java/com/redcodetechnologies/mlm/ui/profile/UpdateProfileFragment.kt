package com.redcodetechnologies.mlm.ui.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FirstFragment : Fragment() {
    var updateprofile : Button? = null
    var name : EditText? = null
    var username : EditText? = null
    var address : EditText? = null
    var spinner_country : Spinner? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        updateprofile = view.findViewById(R.id.btn_updateprofile)
        name = view.findViewById(R.id.ed_name)
        username = view.findViewById(R.id.ed_username)
        address = view.findViewById(R.id.ed_address)
        spinner_country = view.findViewById(R.id.spinner_country)

        initView()
        updateprofile!!.setOnClickListener{
            validiation()
        }

        return view
    }

    private fun initView() {
        var arrayAdapter  =ArrayAdapter.createFromResource(activity!!,R.array.country_arrays,R.layout.support_simple_spinner_dropdown_item)
        spinner_country!!.adapter = arrayAdapter


    }

    fun validiation(){

        if(name!!.text.toString().trim(' ').length < 1) {
            name!!.error = Html.fromHtml("<font color='#E0796C'>Name could not be empty</font>")
            name!!.requestFocus()
        }
        else if(username!!.text.toString().trim(' ').length < 1){
            username!!.error = Html.fromHtml("<font color='#E0796C'>User name could not be empty</font>")
            username!!.requestFocus()
        }
        else if(address!!.text.toString().trim(' ').length < 1){
            address!!.error = Html.fromHtml("<font color='#E0796C'>Address could not be empty</font>")
            address!!.requestFocus()
        }

        else{
            Toast.makeText(activity!!, "Profile has been Updated!" , Toast.LENGTH_LONG).show()
        }

    }

}

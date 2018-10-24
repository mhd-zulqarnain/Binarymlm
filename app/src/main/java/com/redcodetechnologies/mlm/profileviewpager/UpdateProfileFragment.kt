package com.example.mir.viewpager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_first.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FirstFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val updateprofile = view!!.findViewById<Button>(R.id.btn_updateprofile)
        val name = view!!.findViewById<EditText>(R.id.ed_name)
        val username = view!!.findViewById<EditText>(R.id.ed_username)
        val address = view!!.findViewById<EditText>(R.id.ed_address)
        val country = view!!.findViewById<EditText>(R.id.ed_country)


        updateprofile.setOnClickListener(View.OnClickListener {

            if(name.text.toString().trim(' ').length < 1) {
                name.error = Html.fromHtml("<font color='#E0796C'>Name could not be empty</font>")
                name.requestFocus()
            }
            else if(username.text.toString().trim(' ').length < 1){
                username.error = Html.fromHtml("<font color='#E0796C'>User name could not be empty</font>")
                username.requestFocus()
            }
            else if(address.text.toString().trim(' ').length < 1){
                address.error = Html.fromHtml("<font color='#E0796C'>Address could not be empty</font>")
                address.requestFocus()
            }
            else if(country.text.toString().trim(' ').length < 1){
                country.error = Html.fromHtml("<font color='#E0796C'>Country could not be empty</font>")
                country.requestFocus()
            }

        })

        return view
    }

}

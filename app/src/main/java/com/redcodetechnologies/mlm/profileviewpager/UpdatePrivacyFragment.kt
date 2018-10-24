package com.example.mir.viewpager


import android.os.Bundle
import android.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.redcodetechnologies.mlm.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SecondFragment : android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.second_fragment, container, false)

        val password = view!!.findViewById<EditText>(R.id.ed_password)
        val phone = view!!.findViewById<EditText>(R.id.ed_phone)
        val email = view!!.findViewById<EditText>(R.id.ed_email)
        val bankname = view!!.findViewById<EditText>(R.id.ed_bankname)
        val accountnumber = view!!.findViewById<EditText>(R.id.ed_accountnumber)
        val updateprivacy = view!!.findViewById<Button>(R.id.btn_updateprivacy)


        updateprivacy.setOnClickListener(View.OnClickListener {
            if(password.text.toString().trim(' ').length < 1) {
                password.error = Html.fromHtml("<font color='#E0796C'>Password could not be empty</font>")
                password.requestFocus()
            }
            else if(password.text.toString().trim(' ').length < 8) {
                password.error = Html.fromHtml("<font color='#E0796C'>Password must contain 8 characters</font>")
                password.requestFocus()
            }
            else if(phone.text.toString().trim(' ').length < 1) {
                phone.error = Html.fromHtml("<font color='#E0796C'>Phone number could not be empty</font>")
                phone.requestFocus()
            }
            else if(email.text.toString().trim(' ').length < 1) {
                email.error = Html.fromHtml("<font color='#E0796C'>Email address could not be empty</font>")
                email.requestFocus()
            }
            else if(bankname.text.toString().trim(' ').length < 1) {
                bankname.error = Html.fromHtml("<font color='#E0796C'>Bank name could not be empty</font>")
                bankname.requestFocus()
            }
            else if(accountnumber.text.toString().trim(' ').length < 1){
                accountnumber.error = Html.fromHtml("<font color='#E0796C'>Account number could not be empty</font>")
                accountnumber.requestFocus()
            }


        })




        return view
    }
}

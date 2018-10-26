package com.example.mir.viewpager


import android.os.Bundle
import android.app.Fragment
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.company.redcode.royalcryptoexchange.utils.Apputils
import com.redcodetechnologies.mlm.R
import kotlin.math.log


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SecondFragment : android.support.v4.app.Fragment() {
    var password : EditText? = null
    var phone : EditText? = null
    var email : EditText? = null
    var bankname : EditText? = null
    var accountnumber : EditText? = null
    var updateprivacy : Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.second_fragment, container, false)

         password = view!!.findViewById<EditText>(R.id.ed_password)
         phone = view!!.findViewById<EditText>(R.id.ed_phone)
         email = view!!.findViewById<EditText>(R.id.ed_email)
         bankname = view!!.findViewById<EditText>(R.id.ed_bankname)
         accountnumber = view!!.findViewById<EditText>(R.id.ed_accountnumber)
         updateprivacy = view!!.findViewById<Button>(R.id.btn_updateprivacy)


        updateprivacy!!.setOnClickListener(View.OnClickListener {

            validiation()
        })





        return view
    }//oncreate()




    fun validiation(){

    if(password!!.text.toString().trim(' ').length < 1) {
        password!!.error = Html.fromHtml("<font color='#E0796C'>Password could not be empty</font>")
        password!!.requestFocus()
    }
    else if(password!!.text.toString().trim(' ').length < 8) {
        password!!.error = Html.fromHtml("<font color='#E0796C'>Password must contain 8 characters</font>")
        password!!.requestFocus()
    }
    else if(phone!!.text.toString().trim(' ').length < 1) {
        phone!!.error = Html.fromHtml("<font color='#E0796C'>Phone number could not be empty</font>")
        phone!!.requestFocus()
    }
    else if(phone!!.text.toString().trim(' ').length < 11) {
        phone!!.error = Html.fromHtml("<font color='#E0796C'>Phone number must contain 11 characters</font>")
        phone!!.requestFocus()
    }
    else if(email!!.text.toString().trim(' ').length < 1) {
        email!!.error = Html.fromHtml("<font color='#E0796C'>Email address could not be empty</font>")
        email!!.requestFocus()
    }
    else if (!Apputils.isValidEmail(email!!.text.toString()) || email!!.text.toString() == "") {
        email!!.error = Html.fromHtml("<font color='#E0796C'>Not a Proper email Address</font>")
        email!!.requestFocus()

    }
    else if(bankname!!.text.toString().trim(' ').length < 1) {
        bankname!!.error = Html.fromHtml("<font color='#E0796C'>Bank name could not be empty</font>")
        bankname!!.requestFocus()
    }
    else if(accountnumber!!.text.toString().trim(' ').length < 1){
        accountnumber!!.error = Html.fromHtml("<font color='#E0796C'>Account number could not be empty</font>")
        accountnumber!!.requestFocus()
    }
    else if(accountnumber!!.text.toString().trim(' ').length < 16){
        accountnumber!!.error = Html.fromHtml("<font color='#E0796C'>Account number must contain 16 characters</font>")
        accountnumber!!.requestFocus()
    }
    else{
        Toast.makeText(activity!!, "Privacy has been Updated!" , Toast.LENGTH_LONG).show()
    }


}

}

////-------------------------------------------------------------------------------------------------------------------------------------------
//
//        var keyDel2 : Int? =  0;
//
//        accountnumber!!.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                accountnumber!!.setOnKeyListener(object : View.OnKeyListener{
//                    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                        if (keyCode == KeyEvent.KEYCODE_DEL)
//                            keyDel2 = 1;
//                        return false;
//                    }
//
//                });
//                if (keyDel2 == 0) {
//                    var len : Int? = accountnumber!!.text.toString().length
//
//                    if(len == 4 || len == 15) {
//                        accountnumber!!.setText(accountnumber!!.getText().toString() + "-");
//                        accountnumber!!.setSelection(accountnumber!!.getText().toString().length);
//
//                    }
//                    else {
//                        keyDel2 = 0;
//                    }
//                }
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//
//            }
//
//
//
//        });
////-------------------------------------------------------------------------------------------------------------------------------------------
//
//        var flag : Boolean? = false;
//
//
//        phone!!.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun afterTextChanged(s: Editable?) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//
//
//                var len : Int? = phone!!.text.toString().length
//
//                if(len == 4 ){
//                    phone!!.setText(phone!!.getText().toString() + "-809809898798798797");
//                    phone!!.setSelection(phone!!.getText().toString().length);
//                }
//
//                }
//
//        });
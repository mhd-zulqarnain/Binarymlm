package com.redcodetechnologies.mlm.ui.profile


import android.annotation.SuppressLint
import android.os.Bundle
import android.app.Fragment
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.method.Touch
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.NewUserRegistration
import com.redcodetechnologies.mlm.utils.SharedPrefs
import kotlinx.android.synthetic.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SecondFragment : android.support.v4.app.Fragment() {
    var ed_password: EditText? = null
    var phone: EditText? = null
    var email: EditText? = null
    var bankname: EditText? = null
    var accountnumber: EditText? = null
    var updateprivacy: Button? = null
    var pref: SharedPrefs? = null
    lateinit var obj: NewUserRegistration;
    var password:String ?= null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.second_fragment, container, false)

        ed_password = view!!.findViewById(R.id.ed_password)
        phone = view.findViewById(R.id.ed_phone)
        email = view.findViewById(R.id.ed_email)
        bankname = view.findViewById(R.id.ed_bankname)
        accountnumber = view.findViewById(R.id.ed_accountnumber)
        updateprivacy = view.findViewById(R.id.btn_updateprivacy)

        initView()

        updateprivacy!!.setOnClickListener(View.OnClickListener {

            validiation()
        })

        ed_password!!.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val DRAWABLE_RIGHT = 2

                if (event!!.getAction() === MotionEvent.ACTION_UP)
                {
                    if (event!!.getRawX() >= (ed_password!!.getRight() - ed_password!!.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                    {
                        showChangePasswordDialog()
                        // your action here
                        return true
                    }
                }
                return false
            }
        })







        return view
    }//oncreate()

    fun initView() {
        pref = SharedPrefs.getInstance()
        obj = pref!!.getUser(activity!!)
        ed_password!!.setText(obj.password.toString())
        phone!!.setText(obj.phone.toString())
        bankname!!.setText(obj.bankName.toString())
        accountnumber!!.setText(obj.accountNumber.toString())
        email!!.setText(obj.email.toString())
    }


    fun validiation() {



        if (ed_password!!.text.toString().trim(' ').length < 1) {
            ed_password!!.error = Html.fromHtml("<font color='#E0796C'>Password could not be empty</font>")
            ed_password!!.requestFocus()
        } else if (ed_password!!.text.toString().trim(' ').length < 8) {
            ed_password!!.error = Html.fromHtml("<font color='#E0796C'>Password must contain 8 characters</font>")
            ed_password!!.requestFocus()
        } else if (phone!!.text.toString().trim(' ').length < 1) {
            phone!!.error = Html.fromHtml("<font color='#E0796C'>Phone number could not be empty</font>")
            phone!!.requestFocus()
        } else if (phone!!.text.toString().trim(' ').length < 11) {
            phone!!.error = Html.fromHtml("<font color='#E0796C'>Phone number must contain 11 characters</font>")
            phone!!.requestFocus()
        } else if (email!!.text.toString().trim(' ').length < 1) {
            email!!.error = Html.fromHtml("<font color='#E0796C'>Email address could not be empty</font>")
            email!!.requestFocus()
        } else if (!Apputils.isValidEmail(email!!.text.toString()) || email!!.text.toString() == "") {
            email!!.error = Html.fromHtml("<font color='#E0796C'>Not a Proper email Address</font>")
            email!!.requestFocus()

        } else if (bankname!!.text.toString().trim(' ').length < 1) {
            bankname!!.error = Html.fromHtml("<font color='#E0796C'>Bank name could not be empty</font>")
            bankname!!.requestFocus()
        } else if (accountnumber!!.text.toString().trim(' ').length < 1) {
            accountnumber!!.error = Html.fromHtml("<font color='#E0796C'>Account number could not be empty</font>")
            accountnumber!!.requestFocus()
        } else if (accountnumber!!.text.toString().trim(' ').length < 16) {
            accountnumber!!.error = Html.fromHtml("<font color='#E0796C'>Account number must contain 16 characters</font>")
            accountnumber!!.requestFocus()
        } else {
            Toast.makeText(activity!!, "Privacy has been Updated!", Toast.LENGTH_LONG).show()
        }


    }

    private fun showChangePasswordDialog() {
        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dilalog_new_pass, null)
        val alertBox = AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()

        //-------------------------------------------------------------------------
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        val ed_old_pass: EditText = view.findViewById(R.id.ed_old_pass)
        val ed_new_pass: EditText = view.findViewById(R.id.ed_new_pass)
        val ed_confirm_pass: EditText = view.findViewById(R.id.ed_confirm_pass)
        val btn_verify: Button = view.findViewById(R.id.btn_verify)
         password = pref!!.getUser(activity!!).password
        btn_verify.setOnClickListener {
            if (ed_old_pass.text.toString() == password && ed_old_pass.text.toString().trim() != "") {
                if (ed_new_pass.text.toString().trim() != "" && ed_confirm_pass.text.toString().trim() != "") {
                    if (ed_new_pass.text.toString().trim() == ed_confirm_pass.text.toString().trim()) {
                        if (ed_new_pass!!.text.toString().trim { it <= ' ' }.length < 8) {
                            Apputils.showMsg(activity!!, "Password should be greater than 8")
                        } else {
                            password = ed_confirm_pass.text.toString()
                            Apputils.showMsg(activity!!, "New password added please update")
                            dialog.dismiss()

                        }
                    } else {
                        Apputils.showMsg(activity!!, "Password not matched")

                    }

                } else {
                    Apputils.showMsg(activity!!, "Fill all fields")
                }

            } else {
                Apputils.showMsg(activity!!, "Old Password is wrong")
            }
        }
        dialog.show()
    }

}

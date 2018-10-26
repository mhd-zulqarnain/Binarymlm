package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

import com.redcodetechnologies.mlm.R
import kotlinx.android.synthetic.main.fragment_get_help.*

class GetHelp : Fragment() {

    val REQUSET_GALLERY_CODE: Int = 43
    var peroritySpini: Spinner? = null
    var description: EditText? = null;
    var message: EditText? = null
    var addimage: Button? = null;
    var send: Button? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_get_help, container, false)

        initView(view)
        return view
    }
    private fun initView(view: View?) {
        message = view?.findViewById(R.id.ed_message)
        peroritySpini = view?.findViewById(R.id.perority)
        description = view?.findViewById(R.id.ed_description)
        addimage = view?.findViewById(R.id.addImage)
        send = view?.findViewById(R.id.bsend)

        send?.setOnClickListener {
            if (ed_message?.text.toString().trim(' ').length < 1) {
                ed_message?.error = Html.fromHtml("<font color='#E0796C'>Message could not be empty</font>")
                ed_message?.requestFocus()
            }
            if (description?.text.toString().trim(' ').length < 1) {
                ed_description?.error = Html.fromHtml("<font color='#E0796C'>Description could not be empty</font>")
                ed_description?.requestFocus()
            }
            addImage?.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, REQUSET_GALLERY_CODE)
            }

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }
}

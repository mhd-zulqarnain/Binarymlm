package com.redcodetechnologies.mlm.ui.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Base64
import android.view.*
import android.widget.*
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.support.adapter.MessageAdapter
import com.redcodetechnologies.mlm.models.Messages
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.util.*

class InboxFragment : Fragment() {
    var search_view: SearchView? = null
    //    var inboxAdapater: MessageAdapter? = null
    var data: ArrayList<Messages> = ArrayList()
    //    var inbox_recycler: RecyclerView? = null
    var adapter: MessageAdapter? = null
    var disposable: Disposable? = null
    lateinit var progressBar: LinearLayout
    lateinit var tv_no_data: LinearLayout
    lateinit var btn_compose: Button
    lateinit var recyclerView: RecyclerView

    var userId: Int? = null
    var sponserId: Int? = null
    var userName: String? = null

    val SPONSER_INBOX: String = "Sponser_Inbox"
    val IT_INBOX: String = "IT_Inbox"
    val SELECT_SUPPORT_PHOTO: Int = 32
    var supportImage: String = ""
    var img_it_support: TextView? = null

    lateinit var user: NewUserRegistration
    lateinit var frgementType: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_inbox, container, false)
        frgementType = arguments?.getString("Fragment").toString();

        initView(view)
        return view
    }

    private fun initView(view: View) {

        user = SharedPrefs.getInstance()!!.getUser(activity!!)
        userId = user.userId
        sponserId = user.sponsorId
        userName = user.username

        search_view = view.findViewById(R.id.search_view)
        progressBar = view.findViewById(R.id.progressBar)
        tv_no_data = view.findViewById(R.id.tv_no_data)
        btn_compose = view.findViewById(R.id.btn_compose)

        recyclerView = view.findViewById(R.id.unread_inbox_recycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        adapter = MessageAdapter(activity!!, data, frgementType) { obj ->
            viewAndReply(obj)
        }

        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
        getMessages()
        search_view!!.setOnClickListener {
            search_view!!.setIconified(false)
        }

        search_view!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter!!.getFilter().filter(query)
                return false
            }
        })

        btn_compose.setOnClickListener {
            newMessageDialog()
        }
    }

    fun getMessages() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        val observer = getObserver()
        val userId = SharedPrefs.getInstance()!!.getUser(activity!!).userId
        var observable: Observable<ArrayList<Messages>>

        if (frgementType == SPONSER_INBOX) {
            observable = MyApiRxClint.getInstance()!!.getService()!!.viewallmessagesupport(userId!!)
        } else {
            observable = MyApiRxClint.getInstance()!!.getService()!!.viewallmessageItsupport(userId!!)
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun getObserver(): Observer<ArrayList<Messages>> {

        return object : Observer<ArrayList<Messages>> {
            override fun onComplete() {
                progressBar.visibility = View.GONE

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<Messages>) {

                t.forEach { obj ->
                    data.add(obj)
                }
                adapter!!.notifyDataSetChanged()
                if (t.size == 0) {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    tv_no_data.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                print("error")
            }
        }
    }

    fun viewAndReply(inbox: Messages) {

        val v: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_inbox_view_message, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(v)
        val dialog = alertBox.create()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);

        dialog!!.setCancelable(true);
        val message: TextView = v.findViewById(R.id.message)
        val tv_sender_name: TextView = v.findViewById(R.id.tv_sender_name)
        val tv_reply: TextView = v.findViewById(R.id.tv_reply)
        val sc_reply_view: ScrollView = v.findViewById(R.id.sc_reply_view)
        val btn_submit: Button = v.findViewById(R.id.btn_submit)
        val rep_message: EditText = v.findViewById(R.id.rep_message)

        message.text = inbox.Message
        if(inbox.IsRead == false)
            updateMessageStatus(inbox.Id!!)
        inbox.IsRead = true

        var index = -1;

        for (i in 0 until data.size) {

            if (data[i].Id == inbox.Id) {
                index = i
            }
        }
        if (index != -1) {
            data.set(index, inbox)
            adapter!!.notifyItemChanged(index)
        }



        if (frgementType == IT_INBOX) {
            tv_sender_name.text = "Support:"
            tv_reply.visibility = View.GONE
            sc_reply_view.visibility = View.GONE
        } else {
            tv_sender_name.text = inbox.Sender_Name + ":"
        }
        btn_submit.setOnClickListener {
            if (rep_message.text.toString().trim() != "") {

                replymessagesponsor(rep_message.text.toString(), inbox.UserId!!)

                dialog.dismiss()
            } else
                Apputils.showMsg(activity!!, "Message could not be empty!!")
        }

        dialog.show()

    }

    fun newMessageDialog() {

        val v: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_compose_new_message, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(v)
        val dialog = alertBox.create()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog!!.setCancelable(true);

        val btn_submit: Button = v.findViewById(R.id.btn_submit)
        val btn_add_image: Button = v.findViewById(R.id.btn_add_image)
        val rep_message: EditText = v.findViewById(R.id.rep_message)
        val spinner_receiver: Spinner = v.findViewById(R.id.spinner_receiver)
        val view_sponser: LinearLayout = v.findViewById(R.id.view_sponser)
        val view_it: LinearLayout = v.findViewById(R.id.view_it)
        img_it_support = v.findViewById(R.id.img_it_support)

        var reciverId = 1;

        if (frgementType == IT_INBOX) {
            reciverId = 0;
            view_it.visibility = View.VISIBLE
            view_sponser.visibility = View.GONE
        } else {
            view_it.visibility = View.GONE
            view_sponser.visibility = View.VISIBLE

        }
        btn_add_image.setOnClickListener {
            pickImage(SELECT_SUPPORT_PHOTO)
        }

        val arrayAdapter = ArrayAdapter.createFromResource(activity!!, R.array.compose_sponsor_spinner, R.layout.support_simple_spinner_dropdown_item)
        spinner_receiver.adapter = arrayAdapter
        spinner_receiver.setSelection(1)
        spinner_receiver.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (pos == 1) {
                    reciverId = user.sponsorId!!
                } else
                    reciverId = 1
            }
        })

        btn_submit.setOnClickListener {
            if (rep_message.text.toString().trim() != "") {
                if (frgementType == SPONSER_INBOX) {
                    val msg = Messages(null, null, user.username, user.userId,
                            reciverId, rep_message.text.toString())

                    newMessageSponser(msg)
                } else if (frgementType == IT_INBOX) {
                    val msg = Messages(null, null, user.username, user.userId,
                            reciverId, rep_message.text.toString(), null, null, supportImage)

                    newMessageItSupport(msg)
                }
                dialog.dismiss()
            } else
                Apputils.showMsg(activity!!, "Message could not be empty!!")
        }

        dialog.show()
    }

    private fun replymessagesponsor(msg: String, receiverId: Int) {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.replymessagesponsor(receiverId, msg, userId!!, userName!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            Toast.makeText(activity!!, " Message sent ", Toast.LENGTH_SHORT).show()
                            /* if (!data.isEmpty())
                                 data.clear()
                             getMessages()*/
                        }

                        if (code != 200) {
                            Toast.makeText(activity!!, " Failed ", Toast.LENGTH_SHORT).show()

                        }

                    }
                })
    }

    //<editor-fold desc="Compose new Messaage">

    private fun newMessageSponser(msg: Messages) {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.newMessageSponser(msg)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            Toast.makeText(activity!!, " Message sent ", Toast.LENGTH_SHORT).show()
                            /* if (!data.isEmpty())
                                 data.clear()
                             getMessages()*/
                        }

                        if (code != 200) {
                            Toast.makeText(activity!!, " Failed ", Toast.LENGTH_SHORT).show()

                        }

                    }
                })
    }

    private fun newMessageItSupport(msg: Messages) {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.newMessageItSupport(msg)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            Toast.makeText(activity!!, " Message sent ", Toast.LENGTH_SHORT).show()
                        }

                        if (code != 200) {
                            Toast.makeText(activity!!, " Failed ", Toast.LENGTH_SHORT).show()

                        }

                    }
                })
    }

    //</editor-fold>

    //<editor-fold desc="support image handling">
    private fun imageTostring(bitmap: Bitmap): String {
        val outStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outStream)
        val imageBytes = outStream.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }

    fun pickImage(code: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, code)
    }
    //</editor-fold>

    private fun updateMessageStatus(id : Int) {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.updateMessageStatus(id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(activity, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")

                    }
                })
    }
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Inbox")
        (activity as DrawerActivity).getSupportActionBar()!!.setIcon(R.drawable.ic_message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            var filename = "No Image found"

            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.getContentResolver(), imageUri);
            try {
                val arr = getRealPathFromURI(activity!!, imageUri).split("/")
                filename = arr[arr.size - 1]
            } catch (e: Exception) {
            }

            img_it_support!!.setText(filename)
            supportImage = imageTostring(bitmap)


        }

    }

}

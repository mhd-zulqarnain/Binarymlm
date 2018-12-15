package com.redcodetechnologies.mlm.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Base64

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.profile.ProfileSetup
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.ServiceError
import com.redcodetechnologies.mlm.utils.ServiceListener
import com.redcodetechnologies.mlm.utils.SharedPrefs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {


    //<editor-fold desc="privacy">
    var ed_password: EditText? = null
    var phone: EditText? = null
    var email: EditText? = null
    var bankname: EditText? = null
    var accountnumber: EditText? = null
    var pref: SharedPrefs? = null
    lateinit var obj: NewUserRegistration;
    //</editor-fold>

    //<editor-fold desc="setting">
    private val SELECT_CAMERA_IMAGE = 555
    private val REQUSET_GALLERY_CODE: Int = 44
    private val SELECT_DOCUMENT_PHOTO = 999
    private val SELECT_NIC_PHOTO = 39
    private val SELECT_NIC_BACKSIDE_PHOTO = 40
    var updateprofile: Button? = null
    var name: EditText? = null
    var username: EditText? = null
    var address: EditText? = null
    var ed_upload_document: EditText? = null
    var ed_upload_nic: EditText? = null
    var ed_upload_nic_backside: EditText? = null
    var profile_image: CircleImageView? = null
    var spinner_country: SearchableSpinner? = null
    var profileSetup: ProfileSetup = ProfileSetup()
    var mPassword: String = ""
    var progressdialog: android.app.AlertDialog? = null

    var profileImg: String = ""
    var nicImg: String = ""
    var nicImg1: String = ""
    var userdocumentImage: String? = null

    //</editor-fold>

    var id: Int? = null
    var token: String? = null
    var prefs = SharedPrefs.getInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btn_back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()

        }

        if (prefs.getUser(this@ProfileActivity).userId != null) {
            id = prefs.getUser(this@ProfileActivity).userId
            token = prefs.getToken(this@ProfileActivity).accessToken!!

            val isVerified = prefs.getUser(this@ProfileActivity).isVerify!!
            val isBlock = prefs.getUser(this@ProfileActivity).isBlock!!
            val isNewRequest = prefs.getUser(this@ProfileActivity).isNewRequest!!


            if (isBlock && isVerified) {
                showVerifiedDialog()
            } else if (isBlock) {
                showWarningDialog("")
            }
            if(isNewRequest){
                showWarningDialog("Your account is not active")
            }
        }

        progressdialog = SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Loading!!")
                .setTheme(R.style.CustomProgess)
                .build()


        initView()
    }

    fun initView() {
        ed_password = findViewById(R.id.ed_password)
        phone = findViewById(R.id.ed_phone)
        email = findViewById(R.id.ed_email)
        bankname = findViewById(R.id.ed_bankname)
        accountnumber = findViewById(R.id.ed_accountnumber)
        updateprofile = findViewById(R.id.btn_updateprofile)
        name = findViewById(R.id.ed_name)
        username = findViewById(R.id.ed_username)
        address = findViewById(R.id.ed_address)
        ed_upload_document = findViewById(R.id.ed_upload_document)
        ed_upload_nic = findViewById(R.id.ed_upload_nic)
        ed_upload_nic_backside = findViewById(R.id.ed_upload_nic_backside)
        spinner_country = findViewById(R.id.spinner_country)
        profile_image = findViewById(R.id.profile_image)
        pref = SharedPrefs.getInstance()

        pref = SharedPrefs.getInstance()
        obj = pref!!.getUser(this@ProfileActivity)

        if(obj.isVerify!!){
            txt_verify.setText("Account verified")
            txt_verify.setBackgroundColor(Color.GREEN)

        }
        else
            txt_verify.setText("Please verfiy your account")

        if (obj.userId != null) {
            profileImg = obj.profileImage.toString()
            nicImg = obj.nicImage.toString()
            nicImg1 = obj.nicImage1.toString()

            if (profileImg != "" && profileImg != "null")
                profile_image!!.setImageBitmap(stringtoImage(profileImg))
        }
        updateprofile!!.setOnClickListener {

            validiation()
        }



        phone!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 1 && s.toString().startsWith("0")) {
                    s!!.clear();
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        ed_password!!.setOnClickListener {
            showChangePasswordDialog()

        }
        ed_username!!.setOnClickListener {
            showChangeUsernameDialog()

        }
        ed_accountnumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable!!.length == 4 || editable.length == 15) {
                    editable.append('-');
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        /*       setOnTouchListener(object : View.OnTouchListener {
           override fun onTouch(v: View?, event: MotionEvent?): Boolean {
               val DRAWABLE_RIGHT = 2
               if (event!!.getAction() === MotionEvent.ACTION_UP) {
                   if (event!!.getRawX() >= (ed_password!!.getRight() - ed_password!!.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                       return true
                   }
               }
               return false
           }
       })*/

        ed_upload_document!!.setOnClickListener {
            pickImage(SELECT_DOCUMENT_PHOTO)
        }
        ed_upload_nic!!.setOnClickListener {
            pickImage(SELECT_NIC_PHOTO)
        }
        ed_upload_nic_backside!!.setOnClickListener {
            pickImage(SELECT_NIC_BACKSIDE_PHOTO)
        }

        profile_image!!.setOnClickListener {
            profileImageDialoge()
        }

        val arrayAdapter = ArrayAdapter.createFromResource(this@ProfileActivity, R.array.country_arrays, R.layout.support_simple_spinner_dropdown_item)

        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");
        spinner_country!!.setSelection(166)

        if (obj.upperId != null) {
            val u_email = if (obj.email.toString() == "null") "" else obj.email.toString()
            val u_address = if (obj.address.toString() == "null") "" else obj.address.toString()
            val u_bank = if (obj.bankName.toString() == "null") "" else obj.bankName.toString()
            val u_account = if (obj.accountNumber.toString() == "null") "" else obj.accountNumber.toString()

            ed_password!!.setText(obj.password.toString())
            bankname!!.setText(u_bank)
            accountnumber!!.setText(u_account)
            email!!.setText(u_email)
            mPassword = pref!!.getUser(this@ProfileActivity).password!!
            name!!.setText(obj.name)
            username!!.setText(obj.username)
            address!!.setText(u_address)
            cleanMobileNumeber(obj.phone.toString())

        }
        if (obj.documentImage != null)
            userdocumentImage = obj.documentImage.toString()

    }

    fun showWarningDialog(msg:String) {
        val view: View = LayoutInflater.from(this@ProfileActivity).inflate(R.layout.dialog_warning, null)
        val alertBox = AlertDialog.Builder(this@ProfileActivity)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()

        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);

        val btn_ok: Button = view.findViewById(R.id.btn_ok)
        val tv_warn: TextView = view.findViewById(R.id.tv_warn)
        if(msg!=""){
            tv_warn.setText(msg)
        }
        btn_ok.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()

    }

    fun showVerifiedDialog() {
        val view: View = LayoutInflater.from(this@ProfileActivity).inflate(R.layout.dialog_verfied, null)
        val alertBox = AlertDialog.Builder(this@ProfileActivity)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()

        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);

        val btn_ok: Button = view.findViewById(R.id.btn_ok)
        btn_ok.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()

    }

    private fun cleanMobileNumeber(number: String) {
        var temp = number;
        if (number.length == 3) {
            temp = ""
        } else {
            if (number != "") {
                temp = number.substring(3)
            }
        }
        phone!!.setText(temp)
    }

    fun validiation() {

        if (ed_password!!.text.toString().trim(' ').length < 1) {
            ed_password!!.error = Html.fromHtml("<font color='#E0796C'>Password could not be empty</font>")
            ed_password!!.requestFocus()
        } else if (ed_password!!.text.toString().trim(' ').length < 5) {
            ed_password!!.error = Html.fromHtml("<font color='#E0796C'>Password must contain 6 characters</font>")
            ed_password!!.requestFocus()
        } else if (phone!!.text.toString().trim(' ').length < 1) {
            phone!!.error = Html.fromHtml("<font color='#E0796C'>Phone number could not be empty</font>")
            phone!!.requestFocus()
        } else if (phone!!.text.toString().trim(' ').length < 10) {
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

            if (name!!.text.toString().trim(' ').length < 1) {
                name!!.error = Html.fromHtml("<font color='#E0796C'>Name could not be empty</font>")
                name!!.requestFocus()
                return

            }
            if (username!!.text.toString().trim(' ').length < 1) {
                username!!.error = Html.fromHtml("<font color='#E0796C'>User name could not be empty</font>")
                username!!.requestFocus()
                return

            }
            if (address!!.text.toString().trim(' ').length < 1) {
                address!!.error = Html.fromHtml("<font color='#E0796C'>Address could not be empty</font>")
                address!!.requestFocus()
                return
            }
            if (userdocumentImage == null) {
                Apputils.showMsg(this@ProfileActivity, "Please upload document")
                return
            }

            if (profileImg == "") {
                Apputils.showMsg(this@ProfileActivity, "Please update profile picture")
                return
            }
            if (nicImg == "" || nicImg == "null") {
                Apputils.showMsg(this@ProfileActivity, "Please Upload Front Side of NIC")
                return
            }
            if (nicImg1 == "" || nicImg1 == "null") {
                Apputils.showMsg(this@ProfileActivity, "Please Upload Back Side of NIC")
                return
            }

            var countryIndex = 0;
                countryIndex = spinner_country!!.getSelectedItemPosition() + 1

            var mbl = "+92" + phone!!.text.toString()
            profileSetup.Name = name!!.text.toString()
//            profileSetup.Username = username!!.text.toString()
            profileSetup.Address = address!!.text.toString()
            profileSetup.Country = countryIndex.toString()
            profileSetup.DocumentImage = userdocumentImage
            profileSetup.Password = mPassword;
            profileSetup.Phone = mbl
            profileSetup.Email = email!!.text.toString();
            profileSetup.BankName = bankname!!.text.toString();
            profileSetup.AccountNumber = accountnumber!!.text.toString();

            profileSetup.NICImage1 = nicImg1;
            profileSetup.NICImage = nicImg;
            profileSetup.ProfileImage = profileImg;

            print(profileSetup)
            updateProfile()

            Toast.makeText(this@ProfileActivity, "Privacy has been Updated!", Toast.LENGTH_LONG).show()
        }

    }

    private fun updateProfile() {

        if (!Apputils.isNetworkAvailable(this@ProfileActivity)) {
            Toast.makeText(baseContext, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        progressdialog!!.show()
        progressdialog!!.setCancelable(false)

        ApiClint.getInstance()?.getService()?.updateProfile("bearer " + token!!, id!!, profileSetup)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        var status = response.body()!!.success
                        var msg = response.body()!!.message
                        if (code == 200) {
                        } else {
                            progressdialog!!.dismiss()
                            print("error")
                        }

                        progressdialog!!.dismiss()
                        if (status!!) {
                            updatePref()
                            finish()
                            setResult(Activity.RESULT_OK)
                        }

                        Apputils.showMsg(this@ProfileActivity, msg!!)
                    }
                })
    }

    fun updatePref() {
        val mbl = "+92" + phone!!.text.toString()
        obj.name = profileSetup.Name
        obj.country = profileSetup.Country!!.toInt()
        obj.documentImage = profileSetup.DocumentImage
        obj.password = mPassword
        obj.phone = mbl
        obj.address = profileSetup.Address + " "
        obj.email = profileSetup.Email
        obj.bankName = profileSetup.BankName
        obj.accountNumber = profileSetup.AccountNumber
        obj.profileImage = profileImg
        obj.nicImage = profileSetup.NICImage
        obj.nicImage1 = profileSetup.NICImage1
        obj.isBlock = true
        pref!!.setUser(this@ProfileActivity, obj)
    }

    //<editor-fold desc="User name update">
    fun updateUsernamePref(username: String) {
        obj.username = username
        ed_username!!.setText(username)
        pref!!.setUser(this@ProfileActivity, obj)
    }

    fun updateUserName(service: ServiceListener<String>, username: String) {


        if (!Apputils.isNetworkAvailable(this@ProfileActivity)) {
            service.success(" Network error")
            return
        }

        ApiClint.getInstance()?.getService()?.updateUserName("bearer " + token!!, username, id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        service.success("Failed")

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")

                        var code = response!!.code()
                        if (code == 200) {

                            var msg = response!!.body()!!.message
                            if (msg != null)
                                service.success(msg)
                            else
                                service.success("Error")
                        } else {
                            service.success("Failed")
                        }
                    }
                })

    }

    private fun showChangeUsernameDialog() {
        val view: View = LayoutInflater.from(this@ProfileActivity).inflate(R.layout.dialog_update_username, null)
        val alertBox = AlertDialog.Builder(this@ProfileActivity)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()

        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
        val ed_username: EditText = view.findViewById(R.id.ed_username)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        val btn_verify: Button = view.findViewById(R.id.btn_verify)
        btn_verify.setOnClickListener {

            var username = ed_username.text.toString()
            if (username != obj.username && username.trim() != "") {
                progressBar.visibility = View.VISIBLE
                alertBox.setCancelable(false)
                updateUserName(object : ServiceListener<String> {
                    override fun success(response: String) {
                        Apputils.showMsg(this@ProfileActivity, response)
                        dialog.dismiss()
                        updateUsernamePref(username)
                    }

                    override fun fail(error: ServiceError) {
                        dialog.dismiss()
                    }

                }, username)
            }
        }
        dialog.show()
    }
    //</editor-fold>

    private fun showChangePasswordDialog() {
        val view: View = LayoutInflater.from(this@ProfileActivity).inflate(R.layout.dilalog_new_pass, null)
        val alertBox = AlertDialog.Builder(this@ProfileActivity)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()

        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
        val ed_old_pass: EditText = view.findViewById(R.id.ed_old_pass)
        val ed_new_pass: EditText = view.findViewById(R.id.ed_new_pass)
        val ed_confirm_pass: EditText = view.findViewById(R.id.ed_confirm_pass)
        val btn_verify: Button = view.findViewById(R.id.btn_verify)
        btn_verify.setOnClickListener {
            if (ed_old_pass.text.toString() == mPassword && ed_old_pass.text.toString().trim() != "") {
                if (ed_new_pass.text.toString().trim() != "" && ed_confirm_pass.text.toString().trim() != "") {
                    if (ed_new_pass.text.toString().trim() == ed_confirm_pass.text.toString().trim()) {
                        if (ed_new_pass.text.toString().trim { it <= ' ' }.length < 8) {
                            Apputils.showMsg(this@ProfileActivity, "Password should be greater than 8")
                        } else {
                            mPassword = ed_confirm_pass.text.toString()
                            Apputils.showMsg(this@ProfileActivity, "New password added please update")
                            dialog.dismiss()

                        }
                    } else {
                        Apputils.showMsg(this@ProfileActivity, "Password not matched")

                    }

                } else {
                    Apputils.showMsg(this@ProfileActivity, "Fill all fields")
                }

            } else {
                Apputils.showMsg(this@ProfileActivity, "Old Password is wrong")
            }
        }
        dialog.show()
    }

    //<editor-fold desc="Image handling">
    fun pickImage(code: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, code)
    }

    private fun profileImageDialoge() {
        val view: View = LayoutInflater.from(this@ProfileActivity).inflate(R.layout.select_image_dialog, null)
        val alertBox = AlertDialog.Builder(this@ProfileActivity)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        val gallery_dialog: ImageView = view.findViewById(R.id.gallery_dialog)
        val camera_dialog: ImageView = view.findViewById(R.id.camera_dialog)

        gallery_dialog.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUSET_GALLERY_CODE)
            dialog.dismiss()
        }
        camera_dialog.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, SELECT_CAMERA_IMAGE)
            dialog.dismiss()
        }
        dialog.show()
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {

            when (requestCode) {
                SELECT_DOCUMENT_PHOTO ->
                    try {
                        val imageUri = data.data
                        var filename = "No Image found"

                        val bitmap = MediaStore.Images.Media.getBitmap(this@ProfileActivity.getContentResolver(), imageUri);
                        try {
                            val arr = getRealPathFromURI(this@ProfileActivity, imageUri).split("/")
                            filename = arr[arr.size - 1]
                        } catch (e: Exception) {
                        }
                        ed_upload_document!!.setText(filename)
                        userdocumentImage = imageTostring(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SELECT_CAMERA_IMAGE ->
                    try {
                        var bitmap = data.extras.get("data") as Bitmap?
                        profileImg = imageTostring(bitmap!!)
                        profile_image!!.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                REQUSET_GALLERY_CODE ->
                    try {
                        val imageUri = data.data
                        val bitmap = MediaStore.Images.Media.getBitmap(this@ProfileActivity.getContentResolver(), imageUri);
                        profileImg = imageTostring(bitmap)
                        profile_image!!.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SELECT_NIC_PHOTO ->
                    try {
                        val imageUri = data.data
                        var filename = "No Image found"
                        val bitmap = MediaStore.Images.Media.getBitmap(this@ProfileActivity.getContentResolver(), imageUri);
                        try {
                            val arr = getRealPathFromURI(this@ProfileActivity, imageUri).split("/")
                            filename = arr[arr.size - 1]
                        } catch (e: Exception) {
                        }
                        ed_upload_nic!!.setText(filename)
                        nicImg = imageTostring(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SELECT_NIC_BACKSIDE_PHOTO ->
                    try {
                        val imageUri = data.data
                        var filename = "No Image found"
                        val bitmap = MediaStore.Images.Media.getBitmap(this@ProfileActivity.getContentResolver(), imageUri);
                        try {
                            val arr = getRealPathFromURI(this@ProfileActivity, imageUri).split("/")
                            filename = arr[arr.size - 1]
                        } catch (e: Exception) {
                        }
                        ed_upload_nic_backside!!.setText(filename)
                        nicImg1 = imageTostring(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

            }


        }
    }

    fun stringtoImage(encodedString: String): Bitmap? {
        try {
            var encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            var bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size);
            return bitmap;

        } catch (e: Exception) {
            return null
        }
    }
    //</editor-fold>

}



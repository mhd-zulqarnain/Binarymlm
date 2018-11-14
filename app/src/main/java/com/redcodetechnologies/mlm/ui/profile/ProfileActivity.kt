package com.redcodetechnologies.mlm.ui.profile
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Base64

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.profile.PrivacySetting
import com.redcodetechnologies.mlm.models.profile.ProfileSetting
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayOutputStream


class ProfileActivity : AppCompatActivity() {
//    var adapter :ViewPagerAdapter?= null

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
    var updateprofile: Button? = null
    var name: EditText? = null
    var username: EditText? = null
    var address: EditText? = null
    var ed_upload_document: EditText? = null
    var ed_upload_nic: EditText? = null
    var profile_image: CircleImageView? = null
    var spinner_country: SearchableSpinner? = null
    var userdocumentImage: String? = null
    var userNicImage: String? = null
    var profileSetting: ProfileSetting = ProfileSetting()
    var privacySetting: PrivacySetting = PrivacySetting()
    var mPassword: String = ""
    //</editor-fold>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btn_back.setOnClickListener {
            finish()
        }


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
        spinner_country = findViewById(R.id.spinner_country)
        profile_image = findViewById(R.id.profile_image)
        pref = SharedPrefs.getInstance()

        pref = SharedPrefs.getInstance()
        obj = pref!!.getUser(this@ProfileActivity)

        updateprofile!!.setOnClickListener {

            validiation()
        }


        ed_password!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event!!.getAction() === MotionEvent.ACTION_UP) {
                    if (event!!.getRawX() >= (ed_password!!.getRight() - ed_password!!.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showChangePasswordDialog()
                        return true
                    }
                }
                return false
            }
        })


        ed_upload_document!!.setOnClickListener {
            pickImage(SELECT_DOCUMENT_PHOTO)

        }
        ed_upload_nic!!.setOnClickListener {
            pickImage(SELECT_NIC_PHOTO)
        }
        profile_image!!.setOnClickListener {
            profileImageDialoge()
        }

        var arrayAdapter = ArrayAdapter.createFromResource(this@ProfileActivity, R.array.country_arrays, R.layout.support_simple_spinner_dropdown_item)

        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");
        spinner_country!!.setSelection(166)

        if (obj.upperId != null) {
            ed_password!!.setText(obj.password.toString())
            phone!!.setText(obj.phone.toString())
            bankname!!.setText(obj.bankName.toString())
            accountnumber!!.setText(obj.accountNumber.toString())
            email!!.setText(obj.email.toString())
            mPassword = pref!!.getUser(this@ProfileActivity).password!!
            name!!.setText(obj.name)
            username!!.setText(obj.username)
            address!!.setText(obj.address.toString())

        }else{
            ed_password!!.setText("12345678")
        }

        if (obj.documentImage != null)
            userdocumentImage = obj.documentImage.toString()

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
                ed_upload_document!!.error = Html.fromHtml("<font color='#E0796C'>Please upload document</font>")
                ed_upload_document!!.requestFocus()
                return
            }

            if (userNicImage == null) {
                ed_upload_nic!!.error = Html.fromHtml("<font color='#E0796C'>Please upload Nic</font>")
                ed_upload_nic!!.requestFocus()
                return
            }

            var countryIndex = 0;
            if (spinner_country!!.getSelectedItemPosition() != 0) {
                countryIndex = spinner_country!!.getSelectedItemPosition() - 1
            }

            profileSetting.Name = name!!.text.toString()
            profileSetting.Username = username!!.text.toString()
            profileSetting.Address = address!!.text.toString()
            profileSetting.Country = countryIndex
            profileSetting.DocumentImage = userdocumentImage
            privacySetting.Password = mPassword;
            privacySetting.Phone = phone!!.text.toString();
            privacySetting.Email = email!!.text.toString();
            privacySetting.BankName = bankname!!.text.toString();
            privacySetting.AccountNumber = accountnumber!!.text.toString();

            Toast.makeText(this@ProfileActivity, "Privacy has been Updated!", Toast.LENGTH_LONG).show()
        }

    }
    fun pickImage(code: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, code)
    }
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
                        userdocumentImage = imageTostring(bitmap!!)
                        profile_image!!.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                REQUSET_GALLERY_CODE ->
                    try {
                        val imageUri = data.data
                        val bitmap = MediaStore.Images.Media.getBitmap(this@ProfileActivity.getContentResolver(), imageUri);
                        userdocumentImage = imageTostring(bitmap)
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
                        userNicImage = imageTostring(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }

    }


}

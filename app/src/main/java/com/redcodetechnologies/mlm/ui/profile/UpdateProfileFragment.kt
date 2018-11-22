package com.redcodetechnologies.mlm.ui.profile


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.utils.SharedPrefs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import com.redcodetechnologies.mlm.models.profile.ProfileSetting
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream


class UpdateProfileFragment : Fragment() {
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
    var ed_upload_nic_back : EditText? = null
    var profile_image: CircleImageView? = null
    var spinner_country: SearchableSpinner? = null
    var pref: SharedPrefs? = null
    lateinit var obj: NewUserRegistration;
    var userdocumentImage: String? = null
    var userNicImage: String? = null
    var profileSetting: ProfileSetting = ProfileSetting()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        updateprofile = view.findViewById(R.id.btn_updateprofile)
        name = view.findViewById(R.id.ed_name)
        username = view.findViewById(R.id.ed_username)
        address = view.findViewById(R.id.ed_address)
        ed_upload_document = view.findViewById(R.id.ed_upload_document)
        ed_upload_nic = view.findViewById(R.id.ed_upload_nic)
        ed_upload_nic_back = view.findViewById(R.id.ed_upload_nic_backside)
        spinner_country = view.findViewById(R.id.spinner_country)
        profile_image = view.findViewById(R.id.profile_image)
        pref = SharedPrefs.getInstance()


        initView()
        updateprofile!!.setOnClickListener {

            validiation()
        }

        ed_upload_document!!.setOnClickListener {
            pickImage(SELECT_DOCUMENT_PHOTO)

        }
        ed_upload_nic!!.setOnClickListener {
            pickImage(SELECT_NIC_PHOTO)
        }
        ed_upload_nic_back!!.setOnClickListener {
            pickImage(SELECT_NIC_BACKSIDE_PHOTO)
        }
        profile_image!!.setOnClickListener {
            profileImageDialoge()
        }


        return view
    }

    fun pickImage(code: Int) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, code)
    }

    private fun initView() {
        obj = pref!!.getUser(activity!!)
        var arrayAdapter = ArrayAdapter.createFromResource(activity!!, R.array.country_arrays, R.layout.support_simple_spinner_dropdown_item)

        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");
        spinner_country!!.setSelection(166)
        name!!.setText(obj.name)
        username!!.setText(obj.username)
        address!!.setText(obj.address.toString())
        if (obj.documentImage != null)
            userdocumentImage = obj.documentImage.toString()

//        if (obj.documentImage != null)
//            userNicImage = obj.documentImage.toString()

    }

    private fun profileImageDialoge() {
        val view: View = LayoutInflater.from(activity!! as Context).inflate(R.layout.select_image_dialog, null)
        val alertBox = AlertDialog.Builder(activity!! as Context)
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

        dialog.show()
    }

    fun validiation() {

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
        if (resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            var filename = "No Image found"

            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.getContentResolver(), imageUri);
            try {
                val arr = getRealPathFromURI(activity!!, imageUri).split("/")
                filename = arr[arr.size - 1]
            } catch (e: Exception) {
            }

            when (requestCode) {
                SELECT_DOCUMENT_PHOTO ->
                    try {
                        ed_upload_document!!.setText(filename)
                        userdocumentImage = imageTostring(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SELECT_CAMERA_IMAGE ->
                    try {
                        userdocumentImage = imageTostring(bitmap)
                        profile_image!!.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                REQUSET_GALLERY_CODE ->
                    try {
                        userdocumentImage = imageTostring(bitmap)
                        profile_image!!.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                SELECT_NIC_PHOTO ->
                    try {
                        ed_upload_nic!!.setText(filename)
                        userNicImage = imageTostring(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }

    }

}

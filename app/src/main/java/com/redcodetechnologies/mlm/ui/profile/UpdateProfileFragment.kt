package com.redcodetechnologies.mlm.ui.profile


import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
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
import kotlinx.android.synthetic.main.fragment_first.*
import java.io.FileNotFoundException
import java.io.InputStream
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import java.io.ByteArrayOutputStream
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FirstFragment : Fragment() {
    private val CAMERA_INTENT = 555
    private val REQUSET_GALLERY_CODE: Int = 44
    private val SELECT_PHOTO = 999
    var updateprofile : Button? = null
    var uploadimage : EditText?  = null
    var name : EditText? = null
    var username : EditText? = null
    var address : EditText? = null
    var spinner_country : SearchableSpinner? = null
    var pref:SharedPrefs ? = null
    lateinit var obj : NewUserRegistration;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        updateprofile = view.findViewById(R.id.btn_updateprofile)
        uploadimage = view.findViewById(R.id.btn_upload_document)
        name = view.findViewById(R.id.ed_name)
        username = view.findViewById(R.id.ed_username)
        address = view.findViewById(R.id.ed_address)
        spinner_country = view.findViewById(R.id.spinner_country)
        pref = SharedPrefs.getInstance()


        initView()
        updateprofile!!.setOnClickListener{

            validiation()
        }

        uploadimage!!.setOnClickListener{
//            supportImageDialoge()
            pickAImage(it)

        }

        return view
    } //onCreate().

    fun pickAImage(view:View) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, SELECT_PHOTO)
    }



     override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
         when (requestCode) {
             SELECT_PHOTO -> if (resultCode == RESULT_OK)
             {
                 try
                 {
                     val selectedImage = imageReturnedIntent!!.getData()
                     val imageStream = getRealPathFromURI(activity!!, selectedImage)
                     val bitmap = BitmapFactory.decodeFile(imageStream)
                     val byteArrayOutputStream = ByteArrayOutputStream()
                     bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                     val byteArray = byteArrayOutputStream.toByteArray()

////                     fun encoder(filePath: String): String{
//                     val bytes = File(filePath).readBytes()
//   api level 26                  val base64 = Base64.getEncoder().encodeToString(bytes)
//                     return base64
//                 }

                     var encodedString : String? =  android.util.Base64.encodeToString(byteArray , 1)
                    // var encodedString : String? = Base64.encodeToString(byteArray, Base64.DEFAULT)
                     Log.wtf("Base64 = ", encodedString)
                     Toast.makeText(activity!!, encodedString, Toast.LENGTH_SHORT).show()
                 }
                 catch (e:Exception) {
                     e.printStackTrace()
                 }
             }
         }

    }
    fun getRealPathFromURI(context:Context, contentUri:Uri):String {
        var cursor : Cursor? = null
        try
        {
            val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close()
            }
        }
    }



     private fun supportImageDialoge() {
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
            // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            activity!!.startActivityForResult(intent, REQUSET_GALLERY_CODE)
            dialog.dismiss()
        }
        camera_dialog.setOnClickListener {


            if (ContextCompat.checkSelfPermission(activity!! ,
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                activity!!.startActivityForResult(intent, CAMERA_INTENT)

            } else {

                Toast.makeText(activity!!, "Please Allow app to Use Camera of your Device", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()


        }

        dialog.show()
    }



    private fun initView() {
        obj= pref!!.getUser(activity!!)
        var arrayAdapter  =ArrayAdapter.createFromResource(activity!!,R.array.country_arrays,R.layout.support_simple_spinner_dropdown_item)

        spinner_country!!.adapter = arrayAdapter
        spinner_country!!.setTitle("Select Country");
        spinner_country!!.setPositiveButton("Close");
        spinner_country!!.setSelection(166)

            spinner_country!!?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        Toast.makeText(activity!!, ""+(spinner_country!!.selectedItemPosition+1), Toast.LENGTH_SHORT).show()
                }

            }

        name!!.setText(obj.name)
        username!!.setText(obj.username)
        address!!.setText(obj.address.toString())
        spinner_country!!.setSelection(obj.country!!-1)




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

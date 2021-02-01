package com.application.suliraapplication.fragments

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.utils.CommonUtils
import com.application.suliraapplication.utils.ImageFilePath
import com.application.suliraapplication.viewmodels.EditUserViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_my_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyProfileFragment : BaseFragment(), View.OnClickListener {
    var userChoosenTask: String? = null
    var imageFilePath: String? = null
    val REQUEST_CAMERA = 5
    val SELECT_FILE = 6
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    var itemFile: File? = null

    private val editUserViewModel by lazy {
        ViewModelProvider(this).get(EditUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivEditImage.setOnClickListener(this)
        ivMyProfileBack.setOnClickListener(this)
        llEditUserProfile.setOnClickListener(this)
        btUpdateUserData.setOnClickListener(this)
        setUserData()
        disableToEditUserFields()

        editUserViewModel.successful.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
           dismissDialog()
            if (it) {
                if (editUserViewModel.message == "success") {
                    val signUpModel: SignUpModel = Gson().fromJson(
                        PreferenceManager().userResponse.toString(),
                        SignUpModel::class.java
                    )
                    signUpModel.userInfo = editUserViewModel.signUpModel.userInfo
                    PreferenceManager().saveuserData(Gson().toJson(signUpModel))
                    setError(editUserViewModel.signUpModel.msg)
                    editUserViewModel.message=""
                }
            } else {
                setError(editUserViewModel.message)
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (userChoosenTask.equals("Take Photo")) cameraIntent() else if (userChoosenTask.equals(
                        "Choose from Library"
                    )
                ) galleryIntent()
            }
        }
    }

    private fun onClickEditImage() {
        val items = arrayOf<CharSequence>(
            "Take Photo", "Choose from Library",
            "Cancel"
        )
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Upload Image")
        builder.setItems(items) { dialog: DialogInterface, item: Int ->
            when {
                items[item] == "Take Photo" -> {
                    userChoosenTask = "Take Photo"
                    cameraIntent()
                }
                items[item] == "Choose from Library" -> {
                    userChoosenTask = "Choose from Library"
                    galleryIntent()
                }
                items[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data)
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult()
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        val selectedImageUri = data!!.data
        val picturePath: String? =
            selectedImageUri?.let { ImageFilePath.getPath(requireContext().applicationContext, it) }
        itemFile = File(picturePath)
        var bm: Bitmap? = null
        try {
            bm = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data.data)
            ivEditImage.setImageBitmap(bm)
            // editProfileBinding.imgUserAvatar.rotation = 90F
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        val thumbnail = BitmapFactory.decodeFile(imageFilePath) as Bitmap
        itemFile = File(imageFilePath)
        try {
            ivEditImage.setImageBitmap(thumbnail)
            //editProfileBinding.imgUserAvatar.rotation = 90F
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun galleryIntent() {
        if (CommonUtils.checkPermission(requireContext())) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT //
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
        }
    }

    private fun cameraIntent() {
        if (CommonUtils.checkPermissionCamera(requireContext())) {
            val pictureIntent = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
            )
            if (pictureIntent.resolveActivity(requireContext().packageManager) != null) { //Create a itemFile to store the image
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) { // Error occurred while creating the File
                }
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.application.suliraapplication.provider",
                        photoFile
                    )
                    pictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        photoURI
                    )
                    startActivityForResult(
                        pictureIntent,
                        REQUEST_CAMERA
                    )
                }
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }

    override fun onClick(view: View?) {

        when (view) {

            ivMyProfileBack -> (activity as HomeTabActivity).onBackPressed()

            ivEditImage -> onClickEditImage()

            llEditUserProfile -> enableToEditUserFields()

            btUpdateUserData -> validateEnteredData()
        }

    }

    private fun validateEnteredData() {
        val strUserName = etLoggedUserName.text.toString()
        val strUserEmail = etLoggedUserEmail.text.toString()
        val strUserPhoneNumber = etLoggedUserPhoneNumber.text.toString()
        val strUserAge = etLoggedUserAge.text.toString()

        if (TextUtils.isEmpty(strUserName)) {
            etLoggedUserName.error = getString(R.string.enter_your_name)
            etLoggedUserName.isFocusable = true
            return
        }

        if (TextUtils.isEmpty(strUserEmail)) {
            etLoggedUserEmail.error = getString(R.string.enter_your_email)
            etLoggedUserEmail.isFocusable = true
            return
        }

        if (TextUtils.isEmpty(strUserPhoneNumber)) {
            etLoggedUserPhoneNumber.error = getString(R.string.enter_your_mobile_number)
            etLoggedUserPhoneNumber.isFocusable = true
            return
        }

        if (TextUtils.isEmpty(strUserAge)) {
            etLoggedUserAge.error = getString(R.string.enter_your_age)
            etLoggedUserAge.isFocusable = true
            return
        }

        val userId: RequestBody = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            PreferenceManager().userId.toString()
        )

        val fullName: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), strUserName)
        val email: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), strUserEmail)
        val phone: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), strUserPhoneNumber)
        val age: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), strUserAge)
        var body: MultipartBody.Part? = null

        if (itemFile != null) {
            val requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), itemFile!!)
            body = MultipartBody.Part.createFormData(
                "profilePic",
                itemFile!!.name,
                requestFile
            )
        }

        showDialog()
        editUserViewModel.editUser(userId, fullName, email, phone, age, body!!)
    }

    private fun setUserData() {
        val userData: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)
        etLoggedUserName.setText(userData.userInfo.fullName)
        etLoggedUserEmail.setText(userData.userInfo.email)
        etLoggedUserPhoneNumber.setText(userData.userInfo.phone)
        etLoggedUserAge.setText(userData.userInfo.age)

        if (!TextUtils.isEmpty(userData.userInfo.profilePic)) {
            Glide.with(requireActivity()).load("https://hourlylancer.com/devlop/sulira/backend/assets/uploads/profilePic/" + userData.userInfo.profilePic)
                .into(ivEditImage)
        }
    }

    private fun enableToEditUserFields() {
        etLoggedUserName.isEnabled = true
        etLoggedUserEmail.isEnabled = true
        etLoggedUserPhoneNumber.isEnabled = true
        etLoggedUserAge.isEnabled = true
        ivEditImage.isClickable = true

        etLoggedUserName.background = ContextCompat.getDrawable(requireContext(),R.drawable.white_rect_with_border)
        etLoggedUserEmail.background = ContextCompat.getDrawable(requireContext(),R.drawable.white_rect_with_border)
        llNumber.background = ContextCompat.getDrawable(requireContext(),R.drawable.white_rect_with_border)
        etLoggedUserAge.background = ContextCompat.getDrawable(requireContext(),R.drawable.white_rect_with_border)

        etLoggedUserName.isCursorVisible = true
        etLoggedUserName.requestFocus()
    }

    private fun disableToEditUserFields() {
        ivEditImage.isClickable = false
        etLoggedUserName.isEnabled = false
        etLoggedUserEmail.isEnabled = false
        etLoggedUserPhoneNumber.isEnabled = false
        etLoggedUserAge.isEnabled = false
    }
}
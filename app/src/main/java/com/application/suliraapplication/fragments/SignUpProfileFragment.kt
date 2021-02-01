package com.application.suliraapplication.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.adapter.AdapterSignUpProfilePic
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.utils.CommonUtils
import com.application.suliraapplication.utils.ImageFilePath
import com.application.suliraapplication.viewmodels.SignUpViewModel
import com.google.gson.Gson
import com.yarolegovich.discretescrollview.DSVOrientation
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_sign_up_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class SignUpProfileFragment : BaseFragment(),
    DiscreteScrollView.ScrollListener<AdapterSignUpProfilePic.ViewHolderProfilePic>,
    DiscreteScrollView.OnItemChangedListener<AdapterSignUpProfilePic.ViewHolderProfilePic>,
    View.OnClickListener {

    private val signUpViewModel by lazy {
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    private lateinit var adapterProfilePic: AdapterSignUpProfilePic
    private lateinit var listProfilePicModel: ArrayList<Drawable>
    private lateinit var listProfilePicIdModel: ArrayList<Int>
    private lateinit var disorint: DSVOrientation
    var userChoosenTask: String? = null
    var imageFilePath: String? = null
    val REQUEST_CAMERA = 7
    val SELECT_FILE = 8
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 127
    var itemFile: File? = null
    private var posSelectedIcon: Int = 0
    var dummyImageFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_sign_up_profile, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(false, "Skip")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSelectProfileImage.setOnClickListener(this)
        btProfilePicContinue.setOnClickListener(this)

        listProfilePicModel = arrayListOf()
        listProfilePicIdModel = arrayListOf()
        listProfilePicModel.add(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.emojji_one
            )!!
        )
        listProfilePicModel.add(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_two)!!)
        listProfilePicModel.add(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.emoji_three
            )!!
        )
        listProfilePicModel.add(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.emojji_one
            )!!
        )
        listProfilePicModel.add(ContextCompat.getDrawable(requireContext(), R.drawable.emoji_two)!!)
        listProfilePicModel.add(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.emoji_three
            )!!
        )

        listProfilePicIdModel.add(R.drawable.emojji_one)
        listProfilePicIdModel.add(R.drawable.emoji_two)
        listProfilePicIdModel.add(R.drawable.emoji_three)
        listProfilePicIdModel.add(R.drawable.emojji_one)
        listProfilePicIdModel.add(R.drawable.emoji_two)
        listProfilePicIdModel.add(R.drawable.emoji_three)

        adapterProfilePic = AdapterSignUpProfilePic(listProfilePicModel, requireContext(), 0)
        disorint = DSVOrientation.HORIZONTAL
        genderSelection.adapter = adapterProfilePic
        genderSelection.setOrientation(disorint); //Sets an orientation of the view
        genderSelection.setOffscreenItems(2); //Reserve extra space equal to (childSize * count) on each side of the view
        genderSelection.setOverScrollEnabled(true); //Can also be set using android:overScrollMode xml attribute
        genderSelection.setSlideOnFling(true);
        genderSelection.setItemTransformer(
            ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build()
        )

        /* val wrapper: InfiniteScrollAdapter<*> =
             InfiniteScrollAdapter.wrap(adapterProfilePic)
         genderSelection.adapter = wrapper*/
        genderSelection.addScrollListener(this);
        genderSelection.addOnItemChangedListener(this);

        signUpViewModel.successful.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { successful ->
                dismissDialog()
                if (successful) {
                    PreferenceManager().saveuserData(Gson().toJson(signUpViewModel.signUpModel))
                    PreferenceManager().userId(signUpViewModel.signUpModel.userInfo.id)
                    PreferenceManager().stripeToken(signUpViewModel.signUpModel.userInfo.stripeToken)
                    activity?.startActivity(Intent(activity, GetStartedActivity::class.java))
                } else {
                    setError(signUpViewModel.message)
                }
            })
    }

    override fun onScroll(
        scrollPosition: Float,
        currentPosition: Int,
        newPosition: Int,
        currentHolder: AdapterSignUpProfilePic.ViewHolderProfilePic?,
        newCurrent: AdapterSignUpProfilePic.ViewHolderProfilePic?
    ) {
        /*  var data = genderSelection.currentItem
          Toast.makeText(requireContext(), "$newPosition test", Toast.LENGTH_SHORT).show()*/

    }

    override fun onCurrentItemChanged(
        viewHolder: AdapterSignUpProfilePic.ViewHolderProfilePic?,
        adapterPosition: Int
    ) {
        posSelectedIcon = adapterPosition
        genderSelection.post(Runnable {
            adapterProfilePic.setSelectedPos(adapterPosition)
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            ivProfilePic.setImageBitmap(bm)
            // editProfileBinding.imgUserAvatar.rotation = 90F
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        val thumbnail = BitmapFactory.decodeFile(imageFilePath) as Bitmap
        itemFile = File(imageFilePath)
        try {
            ivProfilePic.setImageBitmap(thumbnail)
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

            tvSelectProfileImage -> onClickEditImage()
            btProfilePicContinue -> {
                progressPercentage = 13
                var body: MultipartBody.Part? = null
                if (itemFile != null) {
                    val requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), itemFile!!)
                    body = MultipartBody.Part.createFormData(
                        "profilePic",
                        itemFile!!.name,
                        requestFile
                    )

                } else {
                    val drawable = listProfilePicIdModel[posSelectedIcon]

                    val bm = BitmapFactory.decodeResource(requireContext().getResources(), drawable)
                    val name = "image" + Random().nextInt(61) + "sulira"
                    dummyImageFile = CommonUtils.saveBitmapToFile(
                        requireContext().filesDir,
                        name,
                        bm,
                        Bitmap.CompressFormat.PNG,
                        100
                    )

                    if (dummyImageFile != null) {
                        val requestFile =
                            RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                dummyImageFile!!
                            )
                        body = MultipartBody.Part.createFormData(
                            "profilePic",
                            dummyImageFile!!.name,
                            requestFile
                        )
                    }
                }

                signUpDataClass.profilePic = body!!
                profileDialog(requireContext())
            }
        }
    }

    private fun profileDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_blank)
        dialog.setCancelable(false)
        val metrics: DisplayMetrics = context.resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window!!.setLayout(6 * width / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        val etUserName: AppCompatEditText = dialog.findViewById(R.id.etUserName)
        val etUserEmail: AppCompatEditText = dialog.findViewById(R.id.etUserEmail)
        val etUserAge: AppCompatEditText = dialog.findViewById(R.id.etUserAge)
        val btSave: AppCompatButton = dialog.findViewById(R.id.btSave)
        val ivCancelDialog: AppCompatImageView = dialog.findViewById(R.id.ivCancelDialog)

        ivCancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        btSave.setOnClickListener {

            if (TextUtils.isEmpty(etUserName.text.toString())) {
                etUserName.error = "Please enter your name"
                etUserName.isFocusable = true
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(etUserEmail.text.toString())) {
                etUserEmail.error = "Please enter your email id"
                etUserEmail.isFocusable = true
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(etUserAge.text.toString())) {
                etUserAge.error = "Please enter your age"
                etUserAge.isFocusable = true
                return@setOnClickListener
            }

            val name: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                etUserName.text.toString()
            )

            val email: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                etUserEmail.text.toString()
            )

            val age: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                etUserAge.text.toString()
            )

            val deviceType: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), "android")
            val deviceToken: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                "hjbcd466vd64v6d4vdvvjdvbd"
            )

            val notification: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), "1")

            signUpDataClass.fullName = name
            signUpDataClass.email = email
            signUpDataClass.age = age
            signUpDataClass.deviceType = deviceType
            signUpDataClass.deviceToken = deviceToken
            signUpDataClass.notification = notification
            showDialog()
            signUpViewModel.signUpUser(signUpDataClass)
            dialog.dismiss()
        }
        dialog.show()
    }
}

/*
var body: MultipartBody.Part? = null
if (itemFile != null) {
    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), itemFile!!)
    body = MultipartBody.Part.createFormData("profile_photo", itemFile!!.name, requestFile)
}*/

package com.application.suliraapplication.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseActivity
import com.application.suliraapplication.fragments.*
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.pojo.AddPostCollectionModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.utils.CircularSeekBar
import com.application.suliraapplication.utils.CommonUtils
import com.application.suliraapplication.utils.ImageFilePath
import com.application.suliraapplication.viewmodels.AddPostViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home_tab.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
open class HomeTabActivity : BaseActivity(), View.OnClickListener, View.OnTouchListener {
    private lateinit var csHeatBeat: CircularSeekBar
    private lateinit var addPostCollectionModel: AddPostCollectionModel
    var userChoosenTask: String? = null
    var imageFilePath: String? = null
    val REQUEST_CAMERA = 5
    val SELECT_FILE = 6
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    var itemFile: File? = null
    private lateinit var ivPostSelectedImage: AppCompatImageView
    private lateinit var flSelectedImageContainer: FrameLayout
    private var strHeartRate = "0"
    private var strSugar = "0"
    private var strWeight = "0"
    private var strSleep = "0"
    private var strMood = "0"

    private val addPostViewModel by lazy {
        ViewModelProvider(this).get(AddPostViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tab)
        showHomeFragment()
        //employerCodeDialog()
        ivHomeScreen.setOnClickListener(this)
        ivNewsFeed.setOnClickListener(this)
        ivCreatePost.setOnClickListener(this)
        ivExplore.setOnClickListener(this)
        ivChat.setOnClickListener(this)

        rlHomeScreen.setOnClickListener(this)
        rlNewsFeed.setOnClickListener(this)
        rlExplore.setOnClickListener(this)
        rlChat.setOnClickListener(this)

        addPostViewModel.successful.observe(this, androidx.lifecycle.Observer {
            dismissDialog()
            if (it) {
                setError(addPostViewModel.customResponse.msg)
            } else {
                setError(addPostViewModel.message)
            }
        })

        setUpPermissions()
    }

    private fun setUpPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissionsWeNeed =
                    arrayOf(Manifest.permission.CAMERA)
                requestPermissions(permissionsWeNeed, 88)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.HomeTabActivityContainer, fragment).commit()
    }

    fun hideKeyboard(view: View) {
        val inputManager = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view.windowToken, InputMethodManager
                .RESULT_UNCHANGED_SHOWN
        )
    }

    protected fun getRootView(): View {
        return window.decorView.findViewById(android.R.id.content)
    }

    fun replaceFragmentWithBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.HomeTabActivityContainer, fragment)
            .addToBackStack(fragment::getActivity.name).commit()
    }

    fun removeFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    private fun showHomeFragment() {
        ivHomeScreen.setColorFilter(
            ContextCompat.getColor(this, R.color.black),
            android.graphics.PorterDuff.Mode.MULTIPLY
        );
        replaceFragment(WeightReminderFragment())
    }

    fun showBottomSheetHowAreYouFeeling() {
        val mBottomSheetDialog =
            BottomSheetDialog(this, R.style.SheetDialog)
        val sheetView: View =
            layoutInflater.inflate(R.layout.dialog_layout_how_are_you_felling, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
    }


    @SuppressLint("ClickableViewAccessibility")
    fun heartBeatDialog() {
        val mBottomSheetDialog =
            BottomSheetDialog(this, R.style.SheetDialog)
        val sheetView: View =
            layoutInflater.inflate(R.layout.dialog_layout_heart_rate_monitor, null)
        csHeatBeat = sheetView.findViewById(R.id.csHeatBeat) as CircularSeekBar
        csHeatBeat.setOnTouchListener(this)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
    }

    fun employerCodeDialog() {
        val mBottomSheetDialog =
            BottomSheetDialog(this, R.style.SheetDialog)
        val sheetView: View =
            layoutInflater.inflate(R.layout.employer_code_layout, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
    }

    private fun createPostDialog() {
        addPostCollectionModel = AddPostCollectionModel()
        val mBottomSheetDialog =
            BottomSheetDialog(this, R.style.SheetDialog)
        val sheetView: View =
            layoutInflater.inflate(R.layout.dialog_layout_create_post, null)
        mBottomSheetDialog.setContentView(sheetView)

        ivPostSelectedImage =
            mBottomSheetDialog.findViewById(R.id.ivPostSelectedImage)!!

        flSelectedImageContainer =
            mBottomSheetDialog.findViewById(R.id.flSelectedImageContainer)!!

        val ivMyProfilePic =
            mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivMyProfilePic)

        val etPostTitle =
            mBottomSheetDialog.findViewById<AppCompatEditText>(R.id.etPostTitle)
        val etPostMessage =
            mBottomSheetDialog.findViewById<AppCompatEditText>(R.id.etPostMessage)
        val btPost = mBottomSheetDialog.findViewById<AppCompatButton>(R.id.btPost)
        val ivPicPostImage =
            mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivPicPostImage)
        val ivDeleteSelectedImage =
            mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivDeleteSelectedImage)

        val ivHeart = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivHeart)
        val ivBloodSugar = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivBloodSugar)
        val ivWeight = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivWeight)
        val ivSleep = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivSleep)
        val ivMood = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivMood)

        val ivTickHeart = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivTickHeart)
        val ivTickBloodSugar =
            mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivTickBloodSugar)
        val ivTickWeight = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivTickWeight)
        val ivTickSleep = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivTickSleep)
        val ivTickMood = mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivTickMood)
        val ivPostDismissDialog =
            mBottomSheetDialog.findViewById<AppCompatImageView>(R.id.ivPostDismissDialog)

        val signUpModel: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)

        if (!TextUtils.isEmpty(signUpModel.userInfo.profilePic)) {
            Glide.with(this)
                .load("https://hourlylancer.com/devlop/sulira/backend/assets/uploads/profilePic/" + signUpModel.userInfo.profilePic)
                .into(ivMyProfilePic!!)
        }

        ivPostDismissDialog?.setOnClickListener {
            mBottomSheetDialog.dismiss()
        }

        btPost?.setOnClickListener {
            val strPostTitle = etPostTitle!!.text.toString()
            val strPostMessage = etPostMessage!!.text.toString()

            if (TextUtils.isEmpty(strPostTitle)) {
                Toast.makeText(this, "Please enter what's in your mind", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var body: MultipartBody.Part? = null
            if (itemFile != null) {
                val requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), itemFile!!)
                body = MultipartBody.Part.createFormData(
                    "profilePic",
                    itemFile!!.name,
                    requestFile
                )
            }else{
                val attachmentEmpty =
                    RequestBody.create(MediaType.parse("text/plain"), "")
                body = MultipartBody.Part.createFormData(
                    "profilePic",
                   "",
                    attachmentEmpty
                )
            }

            addPostCollectionModel.postImage = body!!

            val userId: RequestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                PreferenceManager().userId.toString()
            )
            addPostCollectionModel.userId = userId
            val postTitle: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strPostTitle)
            addPostCollectionModel.postTitle = postTitle

            val postDecription: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strPostMessage)
            addPostCollectionModel.postDecription = postDecription

            val heartRate: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strHeartRate)
            addPostCollectionModel.heartRate = heartRate

            val sugar: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strSugar)
            addPostCollectionModel.sugar = sugar

            val weight: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strWeight)
            addPostCollectionModel.weight = weight

            val sleep: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strSleep)
            addPostCollectionModel.sleep = sleep

            val mood: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), strMood)
            addPostCollectionModel.mood = mood

            showDialog()
            addPostViewModel.addPost(addPostCollectionModel)
            mBottomSheetDialog.dismiss()
        }

        ivPicPostImage?.setOnClickListener {
            onClickSelectImage()
        }

        ivHeart?.setOnClickListener {
            when (ivTickHeart?.visibility) {
                View.VISIBLE -> {
                    ivTickHeart.visibility = View.GONE
                    strHeartRate = "0"
                }
                View.GONE -> {
                    ivTickHeart.visibility = View.VISIBLE
                    strHeartRate = "1"
                }
            }
        }

        ivBloodSugar?.setOnClickListener {
            when (ivTickBloodSugar?.visibility) {
                View.VISIBLE -> {
                    ivTickBloodSugar.visibility = View.GONE
                    strSugar = "0"
                }
                View.GONE -> {
                    ivTickBloodSugar.visibility = View.VISIBLE
                    strSugar = "1"
                }
            }
        }

        ivWeight?.setOnClickListener {
            when (ivTickWeight?.visibility) {
                View.VISIBLE -> {
                    ivTickWeight.visibility = View.GONE
                    strWeight = "0"
                }
                View.GONE -> {
                    ivTickWeight.visibility = View.VISIBLE
                    strWeight = "1"
                }
            }
        }

        ivSleep?.setOnClickListener {
            when (ivTickSleep?.visibility) {
                View.VISIBLE -> {
                    ivTickSleep.visibility = View.GONE
                    strSleep = "0"
                }
                View.GONE -> {
                    ivTickSleep.visibility = View.VISIBLE
                    strSleep = "1"
                }
            }
        }

        ivMood?.setOnClickListener {
            when (ivTickMood?.visibility) {
                View.VISIBLE -> {
                    ivTickMood.visibility = View.GONE
                    strMood = "0"
                }
                View.GONE -> {
                    ivTickMood.visibility = View.VISIBLE
                    strMood = "1"
                }
            }
        }

        ivDeleteSelectedImage?.setOnClickListener {
            itemFile = null
            flSelectedImageContainer.visibility = View.GONE
        }

        mBottomSheetDialog.show()
    }

    fun weightReminderDialog() {
        val mBottomSheetDialog =
            BottomSheetDialog(this, R.style.SheetDialog)
        val sheetView: View =
            layoutInflater.inflate(R.layout.activity_weight_reminder, null)
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()
    }

    override fun onClick(view: View?) {

        if (view == ivHomeScreen || view == ivNewsFeed || view == ivExplore || view == ivChat) {
            handleTabIcons(view)
        }

        when (view) {
            ivCreatePost -> {
                createPostDialog()
            }
        }
    }

    override fun onBackPressed() {
        val findFragmentById: Fragment? =
            supportFragmentManager.findFragmentById(R.id.HomeTabActivityContainer)
        if (findFragmentById is HomeScreenFragment) {
            exitAlert()
            return
        }

        if (findFragmentById is ExploreFragment || findFragmentById is NewsFeedFragment || findFragmentById is ChatMainFragment) {
            onClick(ivHomeScreen)
            return
        }

        supportFragmentManager.popBackStackImmediate()
    }

    private fun handleTabIcons(view: View?) {

        ivHomeScreen.setColorFilter(
            ContextCompat.getColor(
                this,
                if (view == ivHomeScreen || view == rlHomeScreen) R.color.black else R.color.darker_gray
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )

        ivNewsFeed.setColorFilter(
            ContextCompat.getColor(
                this,
                if (view == ivNewsFeed || view == rlNewsFeed) R.color.black else R.color.darker_gray
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )

        ivExplore.setColorFilter(
            ContextCompat.getColor(
                this,
                if (view == ivExplore || view == rlExplore) R.color.black else R.color.darker_gray
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )

        ivChat.setColorFilter(
            ContextCompat.getColor(
                this,
                if (view == ivChat || view == rlChat) R.color.black else R.color.darker_gray
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )

        when (view) {

            ivHomeScreen, rlHomeScreen -> {
                replaceFragment(HomeScreenFragment())
            }

            ivNewsFeed, rlNewsFeed -> {
                replaceFragment(NewsFeedFragment())
            }

            ivExplore, rlExplore -> {
                replaceFragment(ExploreFragment())
            }

            ivChat, rlChat -> {
                replaceFragment(ChatMainFragment())
            }
        }
    }

    fun showTabLayout() {
        homeTabLayout.visibility = View.VISIBLE
        val padding = resources.getDimensionPixelOffset(R.dimen.dim50dp)
        HomeTabActivityContainer.setPadding(0, 0, 0, padding)
    }

    fun hideTabLayout() {
        homeTabLayout.visibility = View.GONE
        val padding = resources.getDimensionPixelOffset(R.dimen.dim0dp)
        HomeTabActivityContainer.setPadding(0, 0, 0, 0)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, p1: MotionEvent?): Boolean {
        csHeatBeat.progress = 80
        return true
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

    private fun onClickSelectImage() {
        val items = arrayOf<CharSequence>(
            "Take Photo", "Choose from Library",
            "Cancel"
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Image")
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
            selectedImageUri?.let { ImageFilePath.getPath(applicationContext, it) }
        itemFile = File(picturePath)
        var bm: Bitmap? = null
        try {
            bm = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
            flSelectedImageContainer.visibility = View.VISIBLE
            ivPostSelectedImage.setImageBitmap(bm)
            // editProfileBinding.imgUserAvatar.rotation = 90F
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        val thumbnail = BitmapFactory.decodeFile(imageFilePath) as Bitmap
        itemFile = File(imageFilePath)
        try {
            flSelectedImageContainer.visibility = View.VISIBLE
            ivPostSelectedImage.setImageBitmap(thumbnail)
            //editProfileBinding.imgUserAvatar.rotation = 90F
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun galleryIntent() {
        if (CommonUtils.checkPermission(this)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT //
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun cameraIntent() {
        if (CommonUtils.checkPermissionCamera(this)) {
            val pictureIntent = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
            )
            if (pictureIntent.resolveActivity(this.packageManager) != null) { //Create a itemFile to store the image
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) { // Error occurred while creating the File
                }
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        this,
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
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
        imageFilePath = image.absolutePath
        return image
    }

    private fun exitAlert() {
        AlertDialog.Builder(this)
            .setTitle("Alert!")
            .setMessage("Are you sure? You want to exit from the app")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                dialog.dismiss()
            }.setNegativeButton(
                android.R.string.no
            ) { dialog, which ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }


}
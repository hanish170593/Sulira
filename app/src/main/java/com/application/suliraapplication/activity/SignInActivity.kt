package com.application.suliraapplication.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseActivity
import com.application.suliraapplication.network.BaseUrl
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.utils.MyFirebaseMessagingService
import com.application.suliraapplication.viewmodels.GetUserInfoViewModel
import com.application.suliraapplication.viewmodels.LoginViewModel
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.google.gson.Gson
import com.ra.fingerprint_auth.FingerprintCallback
import com.ra.fingerprint_auth.FingerprintManager
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : BaseActivity(), FingerprintCallback, View.OnClickListener {

    private val loginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private val getUserInfoViewModel by lazy {
        ViewModelProvider(this).get(GetUserInfoViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart------B","onStart")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate------B","onCreate")
        setContentView(R.layout.activity_sign_in)
        btLogin.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)

        if (PreferenceManager().fingerPrintEnable!!){
            FingerprintManager.FingerprintBuilder(this)
                .setTitle("Login Authentication")
                .setSubtitle("Please place your finger")
                .setDescription("Place your finger on finger print scanner")
                .setNegativeButtonText("cancel")
                .build().authenticate(this)
        }

        tvForgetPassword.setOnClickListener(this)

        ivBack.setOnClickListener(this)

        loginViewModel.successful.observe(this, Observer { successful ->
            dismissDialog()
            if (successful) {
                if (loginViewModel.loginResponse.status == BaseUrl.SUCCESS) {

                    PreferenceManager().saveuserData(Gson().toJson(loginViewModel.loginResponse))
                    PreferenceManager().userId(loginViewModel.loginResponse.userInfo.id)
                    PreferenceManager().stripeToken(loginViewModel.loginResponse.userInfo.stripeToken)
                    startActivity(Intent(this, HomeTabActivity::class.java))

                } else {
                    setError(loginViewModel.loginResponse.msg)
                }
            } else {
                setError(loginViewModel.message)
            }

        })

        getUserInfoViewModel.successful.observe(this, Observer {
            dismissDialog()

            if (it) {
                PreferenceManager().saveuserData(Gson().toJson(getUserInfoViewModel.signUpModel))
                PreferenceManager().userId(getUserInfoViewModel.signUpModel.userInfo.id)
                PreferenceManager().stripeToken(getUserInfoViewModel.signUpModel.userInfo.stripeToken)
                startActivity(Intent(this, HomeTabActivity::class.java))
            } else {
                setError(getUserInfoViewModel.message)
            }
        })

        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                MyFirebaseMessagingService.strDeviceToken= instanceIdResult.token
            }

        /*FirebaseInstallations.getInstance().getToken(false).addOnSuccessListener {
            MyFirebaseMessagingService.strDeviceToken=it.token
            Log.d("FirebaseInstallations","------->"+it.token+"<-------")
        }*/

    }

    override fun onAuthenticationCancelled() {
        setError("Authentication Cancelled")
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        setError("Authentication Error")
    }

    override fun onAuthenticationFailed() {
        setError("Authentication Failed")
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
    }

    override fun onAuthenticationSuccessful() {
        showDialog()
        getUserInfoViewModel.getUserInfo(PreferenceManager().userId.toString())
    }

    override fun onBiometricAuthenticationInternalError(error: String?) {
        Toast.makeText(this, "onBiometricAuthenticationInternalError", Toast.LENGTH_SHORT).show()
    }

    override fun onBiometricAuthenticationNotAvailable() {
        Toast.makeText(this, "onBiometricAuthenticationNotAvailable", Toast.LENGTH_SHORT).show()
    }

    override fun onBiometricAuthenticationNotSupported() {
        Toast.makeText(this, "onBiometricAuthenticationNotSupported", Toast.LENGTH_SHORT).show()
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(this, "onBiometricAuthenticationPermissionNotGranted", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSdkVersionNotSupported() {
        Toast.makeText(this, "onSdkVersionNotSupported", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when (view) {
            btLogin -> validateEnteredData()
            ivBack -> onBackPressed()
            tvSignUp -> startActivity(Intent(this, RegistrationContainerActivity::class.java))
            tvForgetPassword -> startActivity(
                Intent(
                    this,
                    ForgetPasswordMobileNumberActivity::class.java
                )
            )
        }
    }

    private fun validateEnteredData() {
        val userName = etUserName.text.toString()
        val userPassword = etUserPassword.text.toString()

        if (TextUtils.isEmpty(userName)) {
            etUserName.error = getString(R.string.enter_username)
            etUserName.isFocusable = true
            return
        }

        if (TextUtils.isEmpty(userPassword)) {
            etUserPassword.error = getString(R.string.enter_password)
            etUserPassword.isFocusable = true
            return
        }

        var userLoginType = ""
        var userPhone = ""
        var userEmail = ""

        if (userName.contains("@")) {
            userLoginType = "email"
            userEmail = userName
        } else {
            userLoginType = "phone"
            userPhone = userName
        }

        showDialog()
        loginViewModel.login(
            userPhone,
            userEmail,
            userLoginType,
            userPassword,
            MyFirebaseMessagingService.strDeviceToken,
            "Android"
        )
    }
}
package com.application.suliraapplication.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseActivity
import com.application.suliraapplication.network.BaseUrl
import com.application.suliraapplication.viewmodels.OTPforForgetPasswordViewModel
import kotlinx.android.synthetic.main.activity_forget_password_mobile_number.*

class ForgetPasswordMobileNumberActivity : BaseActivity(), View.OnClickListener {

    private val forgetPasswordOTPViewModel by lazy {
        ViewModelProvider(this).get(OTPforForgetPasswordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_mobile_number)

        forgetPasswordOTPViewModel.successful.observe(this, Observer { successful ->
            dismissDialog()
            if (successful) {
                if (forgetPasswordOTPViewModel.otpModel.status == BaseUrl.SUCCESS) {
                    startActivity(
                        Intent(
                            this,
                            ForgetPasswordOTPActivity::class.java
                        ).putExtra("number", etForgetPassMobileNumber.text.toString())
                            .putExtra("otp", forgetPasswordOTPViewModel.otpModel.otp)
                    )
                } else {
                    setError(forgetPasswordOTPViewModel.otpModel.msg)
                }
            } else {
                setError(forgetPasswordOTPViewModel.message)
            }

        })
        ivForgetOneBack.setOnClickListener(this)
        btForgetVerifyMob.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view){
            ivForgetOneBack ->onBackPressed()
            btForgetVerifyMob ->{
                if (TextUtils.isEmpty(etForgetPassMobileNumber.text.toString())) {
                    etForgetPassMobileNumber.error=getString(R.string.enter_your_mobile_number)
                    etForgetPassMobileNumber.isFocusable=true
                    return
                }

                showDialog()
                forgetPasswordOTPViewModel.otpForForgetPassword(etForgetPassMobileNumber.text.toString())
            }
        }
    }
}
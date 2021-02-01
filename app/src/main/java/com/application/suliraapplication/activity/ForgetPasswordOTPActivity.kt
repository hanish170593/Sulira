package com.application.suliraapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseActivity
import com.application.suliraapplication.network.BaseUrl
import com.application.suliraapplication.viewmodels.OTPforForgetPasswordViewModel
import kotlinx.android.synthetic.main.activity_forget_password_mobile_number.*
import kotlinx.android.synthetic.main.activity_forget_password_o_t_p.*

class ForgetPasswordOTPActivity : BaseActivity(), View.OnClickListener {
    private var number = ""
    private var otp = ""

    private val forgetPasswordOTPViewModel by lazy {
        ViewModelProvider(this).get(OTPforForgetPasswordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_o_t_p)

        intent.let {
            number = it.getStringExtra("number").toString()
            otp = it.getStringExtra("otp").toString()
        }

        tvNumberForgetWeCanUse.text = "Wel’ll text you on $number"
        pinviewForForget.value = otp

        tvSendNewCode.setOnClickListener(this)
        btOtpForgetContinue.setOnClickListener(this)
        ivForgetTwoBack.setOnClickListener(this)

        forgetPasswordOTPViewModel.successful.observe(this, Observer { successful ->
            dismissDialog()
            if (successful) {
                if (forgetPasswordOTPViewModel.otpModel.status == BaseUrl.SUCCESS) {
                    otp = forgetPasswordOTPViewModel.otpModel.otp
                    setError(forgetPasswordOTPViewModel.otpModel.msg)
                } else {
                    setError(forgetPasswordOTPViewModel.otpModel.msg)
                }
            } else {
                setError(forgetPasswordOTPViewModel.message)
            }
        })
    }

    override fun onClick(view: View?) {
        when (view) {
            tvSendNewCode -> {
                showDialog()
                forgetPasswordOTPViewModel.otpForForgetPassword(etForgetPassMobileNumber.text.toString())
            }

            btOtpForgetContinue -> validateData()
            ivForgetTwoBack -> onBackPressed()
        }
    }

    private fun validateData() {
        val value: String = pinviewForForget.value.toString()
        if (value != otp) {
            setError("Please enter valid OTP")
            return
        }

        startActivity(
            Intent(this, ForgetPasswordActivity::class.java).putExtra(
                "mobileNumber",
                number
            ).putExtra("otp", otp)
        )
    }
}
package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.viewmodels.VerifyMobileNumberViewModel
import kotlinx.android.synthetic.main.activity_mobile_number.*
import okhttp3.MediaType
import okhttp3.RequestBody

class MobileNumberFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_mobile_number, container, false)
    }

    private val verifyMobileNumberViewModel by lazy {
        ViewModelProvider(this).get(VerifyMobileNumberViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btVerifyMob.setOnClickListener {
            progressPercentage=1
            val mobileNumber = etMobileNumber.text.toString()

            if (TextUtils.isEmpty(mobileNumber)){
                etMobileNumber.error=getString(R.string.enter_your_mobile_number)
                etMobileNumber.isFocusable=true
                return@setOnClickListener
            }

            val number: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),mobileNumber)
            signUpDataClass.phone=number
            showDialog()
            verifyMobileNumberViewModel.verifyMobileNumber(mobileNumber)
        }

        verifyMobileNumberViewModel.successful.observe(viewLifecycleOwner, Observer {successful ->
            dismissDialog()
            if (successful){
                (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                    OTPVerificationFragment.newInstance(
                        verifyMobileNumberViewModel.otpModel.otp.toString()
                    )
                )
            }else{
                setError(verifyMobileNumberViewModel.message)
            }

        })

    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(false, "")
    }
}
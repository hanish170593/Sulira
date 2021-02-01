package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.base.BaseFragment
import kotlinx.android.synthetic.main.activity_o_t_p_verification.*

private const val ARG_PARAM1 = "otp"
class OTPVerificationFragment : BaseFragment() {
    private var otpVerify: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_o_t_p_verification, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(otp: String) =
            OTPVerificationFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, otp)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            otpVerify = it.getString(ARG_PARAM1)
        }

        btOtpContinue.setOnClickListener {
            progressPercentage=2
            val value:String = pinview.value
            if (value == otpVerify){
                (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(PasswordFragment())
            }else{
                setError("Please enter valid OTP")
            }
        }

        pinview.value=otpVerify
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(false, "")
    }
}
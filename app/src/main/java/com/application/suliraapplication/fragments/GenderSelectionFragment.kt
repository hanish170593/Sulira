package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.base.BaseFragment
import kotlinx.android.synthetic.main.activity_gender_selection.*
import okhttp3.MediaType
import okhttp3.RequestBody

class GenderSelectionFragment : BaseFragment(), View.OnClickListener {

    private var strGender:String?=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_gender_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btGenderContinue.setOnClickListener(this)
        flMale.setOnClickListener(this)
        flFemale.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Skip")
    }

    override fun onClick(view: View?) {

        when (view) {

            btGenderContinue -> {
                progressPercentage=4

                if (TextUtils.isEmpty(strGender)){
                    setError("Please select gender")
                    return
                }

                val gender: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),strGender)
                signUpDataClass.gender=gender
                (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                    SelectWeightFragment()
                )
            }

            flMale -> {
                ivMaleSelected.visibility=View.VISIBLE
                ivFemaleSelected.visibility=View.GONE
                strGender="male"
            }

            flFemale -> {
                ivMaleSelected.visibility=View.GONE
                ivFemaleSelected.visibility=View.VISIBLE
                strGender="female"
            }
        }

    }
}
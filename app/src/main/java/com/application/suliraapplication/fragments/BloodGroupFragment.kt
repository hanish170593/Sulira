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
import kotlinx.android.synthetic.main.activity_blood_group.*
import okhttp3.MediaType
import okhttp3.RequestBody

class BloodGroupFragment : BaseFragment(), View.OnClickListener {

    private var strBloodGroup:String?=""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_blood_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btAPositive.setOnClickListener(this)
        btANegative.setOnClickListener(this)
        btBPositive.setOnClickListener(this)
        btBNegative.setOnClickListener(this)
        btOPositive.setOnClickListener(this)
        btoNegative.setOnClickListener(this)
        btabPositive.setOnClickListener(this)
        btAbNegative.setOnClickListener(this)
        btBloodContinue.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Next")
    }

    //✔

    private fun handleSelection(view: View) {
        btAPositive.text =
            if (view == btAPositive) btAPositive.text.toString() + " ✔" else btAPositive.text.toString()
                .split(" ✔")[0].trim()
        btANegative.text =
            if (view == btANegative) btANegative.text.toString() + " ✔" else btANegative.text.toString()
                .split(" ✔")[0].trim()
        btBPositive.text =
            if (view == btBPositive) btBPositive.text.toString() + " ✔" else btBPositive.text.toString()
                .split(" ✔")[0].trim()
        btBNegative.text =
            if (view == btBNegative) btBNegative.text.toString() + " ✔" else btBNegative.text.toString()
                .split(" ✔")[0].trim()
        btOPositive.text =
            if (view == btOPositive) btOPositive.text.toString() + " ✔" else btOPositive.text.toString()
                .split(" ✔")[0].trim()
        btoNegative.text =
            if (view == btoNegative) btoNegative.text.toString() + " ✔" else btoNegative.text.toString()
                .split(" ✔")[0].trim()
        btabPositive.text =
            if (view == btabPositive) btabPositive.text.toString() + " ✔" else btabPositive.text.toString()
                .split(" ✔")[0].trim()
        btAbNegative.text =
            if (view == btAbNegative) btAbNegative.text.toString() + " ✔" else btAbNegative.text.toString()
                .split(" ✔")[0].trim()

        when(view){
            btAPositive ->strBloodGroup="A+"
            btANegative ->strBloodGroup="A-"
            btBPositive ->strBloodGroup="B+"
            btBNegative ->strBloodGroup="B-"
            btOPositive ->strBloodGroup="O+"
            btoNegative ->strBloodGroup="O-"
            btabPositive ->strBloodGroup="AB+"
            btAbNegative ->strBloodGroup="AB-"
        }
    }

    override fun onClick(viewClick: View?) {
        when (viewClick) {
            btAPositive,
            btANegative,
            btBPositive,
            btBNegative,
            btOPositive,
            btoNegative,
            btabPositive,
            btAbNegative -> handleSelection(viewClick!!)
            btBloodContinue -> {
                if (TextUtils.isEmpty(strBloodGroup)){
                    setError("Please select Blood Group")
                    return
                }
                progressPercentage = 7
                val bloodGroup: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),strBloodGroup)
                signUpDataClass.bloodGroup=bloodGroup
                (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(ExerciseFragment())
            }
        }
    }
}
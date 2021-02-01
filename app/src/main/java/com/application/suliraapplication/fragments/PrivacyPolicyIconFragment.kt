package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.base.BaseFragment
import kotlinx.android.synthetic.main.activity_privacy_policy_icon.*

class PrivacyPolicyIconFragment : BaseFragment(), CompoundButton.OnCheckedChangeListener {

    private var isAccepted:Boolean?=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_privacy_policy_icon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btContinue.setOnClickListener {

            if (!isAccepted!!){
                setError("Please accept the term & condition")
                return@setOnClickListener
            }

            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                FaceRecognizationFragment()
            )
        }

        cbChecked.setOnCheckedChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(false, "")
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        isAccepted=p1
    }
}
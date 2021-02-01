package com.application.suliraapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.viewmodels.PolicyViewModel
import kotlinx.android.synthetic.main.fragment_privacy_and_policy.*

class PrivacyAndPolicyFragment : BaseFragment() {

    private val privacyAndPolicyViewModel by lazy {
        ViewModelProvider(this).get(PolicyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_privacy_and_policy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        privacyAndPolicyViewModel.successful.observe(viewLifecycleOwner, Observer { successful ->
             dismissDialog()
            if (successful){
                tvPrivacyAndPolicy.text=privacyAndPolicyViewModel.policyModel.policy.privacyPolicy
            }else{
                setError(privacyAndPolicyViewModel.message)
            }
        })

        showDialog()
        privacyAndPolicyViewModel.policyData()
    }

}
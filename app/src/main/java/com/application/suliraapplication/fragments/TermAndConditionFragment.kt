package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.viewmodels.PolicyViewModel
import kotlinx.android.synthetic.main.fragment_privacy_and_policy.*
import kotlinx.android.synthetic.main.fragment_term_and_condition.*


class TermAndConditionFragment : BaseFragment() {

    private val termsAndConditionViewModel by lazy {
        ViewModelProvider(this).get(PolicyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_term_and_condition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        termsAndConditionViewModel.successful.observe(viewLifecycleOwner, Observer{ successful ->
            dismissDialog()
            if (successful){
                tvTermAndCondition.text=termsAndConditionViewModel.policyModel.policy.termsCondiation
            }else{
                setError(termsAndConditionViewModel.message)
            }
        })

        showDialog()
        termsAndConditionViewModel.policyData()
    }

}
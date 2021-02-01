package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.network.BaseUrl
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.ChangePasswordViewModel
import kotlinx.android.synthetic.main.fragment_reset_password.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class ResetPasswordFragment : BaseFragment(), TextWatcher, View.OnClickListener {

    private val changePasswordViewModel by lazy {
        ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
    }

    private var isStrongPassword: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tiNewPassword.editText?.addTextChangedListener(this)

        changePasswordViewModel.successful.observe(viewLifecycleOwner, Observer { successful ->
            dismissDialog()
            if (successful) {
                if (changePasswordViewModel.customResponse.status == BaseUrl.SUCCESS) {
                    setError(changePasswordViewModel.customResponse.msg)
                    (activity as HomeTabActivity).onBackPressed()
                } else {
                    setError(changePasswordViewModel.customResponse.msg)
                }
            } else {
                setError(changePasswordViewModel.message)
            }
        })

        btChangePassword.setOnClickListener(this)
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(textData: CharSequence?, p1: Int, p2: Int, p3: Int) {
        var strongNess: Int = 0
        if (textData!!.length >= 8) {
            strongNess++
        }

        var ch: Char
        val str: String = textData.toString()

        val length = str.length
        val pattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(str)
        val find: Boolean = matcher.find()

        if (find) {
            strongNess++
        }

        loop@ for (i in 0 until length) {
            ch = str[i]
            when {
                Character.isDigit(ch) -> {
                    strongNess++
                    break@loop
                }
            }
        }

        loop@ for (i in 0 until length) {
            ch = str[i]
            when {
                Character.isUpperCase(ch) -> {
                    strongNess++
                    break@loop
                }
                /* Character.isLowerCase(ch) -> {
                     lowerCaseFlag = true
                 }*/
            }
        }

        isStrongPassword = strongNess > 3

        ivStrongOne.setImageDrawable(
            if (strongNess > 0) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )

        ivStrongTwo.setImageDrawable(
            if (strongNess > 1) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )

        ivStrongThree.setImageDrawable(
            if (strongNess > 2) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )

        ivStrongFour.setImageDrawable(
            if (strongNess > 3) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )

    }

    override fun onClick(view: View?) {
        when (view) {
            btChangePassword -> {
                validateEnteredData()
            }
        }
    }

    private fun validateEnteredData() {
        val strOldPassword = etOldPassword.text.toString()
        val strNewPassword = etNewPassword.text.toString()
        val strConfirmNewPassword = etConfirmNewPassword.text.toString()

        if (TextUtils.isEmpty(strOldPassword)){
            etOldPassword.error=getString(R.string.enter_your_password)
            etOldPassword.isFocusable=true
            return
        }

        if (TextUtils.isEmpty(strNewPassword)){
            etNewPassword.error=getString(R.string.enter_your_password)
            etNewPassword.isFocusable=true
            return
        }

        if (!isStrongPassword!!){
            etNewPassword.error=getString(R.string.please_enter_strong_password)
            etNewPassword.isFocusable=true
            return
        }

        if (TextUtils.isEmpty(strConfirmNewPassword)){
            etConfirmNewPassword.error=getString(R.string.please_enter_strong_password)
            etConfirmNewPassword.isFocusable=true
            return
        }

        if (strConfirmNewPassword != strNewPassword){
            etConfirmNewPassword.error=getString(R.string.password_not_matched)
            etConfirmNewPassword.isFocusable=true
            return
        }

        showDialog()
        changePasswordViewModel.changePassword(PreferenceManager().userId.toString(),strOldPassword,strNewPassword)
    }

}
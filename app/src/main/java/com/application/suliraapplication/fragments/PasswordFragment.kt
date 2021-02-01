package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import kotlinx.android.synthetic.main.activity_password.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.regex.Matcher
import java.util.regex.Pattern


class PasswordFragment : Fragment(), TextWatcher {

    private var isStrong:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btSetPassContinue.setOnClickListener {
            progressPercentage=3
            if (!isStrong){
                etSetPassword.error="Please enter strong password"
                etSetPassword.isFocusable=true
                return@setOnClickListener
            }
            val password: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),etSetPassword.text.toString())
            signUpDataClass.password=password

            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                PrivacyPolicyIconFragment()
            )
        }

        tiSetPassword.editText?.addTextChangedListener(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(false, "")
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
        isStrong= strongNess >3
        ivProgressOne.setImageDrawable(
            if (strongNess > 0) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )
        ivProgressTwo.setImageDrawable(
            if (strongNess > 1) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )
        ivProgressThree.setImageDrawable(
            if (strongNess > 2) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )
        ivProgressFour.setImageDrawable(
            if (strongNess > 3) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.filled
            ) else ContextCompat.getDrawable(requireContext(), R.drawable.not_filled)
        )

    }
}
package com.application.suliraapplication.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.base.BaseActivity
import com.application.suliraapplication.network.BaseUrl
import com.application.suliraapplication.viewmodels.ForgetPasswordViewModel
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.forget_password_activity.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.regex.Matcher
import java.util.regex.Pattern


class ForgetPasswordActivity : BaseActivity(), TextWatcher, View.OnClickListener {

    private var isStrong:Boolean = false
    private var number = ""
    private var otp = ""

    private val forgetPasswordViewModel by lazy {
        ViewModelProvider(this).get(ForgetPasswordViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password_activity)

        intent.let {
            number = it.getStringExtra("mobileNumber").toString()
            otp = it.getStringExtra("otp").toString()
        }

        ivForgetLastBack.setOnClickListener(this)
        btForgetPassContinue.setOnClickListener(this)

        tiForgetPassword.editText?.addTextChangedListener(this)

        forgetPasswordViewModel.successful.observe(this, Observer {successful->
              dismissDialog()
            if (successful){

                if (forgetPasswordViewModel.customResponse.status == BaseUrl.SUCCESS){
                    setError(forgetPasswordViewModel.customResponse.msg)
                    startActivity(Intent(this,SignInActivity::class.java))
                }else{
                    setError(forgetPasswordViewModel.customResponse.msg)
                }

            }else{
                setError(forgetPasswordViewModel.message)
            }

        })
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
        ivProgressOneForget.setImageDrawable(
            if (strongNess > 0) ContextCompat.getDrawable(
                this,
                R.drawable.filled
            ) else ContextCompat.getDrawable(this, R.drawable.not_filled)
        )
        ivProgressTwoForget.setImageDrawable(
            if (strongNess > 1) ContextCompat.getDrawable(
                this,
                R.drawable.filled
            ) else ContextCompat.getDrawable(this, R.drawable.not_filled)
        )
        ivProgressThreeForget.setImageDrawable(
            if (strongNess > 2) ContextCompat.getDrawable(this, R.drawable.filled) else ContextCompat.getDrawable(this, R.drawable.not_filled)
        )
        ivProgressFourForget.setImageDrawable(
            if (strongNess > 3) ContextCompat.getDrawable(
                this,
                R.drawable.filled
            ) else ContextCompat.getDrawable(this, R.drawable.not_filled)
        )
    }

    override fun onClick(view: View?) {
        when(view){
            ivForgetLastBack -> onBackPressed()
            btForgetPassContinue ->{
                progressPercentage=3
                if (!isStrong){
                    etForgetPassword.error="Please enter strong password"
                    etForgetPassword.isFocusable=true
                    return
                }

                showDialog()
                forgetPasswordViewModel.forgetPassword(number,otp,etForgetPassword.text.toString())

            }
        }
    }
}
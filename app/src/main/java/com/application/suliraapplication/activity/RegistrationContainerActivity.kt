package com.application.suliraapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.fragments.*
import com.application.suliraapplication.pojo.SignUpDataClass
import kotlinx.android.synthetic.main.activity_registration_container.*
import okhttp3.MediaType
import okhttp3.RequestBody
import com.application.suliraapplication.fragments.FaceRecognizationFragment as FaceRecognizationFragment1

class RegistrationContainerActivity : AppCompatActivity(), View.OnClickListener,
    View.OnTouchListener {

    companion object{
        var signUpDataClass: SignUpDataClass=SignUpDataClass()
        var progressPercentage: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_container)
        showHomeFragment()

        ivBackRegistration.setOnClickListener(this)
        tvSkipOrNext.setOnClickListener(this)

        slRegistrationProgress.setOnTouchListener(this)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_registration_cont, fragment).commit()
    }

    fun hideKeyboard(view: View) {
        val inputManager = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view.windowToken, InputMethodManager
                .RESULT_UNCHANGED_SHOWN
        )
    }

    fun getRootView(): View {
        return window.decorView.findViewById(android.R.id.content)
    }

    fun replaceFragmentWithBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_registration_cont, fragment)
            .addToBackStack(fragment::getActivity.name).commit()
        try {
            slRegistrationProgress.value = progressPercentage.toFloat()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun removeFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    private fun showHomeFragment() {
        replaceFragment(MobileNumberFragment())
    }

    override fun onClick(view: View?) {

        when (view) {

            ivBackRegistration -> {
                onBackPressed()
            }

            tvSkipOrNext -> {
                handleSkipOrNext()
            }
        }
    }

    private fun handleSkipOrNext() {
        val emptyString: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), "")

        val emptyArray: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), "[]")
        val findFragmentById: Fragment? =
            supportFragmentManager.findFragmentById(R.id.fl_registration_cont)

        if (findFragmentById is FaceRecognizationFragment1) {
            replaceFragmentWithBackStack(GenderSelectionFragment())
        }

        if (findFragmentById is GenderSelectionFragment) {
            signUpDataClass.gender= emptyString
            progressPercentage = 4
            replaceFragmentWithBackStack(SelectWeightFragment())
        }

        if (findFragmentById is SelectWeightFragment) {
            signUpDataClass.weight= emptyString
            progressPercentage = 5
            replaceFragmentWithBackStack(HeightFragment())
        }

        if (findFragmentById is HeightFragment) {
            signUpDataClass.height= emptyString
            progressPercentage = 6
            replaceFragmentWithBackStack(BloodGroupFragment())
        }

        if (findFragmentById is BloodGroupFragment) {
            signUpDataClass.bloodGroup= emptyString
            progressPercentage = 7
            replaceFragmentWithBackStack(ExerciseFragment())
        }

        if (findFragmentById is ExerciseFragment) {
            signUpDataClass.exercise= emptyString
            signUpDataClass.exerciseName= emptyString
            progressPercentage = 8
            replaceFragmentWithBackStack(InterestFragment())
        }

        if (findFragmentById is InterestFragment) {
            signUpDataClass.interestArray= emptyArray
            progressPercentage = 9
            replaceFragmentWithBackStack(HowCanHelpFragment())
        }

        if (findFragmentById is HowCanHelpFragment) {
            signUpDataClass.interestArray= emptyArray
            progressPercentage = 10
            replaceFragmentWithBackStack(HealthConditionFragment())
        }

        if (findFragmentById is HealthConditionFragment) {
            signUpDataClass.interestArray= emptyArray
            progressPercentage = 11
            replaceFragmentWithBackStack(TurnOnNotificationFragment())
        }

        if (findFragmentById is TurnOnNotificationFragment) {
            signUpDataClass.interestArray= emptyString
            progressPercentage = 12
            replaceFragmentWithBackStack(SignUpProfileFragment())
        }

        if (findFragmentById is SignUpProfileFragment) {
            progressPercentage = 13
            startActivity(Intent(this, GetStartedActivity::class.java))
        }

    }

    fun handleSkipOrNextBtnVisibility(shouldVisible: Boolean, text: String) {
        tvSkipOrNext.visibility = if (shouldVisible) View.VISIBLE else View.GONE
        tvSkipOrNext.text = text
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        slRegistrationProgress.value = progressPercentage.toFloat()
        return true
    }

}
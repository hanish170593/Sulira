package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.base.BaseFragment
import kotlinx.android.synthetic.main.activity_excersize_fragment.*
import okhttp3.MediaType
import okhttp3.RequestBody

class ExerciseFragment : BaseFragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    var excercise = arrayOf("Twice a week", "Once a week", "Daily", "Alternate Day")
    private  var strExercise:String?=""
    private  var strIsDoExercise:String?="yes"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_excersize_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Next")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btExerciseYes.setOnClickListener(this)
        btExerciseNo.setOnClickListener(this)
        btExerciseContinue.setOnClickListener(this)

        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, excercise)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(spnIfYes) {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@ExerciseFragment
            prompt = "Select How Often"
            gravity = Gravity.CENTER
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(item: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        strExercise=excercise[p2]
    }

    override fun onClick(viewClick: View?) {

        when (viewClick) {
            btExerciseContinue -> {

                if (strIsDoExercise == "yes"){

                    if (strExercise == "Select How Often"){
                        setError("Please Select How often")
                        return
                    }

                }

                progressPercentage=8
                val exercise: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),strExercise)
                val isDoExercise: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),strIsDoExercise)
                signUpDataClass.exerciseName=exercise
                signUpDataClass.exercise=isDoExercise
                (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                    InterestFragment()
                )
            }

            btExerciseYes,
            btExerciseNo -> handleExerciseType(viewClick!!)
        }
    }

    private fun handleExerciseType(clickView: View) {
        btExerciseYes.background =
            if (clickView == btExerciseYes) ContextCompat.getDrawable(requireContext(), R.drawable.rect_fill_back)
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btExerciseNo.background =
            if (clickView == btExerciseNo) ContextCompat.getDrawable(requireContext(), R.drawable.rect_fill_back)
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btExerciseYes.setTextColor(if (clickView == btExerciseYes) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))
        btExerciseNo.setTextColor(if (clickView == btExerciseNo) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))

        llSpn.visibility=if (clickView == btExerciseYes) View.VISIBLE else View.GONE
        ifYes.visibility=if (clickView == btExerciseYes) View.VISIBLE else View.GONE

        strIsDoExercise=if (clickView == btExerciseYes) "yes" else "no"

    }
}
package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.utils.SimpleRulerView
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import kotlinx.android.synthetic.main.activity_select_weight.*
import okhttp3.MediaType
import okhttp3.RequestBody

class SelectWeightFragment : Fragment(), SimpleRulerView.OnValueChangeListener,
    View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_select_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btWeightContinue.setOnClickListener {
            progressPercentage = 5
            val weight: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),tvWeight.text.toString())
            signUpDataClass.weight=weight
            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                HeightFragment()
            )
        }

       /* heightRuler.setOnValueChangeListner(this)
        heightRuler.setSelectedIndex(0)*/

        weightRulerValuePicker.indicatorColor =
            ContextCompat.getColor(requireContext(), R.color.app_color)
        weightRulerValuePicker.textColor = ContextCompat.getColor(requireContext(), R.color.app_color)
        weightRulerValuePicker.notchColor = ContextCompat.getColor(requireContext(), R.color.app_color)
        weightRulerValuePicker.setNotchColorRes(R.color.app_color)
        weightRulerValuePicker.setMinMaxValue(20, 500)
        weightRulerValuePicker.selectValue(120)
        weightRulerValuePicker.setIndicatorIntervalDistance(25)

        weightRulerValuePicker.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                //Value changed and the user stopped scrolling the ruler.
                //You can consider this value as final selected value.

            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                //Value changed but the user is still scrolling the ruler.
                //This value is not final value. You can utilize this value to display the current selected value.
                try {
                    tvWeight.text = selectedValue.toString()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        })

        btLbs.setOnClickListener(this)
        btKgs.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Next")
    }

    override fun onChange(view: SimpleRulerView?, position: Int, value: Float) {
        val data = value.toString().split(".")[0]
        tvWeight.text = data
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            btLbs,
            btKgs -> {
                handleWeightType(clickView!!)
            }
        }

    }

    private fun handleWeightType(clickView: View) {
        btLbs.background =
            if (clickView == btLbs) ContextCompat.getDrawable(requireContext(), R.drawable.rect_fill_back)
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btKgs.background =
            if (clickView == btKgs) ContextCompat.getDrawable(requireContext(), R.drawable.rect_fill_back)
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btLbs.setTextColor(if (clickView == btLbs) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))
        btKgs.setTextColor(if (clickView == btKgs) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))
        tvWeightScaleType.text = if (clickView == btLbs) "LBS" else "KG"
    }
}
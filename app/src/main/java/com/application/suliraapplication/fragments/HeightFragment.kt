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
import kotlinx.android.synthetic.main.activity_height.*
import okhttp3.MediaType
import okhttp3.RequestBody

class HeightFragment : Fragment(), SimpleRulerView.OnValueChangeListener, View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_height, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Next")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*vHeightRuler.setOnValueChangeListener(this)
        vHeightRuler.setSelectedIndex(0)
        vHeightRuler.setIntervalDis(14f)
*/
        rulerValuePicker.indicatorColor = ContextCompat.getColor(requireContext(), R.color.app_color)
        rulerValuePicker.textColor = ContextCompat.getColor(requireContext(), R.color.app_color)
        rulerValuePicker.notchColor = ContextCompat.getColor(requireContext(), R.color.app_color)
        rulerValuePicker.setNotchColorRes(R.color.app_color)
        rulerValuePicker.setMinMaxValue(50, 300)
        rulerValuePicker.selectValue(165)
        rulerValuePicker.setIndicatorIntervalDistance(25)

        rulerValuePicker.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                //Value changed and the user stopped scrolling the ruler.
                //You can consider this value as final selected value.

            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                //Value changed but the user is still scrolling the ruler.
                //This value is not final value. You can utilize this value to display the current selected value.
                try {
                    tvHeightT.text = selectedValue.toString()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        })

        btHeightContinue.setOnClickListener {
            progressPercentage=6
            val height: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),tvHeightT.text.toString())
            signUpDataClass.height=height
            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(BloodGroupFragment())
        }

        btFts.setOnClickListener(this)
        btCm.setOnClickListener(this)
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            btFts,
            btCm -> {
                handleWeightType(clickView!!)
            }

        }

    }

    override fun onChange(view: SimpleRulerView?, position: Int, value: Float) {
        val data = value.toString().split(".")[0]
        tvHeightT.text = data
    }

    private fun handleWeightType(clickView: View) {
        btFts.background =
            if (clickView == btFts) ContextCompat.getDrawable(requireContext(), R.drawable.rect_fill_back)
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btCm.background =
            if (clickView == btCm) ContextCompat.getDrawable(requireContext(), R.drawable.rect_fill_back)
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btFts.setTextColor(if (clickView == btFts) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))
        btCm.setTextColor(if (clickView == btCm) ContextCompat.getColor(requireContext(),R.color.white) else ContextCompat.getColor(requireContext(),R.color.black))
        tvHeightType.text = if (clickView == btFts) "ft" else "cm"
    }

}
package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.utils.SimpleRulerView
import com.application.suliraapplication.viewmodels.UpdateHealthConditionViewModel
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener
import kotlinx.android.synthetic.main.activity_select_weight.btKgs
import kotlinx.android.synthetic.main.activity_select_weight.btLbs
import kotlinx.android.synthetic.main.activity_select_weight.tvWeight
import kotlinx.android.synthetic.main.activity_select_weight.tvWeightScaleType
import kotlinx.android.synthetic.main.activity_update_weight.*

class UpdateWeightFragment : BaseFragment(), SimpleRulerView.OnValueChangeListener,
    View.OnClickListener {

    private val updateHealthConditionViewModel by lazy {
        ViewModelProvider(this).get(UpdateHealthConditionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_update_weight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btUpdateWeight.setOnClickListener {
            showDialog()
            updateHealthConditionViewModel.updateHealthCondition(
                PreferenceManager().userId.toString(),
                "weight",
                tvWeight.text.toString()
            )
        }

        updateWeightRulerValuePicker.indicatorColor =
            ContextCompat.getColor(requireContext(), R.color.app_color)
        updateWeightRulerValuePicker.textColor =
            ContextCompat.getColor(requireContext(), R.color.app_color)
        updateWeightRulerValuePicker.notchColor =
            ContextCompat.getColor(requireContext(), R.color.app_color)
        updateWeightRulerValuePicker.setNotchColorRes(R.color.app_color)
        updateWeightRulerValuePicker.setMinMaxValue(20, 500)
        updateWeightRulerValuePicker.selectValue(120)
        updateWeightRulerValuePicker.setIndicatorIntervalDistance(25)

        updateWeightRulerValuePicker.setValuePickerListener(object : RulerValuePickerListener {
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
        ivBackWeight.setOnClickListener(this)

        updateHealthConditionViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                setError(updateHealthConditionViewModel.customResponse.msg)
                (activity as HomeTabActivity).onBackPressed()
            } else {
                setError(updateHealthConditionViewModel.message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
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

            ivBackWeight ->{
                (activity as HomeTabActivity).onBackPressed()
            }
        }

    }

    private fun handleWeightType(clickView: View) {
        btLbs.background =
            if (clickView == btLbs) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rect_fill_back
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btKgs.background =
            if (clickView == btKgs) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rect_fill_back
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btLbs.setTextColor(
            if (clickView == btLbs) ContextCompat.getColor(
                requireContext(),
                R.color.white
            ) else ContextCompat.getColor(requireContext(), R.color.black)
        )
        btKgs.setTextColor(
            if (clickView == btKgs) ContextCompat.getColor(
                requireContext(),
                R.color.white
            ) else ContextCompat.getColor(requireContext(), R.color.black)
        )
        tvWeightScaleType.text = if (clickView == btLbs) "LBS" else "KG"
    }
}
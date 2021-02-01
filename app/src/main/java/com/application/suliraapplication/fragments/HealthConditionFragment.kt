package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.adapter.AdapterHealthCondition
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.HealthConditionModel
import com.application.suliraapplication.viewmodels.HealthConditionViewModel
import kotlinx.android.synthetic.main.activity_excersize_fragment.*
import kotlinx.android.synthetic.main.activity_health_condition.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class HealthConditionFragment : BaseFragment(), View.OnClickListener {

    private lateinit var adapterHealthCondition: AdapterHealthCondition
    private lateinit var listHealthConditionModel: HealthConditionModel

    private val healthConditionViewModel by lazy {
        ViewModelProvider(this).get(HealthConditionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_health_condition, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Skip")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        healthConditionViewModel.successful.observe(viewLifecycleOwner, Observer { successful ->
            dismissDialog()
            if (successful != null) {
                listHealthConditionModel = healthConditionViewModel.healthConditionModel
                adapterHealthCondition =
                    AdapterHealthCondition(listHealthConditionModel, requireContext())

                if (listHealthConditionModel.purposeRecords.isNotEmpty()) {
                    rvHealthCondition.apply {
                        layoutManager = GridLayoutManager(activity, 3)
                        adapter = AdapterHealthCondition(listHealthConditionModel, requireContext())
                        adapter = adapterHealthCondition
                    }

                }
            }
        })

        btHealthConditionContinue.setOnClickListener {
            progressPercentage = 11

            val itemList = adapterHealthCondition.itemList()

            val jsonData=JSONArray()
            for (item in itemList.purposeRecords){

                val selectedObject=JSONObject()
                if (item.isSelected){
                    selectedObject.put("key",item.id)
                    jsonData.put(selectedObject)
                }
            }

            if (rvHealthCondition.visibility == View.VISIBLE) {

                if (jsonData.length() == 0) {
                    setError("Select At least one")
                    return@setOnClickListener
                }
            }
            val healthConditionArray: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), jsonData.toString())
              signUpDataClass.healthArray=healthConditionArray
            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                TurnOnNotificationFragment()
            )
        }

        btHealthYes.setOnClickListener(this)
        btHealthNo.setOnClickListener(this)

        showDialog()
        healthConditionViewModel.getHealthConditionList()

    }

    private fun handleExerciseType(clickView: View) {
        btExerciseYes.background =
            if (clickView == btExerciseYes) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rect_fill_back
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btExerciseNo.background =
            if (clickView == btExerciseNo) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rect_fill_back
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btExerciseYes.setTextColor(
            if (clickView == btExerciseYes) ContextCompat.getColor(
                requireContext(),
                R.color.white
            ) else ContextCompat.getColor(requireContext(), R.color.black)
        )

        btExerciseNo.setTextColor(
            if (clickView == btExerciseNo) ContextCompat.getColor(
                requireContext(),
                R.color.white
            ) else ContextCompat.getColor(requireContext(), R.color.black)
        )

        llSpn.visibility = if (clickView == btExerciseYes) View.VISIBLE else View.GONE
        ifYes.visibility = if (clickView == btExerciseYes) View.VISIBLE else View.GONE
    }

    override fun onClick(viewClick: View?) {

        when (viewClick) {
            btHealthYes,
            btHealthNo -> handleHealthConditionType(viewClick!!)
        }

    }

    private fun handleHealthConditionType(clickView: View) {
        btHealthYes.background =
            if (clickView == btHealthYes) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rect_fill_back
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btHealthNo.background =
            if (clickView == btHealthNo) ContextCompat.getDrawable(
                requireContext(),
                R.drawable.rect_fill_back
            )
            else ContextCompat.getDrawable(requireContext(), R.drawable.rect_white_fill_back)

        btHealthYes.setTextColor(
            if (clickView == btHealthYes) ContextCompat.getColor(
                requireContext(),
                R.color.white
            ) else ContextCompat.getColor(requireContext(), R.color.black)
        )

        btHealthNo.setTextColor(
            if (clickView == btHealthNo) ContextCompat.getColor(
                requireContext(),
                R.color.white
            ) else ContextCompat.getColor(requireContext(), R.color.black)
        )

        selectHealth.visibility = if (clickView == btHealthYes) View.VISIBLE else View.GONE
        rvHealthCondition.visibility = if (clickView == btHealthYes) View.VISIBLE else View.GONE
    }
}
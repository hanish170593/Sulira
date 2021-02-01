package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.adapter.AdapterCustomizeInterests
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.InterestModel
import com.application.suliraapplication.viewmodels.InterestViewModel
import kotlinx.android.synthetic.main.activity_interest.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject


class InterestFragment : BaseFragment() {

    private lateinit var listModelInterest: InterestModel
    private lateinit var adapterInterest: AdapterCustomizeInterests
    private val interestViewModel by lazy {
        //   InterestViewModel by viewModels()
        ViewModelProvider(this).get(InterestViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_interest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        interestViewModel.successful.observe(viewLifecycleOwner, Observer { successful ->
            dismissDialog()
            if (successful != null) {
                listModelInterest = interestViewModel.interestModel

                if (listModelInterest.interestRecords.isNotEmpty()) {
                    adapterInterest = AdapterCustomizeInterests(listModelInterest, requireContext())
                    rvInterests.apply {
                        layoutManager = GridLayoutManager(activity, 3)
                        adapter = AdapterCustomizeInterests(listModelInterest, requireContext())
                        adapter = adapterInterest
                    }
                }
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.error_something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        btCustomizeInterest.setOnClickListener {
            progressPercentage = 9
            val listItems = adapterInterest.getListItems()
            val jsonData = JSONArray()
            for (item in listItems.interestRecords) {
                val dataObject = JSONObject()
                if (item.isSelected) {
                    dataObject.put("key", item.id)
                    jsonData.put(dataObject)
                }
            }

            if (jsonData.length() == 0){
                setError("Select At least one")
                return@setOnClickListener
            }

            val interestArray: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), jsonData.toString())
            signUpDataClass.interestArray = interestArray
            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                HowCanHelpFragment()
            )
        }

        showDialog()
        interestViewModel.getInterestListData()
    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Skip")
    }
}

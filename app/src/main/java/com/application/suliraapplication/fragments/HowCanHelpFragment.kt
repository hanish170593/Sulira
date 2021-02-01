package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.progressPercentage
import com.application.suliraapplication.activity.RegistrationContainerActivity.Companion.signUpDataClass
import com.application.suliraapplication.adapter.AdapterHowCanHelp
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.HowCanHelpModel
import com.application.suliraapplication.viewmodels.HowCanHelpViewModel
import kotlinx.android.synthetic.main.activity_how_can_help.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

class HowCanHelpFragment : BaseFragment() {

    private lateinit var listHowCanHelp: HowCanHelpModel
    private lateinit var adapterHowCanHelp: AdapterHowCanHelp
   // val howCanHelpModel: HowCanHelpViewModel by viewModels()

    private val howCanHelpModel by lazy {
        ViewModelProvider(this).get(HowCanHelpViewModel::class.java)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_how_can_help, container, false)
    }
    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Skip")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        howCanHelpModel.successful.observe(viewLifecycleOwner, Observer {successful ->
             dismissDialog()
            if (successful !=null){
                listHowCanHelp=howCanHelpModel.howCanHelpModel
                adapterHowCanHelp = AdapterHowCanHelp(listHowCanHelp, requireContext())

                if (listHowCanHelp.purposeRecords.isNotEmpty()){
                    rvInterests.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = AdapterHowCanHelp(listHowCanHelp, requireContext())
                        adapter = adapterHowCanHelp
                    }

                }
            }

        })

        btLetHowCanHelpContinue.setOnClickListener {
            progressPercentage=10
            val itemList = adapterHowCanHelp.itemList()
            val jsonData=JSONArray()
            for (item in itemList.purposeRecords){
                val selectedObject=JSONObject()
                if (item.isSelected){
                    selectedObject.put("key",item.id)
                    jsonData.put(selectedObject)
                }
            }

            if (jsonData.length() == 0){
                setError("Select At least one")
                return@setOnClickListener
            }

            val howCanHelpArray: RequestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), jsonData.toString())
            signUpDataClass.purposeArray=howCanHelpArray
            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                HealthConditionFragment()
            )
        }

        showDialog()
        howCanHelpModel.getHowCanHelpList()
    }
}
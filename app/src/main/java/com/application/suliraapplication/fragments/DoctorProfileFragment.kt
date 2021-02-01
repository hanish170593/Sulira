package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.AdapterDoctorActivitiesList
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.ChatData
import com.application.suliraapplication.models.DoctorDetailModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.AddToChatViewModel
import com.application.suliraapplication.viewmodels.DoctorDetailViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_doctor_profile.*

class DoctorProfileFragment(val doctorId: String) : BaseFragment(), View.OnClickListener {
    private lateinit var adapterDoctorActivitiesList: AdapterDoctorActivitiesList
    private lateinit var doctorDetailModel: DoctorDetailModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctor_profile, container, false)
    }

    private val doctorDetailViewModel by lazy {
        ViewModelProvider(this).get(DoctorDetailViewModel::class.java)
    }

    private val addToChatViewModel by lazy {
        ViewModelProvider(this).get(AddToChatViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBackDoctorProfile.setOnClickListener(this)

        ivChatWithDoctor.setOnClickListener(this)

        doctorDetailViewModel.successful.observe(requireActivity(), Observer {

            dismissDialog()
            if (it) {
                doctorDetailModel = doctorDetailViewModel.doctorDetailModel
                setDataList()
            } else {
                setError(doctorDetailViewModel.message)
            }

        })

        addToChatViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                if (addToChatViewModel.message == "Chat created successfully") {
                    var dummyValue: String = "{\n" +
                            "            \"id\": \"1\",\n" +
                            "            \"chatRef\": \"y6E04dC51O43q\",\n" +
                            "            \"userId\": \"2\",\n" +
                            "            \"receiverId\": \"3\",\n" +
                            "            \"lastMessage\": \"hiiii\",\n" +
                            "            \"create_at\": \"2020-12-31 21:53:51\",\n" +
                            "            \"update_at\": \"2021-01-01 21:17:14\",\n" +
                            "            \"fullName\": \"admin3\",\n" +
                            "            \"profilePic\": \"220px-Usedshgfjshgyerwcnmdr_icon_2.svg.png\"\n" +
                            "        }";
                    var modelChatMain: ChatData = Gson().fromJson(dummyValue, ChatData::class.java)
                    modelChatMain.chatRef = addToChatViewModel.addToChatModel.chatRef
                    modelChatMain.receiverId = doctorDetailModel.doctorDetail.id
                    modelChatMain.fullName = doctorDetailModel.doctorDetail.doctorName
                    modelChatMain.profilePic = doctorDetailModel.doctorDetail.doctorProfileImage
                    val baseUrl =
                        "http://hourlylancer.com/devlop/sulira/backend/assets/uploads/doctorProfileImage/"
                    (activity as HomeTabActivity).replaceFragmentWithBackStack(
                        ChatOneToOneFragment(
                            modelChatMain, baseUrl
                        )
                    )
                    addToChatViewModel.message = ""
                }
            } else {
                setError(addToChatViewModel.message)
            }
        })

        showDialog()
        doctorDetailViewModel.getDoctorDetail(doctorId)
    }

    private fun setDataList() {

        Glide.with(requireActivity())
            .load(doctorDetailModel.urlDoctorImage + doctorDetailModel.doctorDetail.doctorProfileImage)
            .into(ivDoctorProfile)

        tvDocDesignation.text = doctorDetailModel.doctorDetail.doctorDesignation
        tvDocName.text = doctorDetailModel.doctorDetail.doctorName
        tvDocAbout.text = doctorDetailModel.doctorDetail.doctorAbout

        adapterDoctorActivitiesList =
            AdapterDoctorActivitiesList(requireContext(), doctorDetailModel)

        rvRecentActivities.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            adapter = AdapterDoctorActivitiesList(requireContext(), doctorDetailModel)
            adapter = adapterDoctorActivitiesList
        }

    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            ivBackDoctorProfile -> {
                (activity as HomeTabActivity).onBackPressed()
            }

            ivChatWithDoctor -> {
                showDialog()
                addToChatViewModel.addToChat(
                    PreferenceManager().userId.toString(),
                    doctorDetailModel.doctorDetail.id
                )
            }

        }

    }

}
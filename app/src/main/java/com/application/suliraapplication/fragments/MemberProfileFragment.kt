package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.AdapterDoctorList
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.ChatData
import com.application.suliraapplication.models.DoctorsList
import com.application.suliraapplication.models.MemberProfileModel
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.AddToChatViewModel
import com.application.suliraapplication.viewmodels.MemberProfileViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_member_profile.*


class MemberProfileFragment : BaseFragment(), View.OnClickListener,
    AdapterDoctorList.OnClickChatOption {

    private lateinit var adapterDoctorList: AdapterDoctorList
    private lateinit var memberProfileModel: MemberProfileModel
    private lateinit var dataDoctor: DoctorsList
    private var isCalled: Boolean = false

    private val memberProfileViewModel by lazy {
        ViewModelProvider(this).get(MemberProfileViewModel::class.java)
    }

    private val addToChatViewModel by lazy {
        ViewModelProvider(this).get(AddToChatViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        val signUpModel: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)

        cvPremiumLayout.visibility =
            if (signUpModel.userInfo.is_Premium == "1") View.GONE else View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_member_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivSetting.setOnClickListener {
            (activity as HomeTabActivity).replaceFragmentWithBackStack(SettingFragment())
        }

        ivEditBtn.setOnClickListener {
            (activity as HomeTabActivity).replaceFragmentWithBackStack(MyProfileFragment())
        }

        rlMemberBPMMain.setOnClickListener(this)
        rlMemberWeightMain.setOnClickListener(this)
        rlMemberSugarMain.setOnClickListener(this)
        rlMemberSleep.setOnClickListener(this)
        btUnlockPremium.setOnClickListener(this)

        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener {
            (activity as HomeTabActivity).onBackPressed()
        }

        llOne.setOnClickListener(this)
        llTwo.setOnClickListener(this)
        llThree.setOnClickListener(this)

        memberProfileViewModel.successful.observe(viewLifecycleOwner, Observer {
            dismissDialog()
            if (it) {
                memberProfileModel = memberProfileViewModel.memberProfileModel
                try {
                    setData()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                setError(memberProfileViewModel.message)
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
                    modelChatMain.receiverId = dataDoctor.id
                    modelChatMain.fullName = dataDoctor.doctorName
                    modelChatMain.profilePic = dataDoctor.doctorProfileImage
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
        memberProfileViewModel.getMemberUserInfo(PreferenceManager().userId.toString())

    }

    private fun setData() {
        Glide.with(requireActivity())
            .load(memberProfileModel.base_url + memberProfileModel.UserInfo.profilePic)
            .into(ivMemberProfilePic)

        tvMemberName.text = memberProfileModel.UserInfo.fullName
        tvMemberType.text =
            if (memberProfileModel.UserInfo.is_Premium == "1") "Premium Member" else "Basic Member"
        tvAboutMember.text = memberProfileModel.UserInfo.about
        tvMemberWeight.text = memberProfileModel.UserInfo.weight
        tvMemberAge.text = memberProfileModel.UserInfo.age
        tvMemberHeight.text = memberProfileModel.UserInfo.height
        tvGoPremium.text = memberProfileModel.UserInfo.premium.title
        tvUnlockAllFeatures.text = memberProfileModel.UserInfo.premium.subTitle
        tvTagLine.text = memberProfileModel.UserInfo.premium.tagLine
        tvMemberBPM.text = memberProfileModel.UserInfo.userHealthInfo.heartRate.health_value
        val daysDiff =
            HomeScreenFragment.getDaysDiff(memberProfileModel.UserInfo.userHealthInfo.heartRate.updated_at)
        tvMemberBPMLastUpdated.text = "last update $daysDiff d"

        tvMemberProfileWeight.text = memberProfileModel.UserInfo.userHealthInfo.weight.health_value
        val toString =
            HomeScreenFragment.getDaysDiff(memberProfileModel.UserInfo.userHealthInfo.weight.updated_at)
                .toString()
        tvWeightLastUpdatedAt.text = "last update $toString d"

        tvMemberSugar.text = memberProfileModel.UserInfo.userHealthInfo.sugar.health_value
        val updatedAt =
            HomeScreenFragment.getDaysDiff(memberProfileModel.UserInfo.userHealthInfo.sugar.updated_at)
                .toString()
        tvLastSugarUpdated.text = "last update $updatedAt d"

        tvMemberSleep.text = memberProfileModel.UserInfo.userHealthInfo.sleep.health_value
        tvMemberSleepAt.text = memberProfileModel.UserInfo.userHealthInfo.sleep.sleep_at
        tvMemberWakeUpAt.text = memberProfileModel.UserInfo.userHealthInfo.sleep.wakeup_at
        val updatedAtSleep =
            HomeScreenFragment.getDaysDiff(memberProfileModel.UserInfo.userHealthInfo.sleep.updated_at)
                .toString()
        tvLastSleepUpdated.text = "last update $updatedAtSleep d"

        if (memberProfileModel.UserInfo.premium.users.isNotEmpty()) {

            if (llGoPremiumMemberList.childCount > 0) {
                llGoPremiumMemberList.removeAllViews()
            }

            for (i in memberProfileModel.UserInfo.premium.users.indices) {
                val view: View = LayoutInflater.from(requireContext())
                    .inflate(R.layout.view_premium_member_list_layout, null)

                val ivPremiumMember: AppCompatImageView = view.findViewById(R.id.ivPremiumMember)
                Glide.with(requireActivity())
                    .load(memberProfileModel.base_url + memberProfileModel.UserInfo.premium.users[i].profilePic)
                    .into(ivPremiumMember)
                llGoPremiumMemberList.addView(view)
            }

        }

        adapterDoctorList = AdapterDoctorList(requireContext(), memberProfileModel, this)
        rvDoctorList.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = AdapterDoctorList(requireContext(), memberProfileModel, this)
            it.adapter = adapterDoctorList
        }

    }

    override fun onClick(view: View?) {
        when (view) {
            /*llOne, llTwo, llThree -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                DoctorProfileFragment()
            )*/

            rlMemberBPMMain ->
                (activity as HomeTabActivity).replaceFragmentWithBackStack(
                    MyProgressFragment.newInstance(
                        "Heart Rate",
                        memberProfileModel.UserInfo.userHealthInfo.heartRate.updated_at
                    )
                )

            rlMemberWeightMain ->
                (activity as HomeTabActivity).replaceFragmentWithBackStack(
                    MyProgressFragment.newInstance(
                        "Weight",
                        memberProfileModel.UserInfo.userHealthInfo.weight.updated_at
                    )
                )

            rlMemberSugarMain ->
                (activity as HomeTabActivity).replaceFragmentWithBackStack(
                    MyProgressFragment.newInstance(
                        "Sugar",
                        memberProfileModel.UserInfo.userHealthInfo.sugar.updated_at
                    )
                )

            rlMemberSleep ->
                (activity as HomeTabActivity).replaceFragmentWithBackStack(SleepTimeFragment())

            btUnlockPremium -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                GetPremiumMembershipFragment()
            )
        }
    }

    override fun onClickChatWithDoctor(data: DoctorsList) {
        dataDoctor = data
        showDialog()
        addToChatViewModel.addToChat(PreferenceManager().userId.toString(), data.id)
    }

}
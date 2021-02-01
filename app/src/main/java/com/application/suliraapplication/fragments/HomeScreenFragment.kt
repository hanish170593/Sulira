package com.application.suliraapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.HomeDataViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home_screen.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class HomeScreenFragment : BaseFragment(), View.OnClickListener {

    private val homeDataViewModel by lazy {
        ViewModelProvider(this).get(HomeDataViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView-----A", "called")
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("onViewCreated-----A", "called")
        riProfile.setOnClickListener(this)
        ivNotification.setOnClickListener(this)
        rlWeightMain.setOnClickListener(this)
        rlBPMMain.setOnClickListener(this)
        rlSleepLayout.setOnClickListener(this)
        rlSugarMain.setOnClickListener(this)
        tvTodayDate.text = getTodayDay()
        homeDataViewModel.successful.observe(viewLifecycleOwner, Observer { successful ->
            dismissDialog()
            if (successful) {
                setHomeData()
            } else {
                setError(homeDataViewModel.message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume-----A", "called")
        val toString = PreferenceManager().userResponse.toString()
        val signUpModel: SignUpModel =
            Gson().fromJson(toString, SignUpModel::class.java)

        if (signUpModel.userInfo.profilePic.isNotEmpty()) {
            Glide.with(requireActivity())
                .load("https://hourlylancer.com/devlop/sulira/backend/assets/uploads/profilePic/" + signUpModel.userInfo.profilePic).into(riProfile)
        }

        showDialog()
        homeDataViewModel.getHomeData(PreferenceManager().userId.toString())
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause-----A", "called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop-----A", "called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("onDestroyView-----A", "called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy-----A", "called")
    }

    @SuppressLint("SetTextI18n")
    private fun setHomeData() {
        val fullName = homeDataViewModel.homeModel.userRecord.userDetails.fullName
        tvLoggedName.text = "Hi, $fullName"
        val avgHealthScore = homeDataViewModel.homeModel.userRecord.avgHealthScore
        tvScore.text = avgHealthScore
        tvHcText.text = "Based on your overall health \\ntest, your score is $avgHealthScore"
        tvBPM.text = homeDataViewModel.homeModel.userRecord.heartRate.health_value
        val daysDiff = getDaysDiff(homeDataViewModel.homeModel.userRecord.heartRate.updated_at)
        tvHeartUpdated.text = "last update $daysDiff d"

        tvWeight.text = homeDataViewModel.homeModel.userRecord.weight.health_value
        val toString =
            getDaysDiff(homeDataViewModel.homeModel.userRecord.weight.updated_at).toString()
        tvWeightUpdate.text = "last update $toString d"

        tvSugar.text = homeDataViewModel.homeModel.userRecord.sugar.health_value
        val updatedAt =
            getDaysDiff(homeDataViewModel.homeModel.userRecord.sugar.updated_at).toString()
        tvSugarUpdated.text = "last update $updatedAt d"

        tvSleep.text = homeDataViewModel.homeModel.userRecord.sleep.health_value
        val updatedAt1 = getDaysDiff(homeDataViewModel.homeModel.userRecord.sleep.updated_at)
        tvSleepUpdated.text = "last update $updatedAt1 d"
        tvSleepAt.text = homeDataViewModel.homeModel.userRecord.sleep.sleep_at
        tvWakeUpAt.text = homeDataViewModel.homeModel.userRecord.sleep.wakeup_at
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            riProfile -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                MemberProfileFragment()
            )

            ivNotification -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                NotificationScreenFragment()
            )

            rlWeightMain -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                MyProgressFragment.newInstance(
                    "Weight",
                    homeDataViewModel.homeModel.userRecord.weight.updated_at
                )
            )

            rlBPMMain -> {
                (activity as HomeTabActivity).replaceFragmentWithBackStack(
                    MyProgressFragment.newInstance(
                        "Heart Rate",
                        homeDataViewModel.homeModel.userRecord.heartRate.updated_at
                    )
                )

                /*requireActivity().startActivity(
                    Intent(
                        activity,
                        HeartCheckActivity::class.java
                    ).putExtra(
                        "lastUpdate",
                        homeDataViewModel.homeModel.userRecord.heartRate.updated_at
                    )
                )*/
            }
            rlSleepLayout -> {
                /*(activity as HomeTabActivity).replaceFragmentWithBackStack(
                    MyProgressFragment.newInstance(
                        "Sleep",
                        homeDataViewModel.homeModel.userRecord.sleep.updated_at
                    )
                )*/
                (activity as HomeTabActivity).replaceFragmentWithBackStack(SleepTimeFragment())
            }

            rlSugarMain -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                MyProgressFragment.newInstance(
                    "Sugar",
                    homeDataViewModel.homeModel.userRecord.sugar.updated_at
                )
            )
        }

    }

    companion object {
        fun getDaysDiff(lastDate: String): Int {

            if (TextUtils.isEmpty(lastDate)) {
                return 0
            }

            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
            val expireCovertedDate: Date = dateFormat.parse(lastDate)
            val cCal = Calendar.getInstance()
            cCal.time = expireCovertedDate
            val msDiff: Long =
                Calendar.getInstance().timeInMillis - cCal.timeInMillis
            val daysDiff: Long = TimeUnit.MILLISECONDS.toDays(msDiff)
            return daysDiff.toInt()
        }

        fun getTodayDay(): String {
            val dateFormat = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            return dateFormat.format(Date())
        }
    }


}
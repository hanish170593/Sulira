package com.application.suliraapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.AdapterHealth
import com.application.suliraapplication.adapter.AdapterVirus
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.NewsFeedModel
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.NewsFeedViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_news_feed.*

class NewsFeedFragment : BaseFragment(), View.OnClickListener {

    private lateinit var adapterHealth: AdapterHealth
    private lateinit var adapterVirus: AdapterVirus
    private lateinit var newsFeedModel: NewsFeedModel

    private val newsFeedViewModel by lazy {
        ViewModelProvider(this).get(NewsFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsFeedViewModel.successful.observe(requireActivity(), Observer {

            dismissDialog()

            if (it) {
                newsFeedModel = newsFeedViewModel.newsFeedModel
                setListAdapters()
            } else {
                setError(newsFeedViewModel.message)
            }

        })

        ivExploreBack.setOnClickListener(this)

        rlReadMore.setOnClickListener {
            (activity as HomeTabActivity).replaceFragmentWithBackStack(PostDetailFragment())
        }

        btnPremium.setOnClickListener {
            (activity as HomeTabActivity).replaceFragmentWithBackStack(GetPremiumMembershipFragment())
        }
        showDialog()
        newsFeedViewModel.getNewsFeed(PreferenceManager().userId.toString())
        //  (activity as HomeTabActivity).weightReminderDialog()
    }

    private fun setListAdapters() {
        adapterHealth = AdapterHealth(requireContext(), newsFeedModel)
        adapterVirus = AdapterVirus(requireContext(), newsFeedModel)

        rvHealth.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            adapter = AdapterHealth(requireContext(), newsFeedModel)
            adapter = adapterHealth
        }

        rvVirus.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            adapter = AdapterVirus(requireContext(), newsFeedModel)
            adapter = adapterVirus
        }

        tvHeading.text = newsFeedModel.newsData.newsFeedRecords[0].newsType
        tvDesc.text = newsFeedModel.newsData.newsFeedRecords[0].newsTitle
        tvExtraDesc.text = newsFeedModel.newsData.newsFeedRecords[0].newsSubTitle

        val signUpModel: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)

        rlPremiumPlanNotice.visibility =
            if (signUpModel.userInfo.is_Premium == "1") View.GONE else View.VISIBLE

    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                // (activity as HomeTabActivity).heartrateMeasureDialog()
            }
        }, 3000)
    }

    override fun onClick(clickView: View?) {
        when(clickView){
            ivExploreBack ->{
                (activity as HomeTabActivity).onBackPressed()
            }
        }
    }
}
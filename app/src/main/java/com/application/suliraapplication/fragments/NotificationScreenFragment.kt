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
import com.application.suliraapplication.adapter.AdapterEarlierNotificationsAdapter
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.NotificationModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.NotificationViewModel
import kotlinx.android.synthetic.main.fragment_notification_screen.*


class NotificationScreenFragment : BaseFragment() {

    private lateinit var adapterEarlier: AdapterEarlierNotificationsAdapter
    private lateinit var notificationModel: NotificationModel

    private val notificationViewModel by lazy {
        ViewModelProvider(this).get(NotificationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener {
            (activity as HomeTabActivity).onBackPressed()
        }

        notificationViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()

            if (it) {
                notificationModel = notificationViewModel.notificationModel
                setData()
            } else {
                setError(notificationViewModel.message)
            }

        })

        showDialog()
        notificationViewModel.getNotification(PreferenceManager().userId.toString())
    }

    private fun setData() {
        val toString = notificationModel.notificationData.size.toString()
        tvNotificationCount.text = "+$toString"

        adapterEarlier = AdapterEarlierNotificationsAdapter(notificationModel, requireContext())

        rvEarLier.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AdapterEarlierNotificationsAdapter(notificationModel, requireContext())
            adapter = adapterEarlier
        }
    }

}
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
import com.application.suliraapplication.adapter.AdapterChatOneToOne
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.ChatData
import com.application.suliraapplication.models.ChatHistoryModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.ChatHistoryViewModel
import com.application.suliraapplication.viewmodels.SendMessageViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatOneToOneFragment(
    var modelChatMain: ChatData,
    val baseUrl: String
) : BaseFragment() {

    private lateinit var adapterOneToOne: AdapterChatOneToOne
    private lateinit var chatHistoryModel: ChatHistoryModel

    private val chatHistoryViewModel by lazy {
        ViewModelProvider(this).get(ChatHistoryViewModel::class.java)
    }

    private val sendMessageViewModel by lazy {
        ViewModelProvider(this).get(SendMessageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPersonName.text = modelChatMain.fullName
        Glide.with(requireActivity()).load(baseUrl + modelChatMain.profilePic).into(ivPersonImage)

        btnSend.setOnClickListener {
            val message = etMessageBox.text

            if (message.isNullOrEmpty()) {
                return@setOnClickListener
            }

            sendMessageViewModel.sendMessage(
                PreferenceManager().userId.toString(),
                modelChatMain.receiverId,
                message.toString(),
                modelChatMain.chatRef
            )

            /*adapterOneToOne.addMessage(message.toString())
            etMessageBox.setText("")
            view.let {
                (activity as HomeTabActivity).hideKeyboard(it)
            }*/
        }

        ivBack.setOnClickListener {
            (activity as HomeTabActivity).onBackPressed()
        }

        chatHistoryViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                chatHistoryModel = chatHistoryViewModel.chatHistoryModel
                setChatHistoryListAdapter()
            } else {
                setError(chatHistoryViewModel.message)
            }
        })

        sendMessageViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()

            if (it) {
                etMessageBox.setText("")
                chatHistoryViewModel.getChatHistory(modelChatMain.chatRef)
            } else {
                setError(sendMessageViewModel.message)
            }

        })

        showDialog()
        chatHistoryViewModel.getChatHistory(modelChatMain.chatRef)
    }

    private fun setChatHistoryListAdapter() {
        adapterOneToOne = AdapterChatOneToOne(requireContext(), chatHistoryModel)

        rvChatOneToOne.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AdapterChatOneToOne(requireContext(), chatHistoryModel)
            adapter = adapterOneToOne
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as HomeTabActivity).hideTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as HomeTabActivity).showTabLayout()
    }

}
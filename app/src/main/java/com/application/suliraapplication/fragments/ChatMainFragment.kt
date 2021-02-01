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
import com.application.suliraapplication.adapter.AdapterChatMain
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.ChatListModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.ChatListViewModel
import com.application.suliraapplication.viewmodels.ChatMainFragmentViewModel
import kotlinx.android.synthetic.main.chat_main_fragmrnt_fragment.*

class ChatMainFragment : BaseFragment(), View.OnClickListener {

    private lateinit var adapterChatMain: AdapterChatMain
    private lateinit var chatListModel: ChatListModel

    private val chatListViewModel by lazy {
        ViewModelProvider(this).get(ChatListViewModel::class.java)
    }

    private lateinit var viewModel: ChatMainFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_main_fragmrnt_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatMainFragmentViewModel::class.java)
        ivBackChatMain.setOnClickListener(this)
        chatListViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()

            if (it) {
                chatListModel = chatListViewModel.chatListModel
                setChatListAdapter()
            } else {
                setError(chatListViewModel.message)
            }
        })

        showDialog()
        chatListViewModel.getChatList(PreferenceManager().userId.toString(), "customer")
    }

    private fun setChatListAdapter() {
        adapterChatMain = AdapterChatMain(requireContext(), chatListModel)

        rvChatMain.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AdapterChatMain(requireContext(), chatListModel)
            adapter = adapterChatMain
        }

        tvDataNotFound.visibility =
            if (chatListModel.chatData.isNotEmpty()) View.GONE else View.VISIBLE
    }

    override fun onClick(clickView: View?) {
        when(clickView){
            ivBackChatMain ->{
                (activity as HomeTabActivity).onBackPressed()
            }
        }
    }
}
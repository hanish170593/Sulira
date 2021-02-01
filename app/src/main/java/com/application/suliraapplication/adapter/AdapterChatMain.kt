package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.fragments.ChatOneToOneFragment
import com.application.suliraapplication.models.ChatListModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_layout_chat_main.view.*

class AdapterChatMain(
    val context: Context,
    var listChatMain: ChatListModel
) : RecyclerView.Adapter<AdapterChatMain.ViewHolderChatMain>() {

    class ViewHolderChatMain(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChatMain =
        ViewHolderChatMain(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_layout_chat_main, parent, false
            )
        )

    override fun getItemCount(): Int = listChatMain.chatData.size

    override fun onBindViewHolder(holder: ViewHolderChatMain, position: Int) {
        Glide.with(context).load(listChatMain.base_url + listChatMain.chatData[position].profilePic)
            .into(holder.itemView.ivChatPersonImage)
        holder.itemView.tvChatPersonName.text = listChatMain.chatData[position].fullName
        holder.itemView.tvChatPersonMessage.text = listChatMain.chatData[position].lastMessage
        holder.itemView.tvMessageTime.text = listChatMain.chatData[position].create_at

        holder.itemView.setOnClickListener {
            (context as HomeTabActivity).replaceFragmentWithBackStack(
                ChatOneToOneFragment(
                    listChatMain.chatData[position],listChatMain.base_url
                )
            )
        }
    }
}
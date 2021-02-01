package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.ChatHistoryModel
import kotlinx.android.synthetic.main.adapter_layout_chat_one_to_one.view.*

class AdapterChatOneToOne(val context: Context, var listChatModel: ChatHistoryModel) :
    RecyclerView.Adapter<AdapterChatOneToOne.ViewHolderChatOneToOne>() {

    class ViewHolderChatOneToOne(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChatOneToOne =
        ViewHolderChatOneToOne(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_layout_chat_one_to_one, parent, false
            )
        )

    override fun getItemCount(): Int = listChatModel.ChatHistory.size

    override fun onBindViewHolder(holder: ViewHolderChatOneToOne, position: Int) {

        when (listChatModel.ChatHistory[position].userType) {

            "customer" -> {
                holder.itemView.tvYourMessage.text = listChatModel.ChatHistory[position].message
                holder.itemView.tvYourMessageTime.text =
                    listChatModel.ChatHistory[position].created_at
                holder.itemView.llYourMessageLayout.visibility = View.VISIBLE
                holder.itemView.llMeMessageLayout.visibility = View.GONE
            }

            "doctor" -> {
                holder.itemView.tvMeMessage.text = listChatModel.ChatHistory[position].message
                holder.itemView.tvMeMessageTime.text =
                    listChatModel.ChatHistory[position].created_at
                holder.itemView.llYourMessageLayout.visibility = View.GONE
                holder.itemView.llMeMessageLayout.visibility = View.VISIBLE
            }
        }

    }

    fun addMessage(message: String) {
        notifyDataSetChanged()
    }
}
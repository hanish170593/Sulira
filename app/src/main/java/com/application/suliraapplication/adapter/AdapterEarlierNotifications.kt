package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.NotificationModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_layout_earlier_notifications.view.*

class AdapterEarlierNotificationsAdapter(var itemsData: NotificationModel, val context: Context) :
    RecyclerView.Adapter<AdapterEarlierNotificationsAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        NotificationViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_layout_earlier_notifications, parent, false)
        )

    override fun getItemCount(): Int = itemsData.notificationData.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        Glide.with(context)
            .load(itemsData.baseUrl + itemsData.notificationData[position].notificationImage)
            .into(holder.itemView.ivNotificationImage)

        holder.itemView.tvNotTitle.text = itemsData.notificationData[position].notificationTitle
        holder.itemView.tvNotDesc.text = itemsData.notificationData[position].notificationText
    }
}
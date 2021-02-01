package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.NewsFeedModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_health_layout.view.*
import kotlinx.android.synthetic.main.adapter_topics_layout.view.*

class AdapterHealth(
    val context: Context,
    var newsFeedModel: NewsFeedModel
) : RecyclerView.Adapter<AdapterHealth.ViewHolderHealth>() {

    class ViewHolderHealth(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHealth =
        ViewHolderHealth(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_health_layout, parent, false
            )
        )

    override fun getItemCount(): Int =newsFeedModel.newsData.healthRecords.size

    override fun onBindViewHolder(holder: ViewHolderHealth, position: Int) {
        val healthRecords = newsFeedModel.newsData.healthRecords[position]
        Glide.with(context).load(newsFeedModel.base_url + "/health/" + healthRecords.healthImage)
            .into(holder.itemView.ivHealthImage)

        holder.itemView.tvHealthTitle.text=healthRecords.healthTitle
        holder.itemView.tvHealthTime.text=healthRecords.create_at

    }
}
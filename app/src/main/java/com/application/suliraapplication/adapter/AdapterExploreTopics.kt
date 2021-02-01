package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.ExploreModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_topics_layout.view.*

class AdapterExploreTopics(
    val context: Context,
    var exploreModel: ExploreModel
) :
    RecyclerView.Adapter<AdapterExploreTopics.ExploreTopicsViewHolder>() {

    class ExploreTopicsViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreTopicsViewHolder =
        ExploreTopicsViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_topics_layout, parent, false)
        )

    override fun getItemCount(): Int = exploreModel.exploreData.interestRecords.size

    override fun onBindViewHolder(holder: ExploreTopicsViewHolder, position: Int) {
        val interestRecords = exploreModel.exploreData.interestRecords[position]
        Glide.with(context).load(exploreModel.base_url + "/interest_icons/" + interestRecords.interestPic)
            .into(holder.itemView.ivTopicImage)
        holder.itemView.tvTopicName.text = interestRecords.interestName
    }
}
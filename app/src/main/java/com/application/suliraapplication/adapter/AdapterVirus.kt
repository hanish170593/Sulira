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

class AdapterVirus(val context: Context, var newsFeedModel: NewsFeedModel) : RecyclerView.Adapter<AdapterVirus.ViewHolderVirus>() {

    class ViewHolderVirus(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVirus =
        ViewHolderVirus(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_health_layout, parent, false
            )
        )

    override fun getItemCount(): Int =newsFeedModel.newsData.virusRecords.size

    override fun onBindViewHolder(holder: ViewHolderVirus, position: Int) {
        val virusRecords = newsFeedModel.newsData.virusRecords[position]
        Glide.with(context).load(newsFeedModel.base_url + "/virus/" + virusRecords.virusImage)
            .into(holder.itemView.ivHealthImage)

        holder.itemView.tvHealthTitle.text=virusRecords.virusTitle
        holder.itemView.tvHealthTime.text=virusRecords.create_at

    }
}
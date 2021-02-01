package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.ExploreModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_get_inspired_layout.view.*
import kotlinx.android.synthetic.main.adapter_topics_layout.view.*

class AdapterExploreGetInspired(
    val context: Context,
    var exploreModel: ExploreModel
) :
    RecyclerView.Adapter<AdapterExploreGetInspired.ExploreInspiredViewHolder>() {

    class ExploreInspiredViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreInspiredViewHolder =
        ExploreInspiredViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_get_inspired_layout, parent, false)
        )

    override fun getItemCount(): Int = exploreModel.exploreData.inspireRecords.size

    override fun onBindViewHolder(holder: ExploreInspiredViewHolder, position: Int) {

        val inspireRecordsClass = exploreModel.exploreData.inspireRecords[position]

        Glide.with(context).load(exploreModel.base_url + "/inspire/" + inspireRecordsClass.inspireImage)
            .into(holder.itemView.ivGetInsImage)

        holder.itemView.tvGetInsHeading.text=inspireRecordsClass.inspireTitle
        holder.itemView.tvInspiredType.text=inspireRecordsClass.inspireSubTitle
    }
}
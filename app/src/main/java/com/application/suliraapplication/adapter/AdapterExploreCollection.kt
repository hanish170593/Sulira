package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.ExploreModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_collection_layout.view.*
import kotlinx.android.synthetic.main.adapter_get_inspired_layout.view.*

class AdapterExploreCollection(
    val context: Context,
    var exploreModel: ExploreModel
) :
    RecyclerView.Adapter<AdapterExploreCollection.CollectionViewHolder>() {

    class CollectionViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder =
        CollectionViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_collection_layout, parent, false)
        )

    override fun getItemCount(): Int = exploreModel.exploreData.collectionRecords.size

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val collectionRecordsClass = exploreModel.exploreData.collectionRecords[position]

        Glide.with(context).load(exploreModel.base_url + "/collection/" + collectionRecordsClass.collectionImage)
            .into(holder.itemView.ivCollectionImage)

        holder.itemView.tvCollectionTitle.text=collectionRecordsClass.collectionType
        holder.itemView.tvCollText.text=collectionRecordsClass.collectionTitle
        holder.itemView.btnArticles.text=collectionRecordsClass.collectionArtical
    }
}
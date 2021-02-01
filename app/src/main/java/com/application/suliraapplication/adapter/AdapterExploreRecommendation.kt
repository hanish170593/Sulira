package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.ExploreModel
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.adapter_recomendation_layout.view.*

class AdapterExploreRecommendation(
    val context: Context,
    var exploreModel: ExploreModel
) :
    RecyclerView.Adapter<AdapterExploreRecommendation.RecommendationViewHolder>() {

    class RecommendationViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder =
        RecommendationViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_recomendation_layout, parent, false)
        )

    override fun getItemCount(): Int = exploreModel.exploreData.exploreRecords.size

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val exploreRecords = exploreModel.exploreData.exploreRecords[position]

        Glide.with(context).load(exploreModel.base_url + "/explore/" + exploreRecords.exploreImage)
            .into(holder.itemView.ivRecoImage)

        val userData: SignUpModel = Gson().fromJson(
            PreferenceManager().userResponse.toString(),
            SignUpModel::class.java
        )

        Glide.with(context).load(userData.base_url + userData.userInfo.profilePic)
            .into(holder.itemView.ivRecoAvatar)

        holder.itemView.tvExplorePersonName.text = userData.userInfo.fullName

        holder.itemView.tvRecoTitle.text = exploreRecords.exploreTitle
    }
}
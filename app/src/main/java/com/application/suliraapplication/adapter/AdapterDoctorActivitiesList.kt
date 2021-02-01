package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.DoctorDetailModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_health_layout.view.*

class AdapterDoctorActivitiesList(
    val context: Context,
    var doctorDetailModel: DoctorDetailModel
) : RecyclerView.Adapter<AdapterDoctorActivitiesList.ViewHolderHealth>() {

    class ViewHolderHealth(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHealth =
        ViewHolderHealth(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_health_layout, parent, false
            )
        )

    override fun getItemCount(): Int = doctorDetailModel.doctorDetail.activities.size

    override fun onBindViewHolder(holder: ViewHolderHealth, position: Int) {
        val activities = doctorDetailModel.doctorDetail.activities[position]
        Glide.with(context).load(doctorDetailModel.urlActivitiesImage + activities.actImage)
            .into(holder.itemView.ivHealthImage)

        holder.itemView.tvHealthTitle.text = activities.actText
        holder.itemView.tvHealthTime.text = activities.create_at

    }
}
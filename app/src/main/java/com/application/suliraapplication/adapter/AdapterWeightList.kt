package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.HealthLogArray
import kotlinx.android.synthetic.main.adapter_layout_weight_list.view.*

class AdapterWeightList(
    var listModel: List<HealthLogArray>,
    val context: Context):RecyclerView.Adapter<AdapterWeightList.ViewHolderWeightList>() {

    class ViewHolderWeightList(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWeightList = ViewHolderWeightList(LayoutInflater.from(context).inflate(
        R.layout.adapter_layout_weight_list,parent,false))

    override fun getItemCount(): Int = listModel.size

    override fun onBindViewHolder(holder: ViewHolderWeightList, position: Int) {
       holder.itemView.tvDate.text=listModel[position].create_at
       holder.itemView.tvProgressData.text=listModel[position].healthValue
    }
}
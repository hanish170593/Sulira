package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.HealthConditionModel
import com.application.suliraapplication.pojo.ModelHealthCondition
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_layout_health_condition.view.*

class AdapterHealthCondition(var listModel: HealthConditionModel, val context: Context) :
    RecyclerView.Adapter<AdapterHealthCondition.ViewHolderHealthCondition>() {

    var colorSelected: Int = R.color.color_one

    class ViewHolderHealthCondition(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHealthCondition =
        ViewHolderHealthCondition(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_layout_health_condition, parent, false
            )
        )

    override fun getItemCount(): Int = listModel.purposeRecords.size

    override fun onBindViewHolder(holder: ViewHolderHealthCondition, position: Int) {

        Glide.with(context).load(listModel.picUrl+listModel.purposeRecords[position].healthConditionPic).into(holder.itemView.ivHealthCondition)
        holder.itemView.tvHealthConditionName.text = listModel.purposeRecords[position].healthCondition

        when (listModel.purposeRecords[position].isSelected) {

            true -> {
                holder.itemView.llLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        listModel.purposeRecords[position].colorName
                    )
                )
            }

            false -> {
                holder.itemView.llLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        }


        holder.itemView.setOnClickListener {
            listModel.purposeRecords[position].isSelected = !listModel.purposeRecords[position].isSelected
            listModel.purposeRecords[position].colorName = colorSelected
            when (colorSelected) {
                R.color.color_one -> colorSelected = R.color.color_two
                R.color.color_two -> colorSelected = R.color.color_three
                R.color.color_three -> colorSelected = R.color.color_four
                R.color.color_four -> colorSelected = R.color.color_one
            }
            notifyDataSetChanged()
        }

    }

    fun itemList():HealthConditionModel{
       return listModel
    }
}
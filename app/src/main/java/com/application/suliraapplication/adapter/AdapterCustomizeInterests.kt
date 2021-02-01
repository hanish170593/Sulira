package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.InterestModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_customize_interest.view.*

class AdapterCustomizeInterests(
    var listModelInterest: InterestModel,
    val context: Context
) : RecyclerView.Adapter<AdapterCustomizeInterests.ViewHolderInterests>() {

    var colorSelected: Int = R.color.color_one

    class ViewHolderInterests(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderInterests =
        ViewHolderInterests(
            LayoutInflater.from(context).inflate(R.layout.adapter_customize_interest, parent, false)
        )

    override fun getItemCount(): Int = listModelInterest.interestRecords.size

    override fun onBindViewHolder(holder: ViewHolderInterests, position: Int) {

        Glide.with(context).load(listModelInterest.picUrl+listModelInterest.interestRecords[position].interestPic).into( holder.itemView.ivInterestIcon)
        holder.itemView.tvInterestName.text = listModelInterest.interestRecords[position].interestName

        when (listModelInterest.interestRecords[position].isSelected) {

            true -> {
                holder.itemView.llCardView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        listModelInterest.interestRecords[position].colorName
                    )
                )
            }
            false -> {
                holder.itemView.llCardView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                // holder.itemView.ivInterestIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
            }
        }

        holder.itemView.setOnClickListener {
            listModelInterest.interestRecords[position].isSelected = !listModelInterest.interestRecords[position].isSelected!!
            listModelInterest.interestRecords[position].colorName = colorSelected
            when (colorSelected) {
                R.color.color_one -> colorSelected = R.color.color_two
                R.color.color_two -> colorSelected = R.color.color_three
                R.color.color_three -> colorSelected = R.color.color_four
                R.color.color_four -> colorSelected = R.color.color_one
            }
            notifyDataSetChanged()
        }
        }

    fun getListItems():InterestModel{
        return listModelInterest
    }

}
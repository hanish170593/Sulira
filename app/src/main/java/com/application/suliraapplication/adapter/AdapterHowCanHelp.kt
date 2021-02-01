package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.HowCanHelpModel
import com.application.suliraapplication.pojo.ModelHowCanHelp
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_layout_how_we_can_help.view.*


class AdapterHowCanHelp(var listHowCanHelp: HowCanHelpModel, val context: Context) : RecyclerView.Adapter<AdapterHowCanHelp.ViewHolderHowCanHelp>() {

    class ViewHolderHowCanHelp(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHowCanHelp = ViewHolderHowCanHelp(LayoutInflater.from(context).inflate(R.layout.adapter_layout_how_we_can_help, parent, false))

    override fun getItemCount(): Int = listHowCanHelp.purposeRecords.size

    override fun onBindViewHolder(holder: ViewHolderHowCanHelp, position: Int) {

        Glide.with(context).load(listHowCanHelp.picUrl+listHowCanHelp.purposeRecords[position].purposePic).into(holder.itemView.ivIcon)
        holder.itemView.tvHowCanHelp.text = listHowCanHelp.purposeRecords[position].purposeName

        when (listHowCanHelp.purposeRecords[position].isSelected) {

            true -> {

                if (position % 2 == 0) {
                    holder.itemView.llHow.background = ContextCompat.getDrawable(context, R.drawable.grey_interest_bg)
                } else {
                    holder.itemView.llHow.background = ContextCompat.getDrawable(context, R.drawable.orange_interest_bg)
                }
                holder.itemView.tvHowCanHelp.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.itemView.cbHow.isChecked = true

            }

            false -> {
                holder.itemView.llHow.background = ContextCompat.getDrawable(context, R.drawable.white_back_how_we_can)
                holder.itemView.tvHowCanHelp.setTextColor(ContextCompat.getColor(context, R.color.light_black))
                holder.itemView.cbHow.isChecked = false
            }

        }

        holder.itemView.setOnClickListener {
            listHowCanHelp.purposeRecords[position].isSelected = !listHowCanHelp.purposeRecords[position].isSelected
            context.run { notifyDataSetChanged() }
        }

    }

    fun itemList():HowCanHelpModel{
        return listHowCanHelp
    }

}
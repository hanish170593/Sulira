package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import kotlinx.android.synthetic.main.adapter_text_layout.view.*

class AdapterHourly(var listChar: List<String>, val context: Context) :
    RecyclerView.Adapter<AdapterHourly.HourlyViewHolder>() {
       var setPos:Int = 2

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder =
        HourlyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.adapter_text_layout, parent, false))

    override fun getItemCount(): Int = listChar.size

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.itemView.tvText.text = listChar[position]
        if (position==setPos){
            holder.itemView.tvText.setTextColor(ContextCompat.getColor(context,R.color.app_color))
            holder.itemView.tvText.setBackgroundColor(ContextCompat.getColor(context,R.color.o_minus))
        }else{
            holder.itemView.tvText.setTextColor(ContextCompat.getColor(context,R.color.black))
            holder.itemView.tvText.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
        }
    }

    fun setData( listCharData: List<String>){
        listChar=listCharData
        notifyDataSetChanged()
    }

    fun selectItemAtIndex(i: Int) {
        setPos=i
        notifyDataSetChanged()
    }
}
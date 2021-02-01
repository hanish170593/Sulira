package com.application.suliraapplication.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.application.suliraapplication.R

class AdapterLastWeek(var itemsData: ArrayList<String>, val context: Context) : BaseAdapter() {
    private class ListRowHolder(row: View?) {

        init {
           
        }
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ListRowHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_layout_last_week, parent, false)
            viewHolder = ListRowHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ListRowHolder
        }



        return view!!
    }

    override fun getItem(position: Int): Any = itemsData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = 3
}
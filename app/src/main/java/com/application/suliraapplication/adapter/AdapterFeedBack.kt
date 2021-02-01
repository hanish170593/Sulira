package com.application.suliraapplication.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatImageView
import com.application.suliraapplication.R
import com.application.suliraapplication.pojo.FeedBackModel
import com.lukedeighton.wheelview.adapter.WheelAdapter

class AdapterFeedBack(
    val context: Context,
    var listFeedBackList: ArrayList<FeedBackModel>
) : BaseAdapter(), WheelAdapter {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val holder: FeedBackHolder
        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_layout_feedback, parent, false)
            holder = FeedBackHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as FeedBackHolder
        }
        //holder.ivUserFeedBackIcon.setImageDrawable(listFeedBackList[position].icon)
        return view

    }

    override fun getItem(position: Int): Any = listFeedBackList[position]

    override fun getItemId(position: Int): Long = position.toLong()
    override fun getDrawable(position: Int): Drawable {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int = listFeedBackList.size

    private class FeedBackHolder(layoutView: View?) {
        var ivUserFeedBackIcon: AppCompatImageView =
            layoutView!!.findViewById(R.id.ivUserFeedBackIcon)
    }
}
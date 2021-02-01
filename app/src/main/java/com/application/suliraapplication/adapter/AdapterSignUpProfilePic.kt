package com.application.suliraapplication.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import kotlinx.android.synthetic.main.adapter_layout_profile_pic.view.*

class AdapterSignUpProfilePic(
    var listModel: ArrayList<Drawable>,
    val context: Context,
    var pos: Int
) :
    RecyclerView.Adapter<AdapterSignUpProfilePic.ViewHolderProfilePic>() {

    class ViewHolderProfilePic(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProfilePic =
        ViewHolderProfilePic(
            LayoutInflater.from(context).inflate(R.layout.adapter_layout_profile_pic, parent, false)
        )

    override fun getItemCount(): Int = listModel.size

    override fun onBindViewHolder(holder: ViewHolderProfilePic, position: Int) {
        holder.itemView.ivPic.setImageDrawable(listModel[position])
        when (position) {

            pos -> {
                holder.itemView.llLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.profile_back_color
                    )
                )
                holder.itemView.ivDown.visibility = View.VISIBLE
                holder.itemView.ivUp.visibility = View.VISIBLE
            }

            else -> {
                holder.itemView.llLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                holder.itemView.ivDown.visibility = View.GONE
                holder.itemView.ivUp.visibility = View.GONE
            }
        }
    }

    fun setSelectedPos(position: Int) {
        pos = position
        notifyDataSetChanged()
    }

}
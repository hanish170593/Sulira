package com.application.suliraapplication.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.fragments.DoctorProfileFragment
import com.application.suliraapplication.models.DoctorsList
import com.application.suliraapplication.models.MemberProfileModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_layout_doctor_list.view.*

class AdapterDoctorList(
    val context: Context,
    var memberProfileModel: MemberProfileModel,
    var onClickChatWithDoctor: OnClickChatOption
) :
    RecyclerView.Adapter<AdapterDoctorList.DoctorListViewHolder>() {


    class DoctorListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListViewHolder =
        DoctorListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.adapter_layout_doctor_list, parent, false)
        )

    override fun getItemCount(): Int = memberProfileModel.UserInfo.doctors.size

    override fun onBindViewHolder(holder: DoctorListViewHolder, position: Int) {
        val doctorsList = memberProfileModel.UserInfo.doctors[position]

        Glide.with(context)
            .load("http://hourlylancer.com/devlop/sulira/backend/assets/uploads/doctorProfileImage/" + doctorsList.doctorProfileImage)
            .into(holder.itemView.ivDoctorImage)

        holder.itemView.tvDoctorName.text = doctorsList.doctorName
        holder.itemView.tvDoctorDesignation.text = doctorsList.doctorDesignation

        holder.itemView.ivChatDoctor.setOnClickListener {
            onClickChatWithDoctor.onClickChatWithDoctor(doctorsList)
        }

        holder.itemView.ivDoctorImage.setOnClickListener {
            (context as HomeTabActivity).replaceFragmentWithBackStack(
                DoctorProfileFragment(
                    doctorsList.id
                )
            )
        }

        holder.itemView.tvDoctorName.setOnClickListener {
            (context as HomeTabActivity).replaceFragmentWithBackStack(
                DoctorProfileFragment(
                    doctorsList.id
                )
            )
        }

        holder.itemView.ivCallDoctor.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.fromParts("tel", doctorsList.doctorMobile, null)
            )
            context.startActivity(intent)
        }
    }

    interface OnClickChatOption {
        fun onClickChatWithDoctor(data: DoctorsList)
    }

}
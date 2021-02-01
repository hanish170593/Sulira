package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.StripeCrads
import kotlinx.android.synthetic.main.adapter_layout_final_payment.view.*

class AdapterFinalPayment(var listModel: List<StripeCrads>, val context: Context) :
    RecyclerView.Adapter<AdapterFinalPayment.ViewHolderFinalPayment>() {

    class ViewHolderFinalPayment(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFinalPayment =
        ViewHolderFinalPayment(
            LayoutInflater.from(context).inflate(
                R.layout.adapter_layout_final_payment, parent, false
            )
        )

    override fun getItemCount(): Int = listModel.size

    override fun onBindViewHolder(holder: ViewHolderFinalPayment, position: Int) {

        // holder.itemView.ivCardImage.setImageDrawable(listModel[position].icon)
        //holder.itemView.tvCardType.text = listModel[position].cardType
        holder.itemView.tvCardNumber.text = "xxxxxxxxxxxx" + listModel[position].last4

        holder.itemView.rbSelectedCard.isChecked = listModel[position].isSelected

        holder.itemView.setOnClickListener {
            for (i in listModel) {
                i.isSelected = false
            }
            listModel[position].isSelected = true
            notifyDataSetChanged()
        }

    }

    fun getSelectedCard(): List<StripeCrads> {
        return listModel;
    }
}
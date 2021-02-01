package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.suliraapplication.R
import com.application.suliraapplication.models.PostDetailModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feedback_post.view.*

class AdapterFeedbackPost(var postDetailModel: PostDetailModel, val context: Context) :
    RecyclerView.Adapter<AdapterFeedbackPost.ViewHolderFeedbackPost>() {

    class ViewHolderFeedbackPost(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFeedbackPost =
        ViewHolderFeedbackPost(
            LayoutInflater.from(context).inflate(
                R.layout.feedback_post, parent, false
            )
        )

    override fun getItemCount(): Int = postDetailModel.postDetail.comment.size

    override fun onBindViewHolder(holder: ViewHolderFeedbackPost, position: Int) {
        val comment = postDetailModel.postDetail.comment[position]

        Glide.with(context).load(postDetailModel.profilePicUrl+comment.profilePic).into(holder.itemView.ivFeedIcon)

        holder.itemView.tvFeedPersonName.text = comment.fullName
        holder.itemView.tvTimeAgo.text = comment.create_at
        holder.itemView.tvFeedback.text = comment.comment
        val rating = comment.ratting
        holder.itemView.tvRating.text = "★ $rating"
    }
}
package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.AdapterFeedbackPost
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.PostDetailModel
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.AddRatingAndCommentOrFollowViewModel
import com.application.suliraapplication.viewmodels.AddToChatViewModel
import com.application.suliraapplication.viewmodels.PostDetailViewModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_post_detail.*

class PostDetailFragment : BaseFragment(), RatingBar.OnRatingBarChangeListener,
    View.OnClickListener {

    private lateinit var adapterFeedback: AdapterFeedbackPost
    private lateinit var postDetailModel: PostDetailModel
    private lateinit var REQUEST_TYPE: String
    private val RATING = "rating"
    private val FOLLOW = "follow"
    private val COMMENT = "comment"

    private val postDetailViewModel by lazy {
        ViewModelProvider(this).get(PostDetailViewModel::class.java)
    }

    private val addRatingAndCommentOrFollowViewModel by lazy {
        ViewModelProvider(this).get(AddRatingAndCommentOrFollowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener(this)
        btnFollow.setOnClickListener(this)
        btnPostComment.setOnClickListener(this)
        postDetailViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                postDetailModel = postDetailViewModel.postDetailModel
                setCommentListAdapter()
            } else {
                setError(postDetailViewModel.message)
            }
        })

        addRatingAndCommentOrFollowViewModel.successful.observe(requireActivity(), Observer {
            when (REQUEST_TYPE) {

                RATING -> {
                    // dismissDialog()
                }

                FOLLOW -> {
                    // dismissDialog()
                }

                COMMENT -> {
                    //dismissDialog()
                    if (it) {
                        etPostComment.text?.clear()
                    }
                }
            }

            if (it) {
                setError(addRatingAndCommentOrFollowViewModel.customResponse.msg)
                postDetailViewModel.getPostDetail("2", "2")
            } else {
                setError(addRatingAndCommentOrFollowViewModel.message)
            }
        })

        rbUserRating.onRatingBarChangeListener = this

        showDialog()
        postDetailViewModel.getPostDetail("2", "2")

    }

    private fun setCommentListAdapter() {
        tvPostTitle.text = postDetailModel.postDetail.postTitle

        Glide.with(requireActivity())
            .load(postDetailModel.profilePicUrl + postDetailModel.postDetail.profilePic)
            .into(ivPostUserImage)

        tvPostUserName.text = postDetailModel.postDetail.fullName
        tvPostDescription.text = postDetailModel.postDetail.postDescription
        rbUserRating.rating = postDetailModel.postDetail.ratting.toFloat()
        tvOverallRating.text = postDetailModel.postDetail.userRatting.ratting
        val commentCount = postDetailModel.postDetail.commentCount
        tvReviewCount.text = "($commentCount Reviews)"

        adapterFeedback = AdapterFeedbackPost(postDetailModel, requireContext())

        rvFeedback.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AdapterFeedbackPost(postDetailModel, requireContext())
            adapter = adapterFeedback
        }

        val signUpModel: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)

        Glide.with(requireActivity())
            .load(postDetailModel.profilePicUrl + signUpModel.userInfo.profilePic).into(ivUserImage)

        Glide.with(requireActivity())
            .load(postDetailModel.postImageUrl + postDetailModel.postDetail.postImage)
            .into(ivPostImage)

        btnFollow.text = if (postDetailModel.postDetail.Follow > 0) "UnFollow" else "Follow"
    }

    override fun onRatingChanged(ratingBar: RatingBar?, p1: Float, p2: Boolean) {
        REQUEST_TYPE = RATING
        //showDialog()
        addRatingAndCommentOrFollowViewModel.addRating(
            PreferenceManager().userId.toString(),
            postDetailModel.postDetail.id,
            p1.toString()
        )
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            btnFollow -> {
                REQUEST_TYPE = FOLLOW
                val isFollow: String = if (btnFollow.text == "Follow") "1" else "0"

                //showDialog()
                addRatingAndCommentOrFollowViewModel.followRequest(
                    PreferenceManager().userId.toString(),
                    postDetailModel.postDetail.userId, isFollow
                )
            }

            ivBack -> (activity as HomeTabActivity).onBackPressed()

            btnPostComment -> {

                val comment = etPostComment.text.toString()

                if (TextUtils.isEmpty(comment)) {
                    setError("Please write a comment")
                    return
                }

                REQUEST_TYPE = COMMENT
                //showDialog()
                addRatingAndCommentOrFollowViewModel.addComment(
                    postDetailModel.postDetail.id,
                    PreferenceManager().userId.toString(),
                    postDetailModel.postDetail.userRatting.id,
                    comment
                )
            }
        }

    }

}
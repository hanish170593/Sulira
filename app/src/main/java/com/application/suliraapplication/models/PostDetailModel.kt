package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostDetailModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("profilePicUrl")
    @Expose
    var profilePicUrl: String,

    @SerializedName("postImageUrl")
    @Expose
    var postImageUrl: String,

    @SerializedName("postDetail")
    @Expose
    var postDetail: PostDetail

) : Parcelable

@Parcelize
data class PostDetail(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("postTitle")
    @Expose
    var postTitle: String,

    @SerializedName("postImage")
    @Expose
    var postImage: String,

    @SerializedName("postDescription")
    @Expose
    var postDescription: String,

    @SerializedName("postHeart")
    @Expose
    var postHeart: String,

    @SerializedName("postSugar")
    @Expose
    var postSugar: String,

    @SerializedName("postWeight")
    @Expose
    var postWeight: String,

    @SerializedName("postSleep")
    @Expose
    var postSleep: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String,

    @SerializedName("update_at")
    @Expose
    var update_at: String,

    @SerializedName("fullName")
    @Expose
    var fullName: String,

    @SerializedName("profilePic")
    @Expose
    var profilePic: String,

    @SerializedName("Follow")
    @Expose
    var Follow: Int,

    @SerializedName("ratting")
    @Expose
    var ratting: String,

    @SerializedName("commentCount")
    @Expose
    var commentCount: String,

    @SerializedName("userRatting")
    @Expose
    var userRatting: UserRatting,

    @SerializedName("comment")
    @Expose
    var comment: List<Comment>

) : Parcelable

@Parcelize
data class UserRatting(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("from")
    @Expose
    var from: String,

    @SerializedName("to")
    @Expose
    var to: String,

    @SerializedName("ratting")
    @Expose
    var ratting: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String

) : Parcelable

@Parcelize
data class Comment(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("from")
    @Expose
    var from: String,

    @SerializedName("to")
    @Expose
    var to: String,

    @SerializedName("rattingId")
    @Expose
    var rattingId: String,

    @SerializedName("comment")
    @Expose
    var comment: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String,

    @SerializedName("ratting")
    @Expose
    var ratting: String,

    @SerializedName("fullName")
    @Expose
    var fullName: String,

    @SerializedName("profilePic")
    @Expose
    var profilePic: String

) : Parcelable
package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatListModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("base_url")
    @Expose
    var base_url:String,

    @SerializedName("chatData")
    @Expose
    var chatData: List<ChatData>

) : Parcelable

@Parcelize
data class ChatData(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("chatRef")
    @Expose
    var chatRef: String,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("receiverId")
    @Expose
    var receiverId: String,

    @SerializedName("lastMessage")
    @Expose
    var lastMessage: String,

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
    var profilePic: String

) : Parcelable
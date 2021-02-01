package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatHistoryModel(
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("url")
    @Expose
    var url: String,

    @SerializedName("ChatHistory")
    @Expose
    var ChatHistory: List<ChatHistoryData>

) : Parcelable

@Parcelize
data class ChatHistoryData(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("chatRef")
    @Expose
    var chatRef: String,

    @SerializedName("senderId")
    @Expose
    var senderId: String,

    @SerializedName("receiverId")
    @Expose
    var receiverId: String,

    @SerializedName("userType")
    @Expose
    var userType: String,

    @SerializedName("message")
    @Expose
    var message: String,

    @SerializedName("chatImage")
    @Expose
    var chatImage: String,

    @SerializedName("created_at")
    @Expose
    var created_at: String

) : Parcelable
package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("baseUrl")
    @Expose
    var baseUrl: String,

    @SerializedName("notificationData")
    @Expose
    var notificationData: List<NotificationData>

) : Parcelable


@Parcelize
data class NotificationData(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("notificationRef")
    @Expose
    var notificationRef: String,

    @SerializedName("notificationTitle")
    @Expose
    var notificationTitle: String,

    @SerializedName("notificationText")
    @Expose
    var notificationText: String,

    @SerializedName("notificationImage")
    @Expose
    var notificationImage: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String

) : Parcelable
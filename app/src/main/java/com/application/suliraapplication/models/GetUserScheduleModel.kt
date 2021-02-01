package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetUserScheduleModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("scheduleData")
    @Expose
    var scheduleData: ScheduleData

) : Parcelable

@Parcelize
data class ScheduleData(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("type")
    @Expose
    var type: String,

    @SerializedName("monday")
    @Expose
    var monday: String,

    @SerializedName("tuesday")
    @Expose
    var tuesday: String,

    @SerializedName("wednesday")
    @Expose
    var wednesday: String,

    @SerializedName("thursday")
    @Expose
    var thursday: String,

    @SerializedName("friday")
    @Expose
    var friday: String,

    @SerializedName("saturday")
    @Expose
    var saturday: String,

    @SerializedName("sunday")
    @Expose
    var sunday: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String,

    @SerializedName("updated_at")
    @Expose
    var updated_at: String

) : Parcelable
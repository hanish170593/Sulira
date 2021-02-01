package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HealthProgressDataModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("lastUpdatedDate")
    @Expose
    var lastUpdatedDate: String,

    @SerializedName("lastUpdatedValue")
    @Expose
    var lastUpdatedValue: String,

    @SerializedName("bmiVal")
    @Expose
    var bmiVal: String,

    @SerializedName("bmiText")
    @Expose
    var bmiText: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("healthLogArray")
    @Expose
    var healthLogArray: List<HealthLogArray>

) : Parcelable

@Parcelize
data class HealthLogArray(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("healthType")
    @Expose
    var healthType: String,

    @SerializedName("healthValue")
    @Expose
    var healthValue: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String,

    @SerializedName("grafTime")
    @Expose
    var grafTime:String

) : Parcelable
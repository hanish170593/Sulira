package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PremiumPlanModel(
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("subTitle")
    @Expose
    var subTitle: String,

    @SerializedName("Plans")
    @Expose
    var Plans: List<PlansData>
) : Parcelable

@Parcelize
data class PlansData(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("planType")
    @Expose
    var planType: String,

    @SerializedName("planPrice")
    @Expose
    var planPrice: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String,

    @SerializedName("saveing")
    @Expose
    var saveing: String,

    @SerializedName("monthly")
    @Expose
    var monthly: String

) : Parcelable
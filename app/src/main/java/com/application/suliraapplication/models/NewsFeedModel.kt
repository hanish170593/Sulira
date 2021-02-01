package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsFeedModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("base_url")
    @Expose
    var base_url: String,

    @SerializedName("newsData")
    @Expose
    var newsData: NewsData

) : Parcelable

@Parcelize
data class NewsData(

    @SerializedName("newsFeedRecords")
    @Expose
    var newsFeedRecords: List<NewsFeedRecords>,

    @SerializedName("healthRecords")
    @Expose
    var healthRecords: List<HealthRecords>,

    @SerializedName("virusRecords")
    @Expose
    var virusRecords: List<VirusRecords>

) : Parcelable

@Parcelize
data class NewsFeedRecords(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("newsType")
    @Expose
    var newsType: String,

    @SerializedName("newsTitle")
    @Expose
    var newsTitle: String,

    @SerializedName("newsSubTitle")
    @Expose
    var newsSubTitle: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String

) : Parcelable

@Parcelize
data class HealthRecords(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("healthTitle")
    @Expose
    var healthTitle: String,

    @SerializedName("healthImage")
    @Expose
    var healthImage: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String

) : Parcelable

@Parcelize
data class VirusRecords(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("virusTitle")
    @Expose
    var virusTitle: String,

    @SerializedName("virusImage")
    @Expose
    var virusImage: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String

) : Parcelable
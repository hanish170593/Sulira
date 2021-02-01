package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HowCanHelpModel(
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("picUrl")
    @Expose
    var picUrl: String,

    @SerializedName("purposeRecords")
    @Expose
    var purposeRecords: List<PurposeRecords>


) : Parcelable

@Parcelize
data class PurposeRecords(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("purposeName")
    @Expose
    var purposeName: String,

    @SerializedName("purposePic")
    @Expose
    var purposePic: String,

    @SerializedName("purposeStatus")
    @Expose
    var purposeStatus: String,

    var isSelected:Boolean

) : Parcelable
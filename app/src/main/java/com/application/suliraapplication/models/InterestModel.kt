package com.application.suliraapplication.models

import android.os.Parcelable
import com.application.suliraapplication.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InterestModel( @SerializedName("status")
                          @Expose
                          var status: String,

                          @SerializedName("msg")
                          @Expose
                          var msg: String,

                          @SerializedName("picUrl")
                          @Expose
                          var picUrl: String,

                          @SerializedName("interestRecords")
                          @Expose
                          var interestRecords:List<InterestRecords>):Parcelable


@Parcelize
data class InterestRecords(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("interestName")
    @Expose
    var interestName: String,

    @SerializedName("interestPic")
    @Expose
    var interestPic: String,

    @SerializedName("interestStatus")
    @Expose
    var interestStatus: String,

   var isSelected: Boolean,

    var colorName:Int= R.color.white
):Parcelable
package com.application.suliraapplication.models

import android.graphics.drawable.Drawable
import android.os.Parcelable
import com.application.suliraapplication.R
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HealthConditionModel(

    @SerializedName("status")
    @Expose
    var status:String,

    @SerializedName("msg")
    @Expose
    var msg:String,

    @SerializedName("picUrl")
    @Expose
    var picUrl:String,

    @SerializedName("purposeRecords")
    @Expose
    var purposeRecords:List<HealthConditionListData>

):Parcelable

@Parcelize
data class HealthConditionListData(

    @SerializedName("id")
    @Expose
    var id:String,

    @SerializedName("healthCondition")
    @Expose
    var healthCondition:String,

    @SerializedName("healthConditionPic")
    @Expose
    var healthConditionPic:String,

    @SerializedName("healthConditionStatus")
    @Expose
    var healthConditionStatus:String,
    var isSelected: Boolean,
    var colorName:Int

):Parcelable


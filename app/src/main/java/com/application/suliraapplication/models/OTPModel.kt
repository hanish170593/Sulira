package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OTPModel(

    @SerializedName("status")
    @Expose
    var status:String,

    @SerializedName("msg")
    @Expose
    var msg:String,

    @SerializedName("otp")
    @Expose
    var otp:Int

):Parcelable
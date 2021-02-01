package com.application.suliraapplication.models

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PolicyModel(
    @SerializedName("status")
    @Expose
    var status:String,

    @SerializedName("msg")
    @Expose
    var msg:String,

    @SerializedName("policy")
    @Expose
    var policy:PolicyData

):Parcelable

@Parcelize
class PolicyData(
    @SerializedName("termsCondiation")
    @Expose
    var termsCondiation:String,

    @SerializedName("privacyPolicy")
    @Expose
    var privacyPolicy:String
):Parcelable
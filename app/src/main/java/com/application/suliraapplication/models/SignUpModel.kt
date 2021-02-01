package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("base_url")
    @Expose
    var base_url: String,

    @SerializedName("UserInfo")
    @Expose
    var userInfo: UserInfo

) : Parcelable

@Parcelize
data class UserInfo(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("fullName")
    @Expose
    var fullName: String,

    @SerializedName("email")
    @Expose
    var email: String,

    @SerializedName("phone")
    @Expose
    var phone: String,

    @SerializedName("age")
    @Expose
    var age: String,

    @SerializedName("password")
    @Expose
    var password: String,

    @SerializedName("gender")
    @Expose
    var gender: String,

    @SerializedName("weight")
    @Expose
    var weight: String,

    @SerializedName("height")
    @Expose
    var height: String,

    @SerializedName("bloodGroup")
    @Expose
    var bloodGroup: String,

    @SerializedName("exercise")
    @Expose
    var exercise: String,

    @SerializedName("exerciseName")
    @Expose
    var exerciseName: String,

    @SuppressWarnings("notification")
    @Expose
    var notification: String,

    @SerializedName("profilePic")
    @Expose
    var profilePic: String,

    @SerializedName("deviceType")
    @Expose
    var deviceType: String,

    @SerializedName("deviceToken")
    @Expose
    var deviceToken: String,

    @SerializedName("created_at")
    @Expose
    var created_at: String,

    @SerializedName("loginStatus")
    @Expose
    var loginStatus: String,

    @SerializedName("stripeToken")
    @Expose
    var stripeToken:String,

    @SerializedName("Is_Active")
    @Expose
    var Is_Active: String,

    @SerializedName("is_Premium")
    @Expose
    var is_Premium: String,

    @SerializedName("validationCode")
    @Expose
    var validationCode: String

) : Parcelable
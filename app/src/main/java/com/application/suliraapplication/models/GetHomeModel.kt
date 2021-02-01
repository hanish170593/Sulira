package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class GetHomeModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("userRecord")
    @Expose
    var userRecord: UserRecord

) : Parcelable

@Parcelize
class UserRecord(

    @SerializedName("avgHealthScore")
    @Expose
    var avgHealthScore: String,

    @SerializedName("userDetails")
    @Expose
    var userDetails: UserDetails,

    @SerializedName("heartRate")
    @Expose
    var heartRate: HealthData,

    @SerializedName("weight")
    @Expose
    var weight: HealthData,

    @SerializedName("sugar")
    @Expose
    var sugar: HealthData,

    @SerializedName("sleep")
    @Expose
    var sleep: SleepHealthData

) : Parcelable


@Parcelize
class UserDetails(

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

    @SerializedName("notification")
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

    @SerializedName("Is_Active")
    @Expose
    var Is_Active: String

) : Parcelable

@Parcelize
class SleepHealthData(
    @SerializedName("health_value")
    @Expose
    var health_value: String,

    @SerializedName("updated_at")
    @Expose
    var updated_at: String,

    @SerializedName("sleep_at")
    @Expose
    var sleep_at: String,

    @SerializedName("wakeup_at")
    @Expose
    var wakeup_at: String
) : Parcelable

@Parcelize
class HealthData(

    @SerializedName("health_value")
    @Expose
    var health_value: String,

    @SerializedName("updated_at")
    @Expose
    var updated_at: String

) : Parcelable
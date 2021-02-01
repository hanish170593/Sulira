package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberProfileModel(

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
    var UserInfo: UserInfoData

) : Parcelable

@Parcelize
data class UserInfoData(

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

    @SerializedName("about")
    @Expose
    var about: String,

    @SerializedName("exercise")
    @Expose
    var exercise: String,

    @SerializedName("exerciseName")
    @Expose
    var exerciseName: String,

    @SerializedName("profilePic")
    @Expose
    var profilePic: String,

    @SerializedName("is_Premium")
    @Expose
    var is_Premium: String,

    @SerializedName("healthParameters")
    @Expose
    var healthParameters: HealthParameters,

    @SerializedName("doctors")
    @Expose
    var doctors: List<DoctorsList>,

    @SerializedName("premium")
    @Expose
    var premium: Premium,

    @SerializedName("userHealthInfo")
    @Expose
    var userHealthInfo:UserHealthInfo

) : Parcelable

@Parcelize
data class UserHealthInfo(

    @SerializedName("heartRate")
    @Expose
    var heartRate:HealthData,

    @SerializedName("weight")
    @Expose
    var weight:HealthData,

    @SerializedName("sugar")
    @Expose
    var sugar:HealthData,

    @SerializedName("sleep")
    @Expose
    var sleep:SleepHealthData

):Parcelable

@Parcelize
data class HealthParameters(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("userId")
    @Expose
    var userId: String,

    @SerializedName("weight")
    @Expose
    var weight: String,

    @SerializedName("age")
    @Expose
    var age: String,

    @SerializedName("height")
    @Expose
    var height: String,

    @SerializedName("steps")
    @Expose
    var steps: String,

    @SerializedName("heartRate")
    @Expose
    var heartRate: String

) : Parcelable

@Parcelize
data class DoctorsList(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("doctorName")
    @Expose
    var doctorName: String,

    @SerializedName("doctorDesignation")
    @Expose
    var doctorDesignation: String,

    @SerializedName("doctorMobile")
    @Expose
    var doctorMobile: String,

    @SerializedName("doctorAbout")
    @Expose
    var doctorAbout: String,

    @SerializedName("doctorProfileImage")
    @Expose
    var doctorProfileImage: String

) : Parcelable

@Parcelize
data class Premium(

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("subTitle")
    @Expose
    var subTitle: String,

    @SerializedName("tagLine")
    @Expose
    var tagLine: String,

    @SerializedName("users")
    @Expose
    var users: List<Users>

) : Parcelable

@Parcelize
data class Users(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("fullName")
    @Expose
    var fullName: String,

    @SerializedName("profilePic")
    @Expose
    var profilePic: String

) : Parcelable
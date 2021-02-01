package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoctorDetailModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("urlDoctorImage")
    @Expose
    var urlDoctorImage: String,

    @SerializedName("urlActivitiesImage")
    @Expose
    var urlActivitiesImage: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("doctorDetail")
    @Expose
    var doctorDetail: DoctorDetail

) : Parcelable

@Parcelize
data class DoctorDetail(

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
    var doctorProfileImage: String,

    @SerializedName("doctorDeviceToken")
    @Expose
    var doctorDeviceToken: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String,

    @SerializedName("activities")
    @Expose
    var activities: List<Activities>

) : Parcelable

@Parcelize
data class Activities(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("doctorId")
    @Expose
    var doctorId: String,

    @SerializedName("actImage")
    @Expose
    var actImage: String,

    @SerializedName("actText")
    @Expose
    var actText: String,

    @SerializedName("create_at")
    @Expose
    var create_at: String

) : Parcelable
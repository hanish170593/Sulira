package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardListModel(

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg: String,

    @SerializedName("stripeCrads")
    @Expose
    var stripeCrads: List<StripeCrads>

) : Parcelable

@Parcelize
data class StripeCrads(

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("brand")
    @Expose
    var brand: String,

    @SerializedName("country")
    @Expose
    var country: String,

    @SerializedName("customer")
    @Expose
    var customer: String,

    @SerializedName("exp_month")
    @Expose
    var exp_month: String,

    @SerializedName("exp_year")
    @Expose
    var exp_year: String,

    @SerializedName("fingerprint")
    @Expose
    var fingerprint: String,

    @SerializedName("funding")
    @Expose
    var funding: String,

    @SerializedName("last4")
    @Expose
    var last4: String,

    var isSelected: Boolean = false

) : Parcelable
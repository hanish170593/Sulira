package com.application.suliraapplication.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExploreModel (

    @SerializedName("status")
    @Expose
    var status:String,

    @SerializedName("msg")
    @Expose
    var msg:String,

    @SerializedName("base_url")
    @Expose
    var base_url:String,

    @SerializedName("exploreData")
    @Expose
    var exploreData:ExploreDataClass
):Parcelable

@Parcelize
data class ExploreDataClass(
    @SerializedName("exploreRecords")
    @Expose
    var exploreRecords:List<ExploreRecords>,

    @SerializedName("interestRecords")
    @Expose
    var interestRecords:List<InterestRecordsClass>,

    @SerializedName("inspireRecords")
    @Expose
    var inspireRecords:List<InspireRecordsClass>,

    @SerializedName("collectionRecords")
    @Expose
    var collectionRecords:List<CollectionRecordsClass>

):Parcelable

@Parcelize
data class ExploreRecords(
    @SerializedName("id")
    @Expose
    var id:String,

    @SerializedName("exploreImage")
    @Expose
    var exploreImage:String,

    @SerializedName("exploreTitle")
    @Expose
    var exploreTitle:String,

    @SerializedName("create_at")
    @Expose
    var create_at:String

):Parcelable

@Parcelize
data class InterestRecordsClass(

    @SerializedName("id")
    @Expose
    var id:String,

    @SerializedName("interestName")
    @Expose
    var interestName:String,

    @SerializedName("interestPic")
    @Expose
    var interestPic:String,

    @SerializedName("interestStatus")
    @Expose
    var interestStatus:String

):Parcelable

@Parcelize
data class InspireRecordsClass(

    @SerializedName("id")
    @Expose
    var id:String,

    @SerializedName("inspireImage")
    @Expose
    var inspireImage:String,

    @SerializedName("inspireTitle")
    @Expose
    var inspireTitle:String,

    @SerializedName("inspireSubTitle")
    @Expose
    var inspireSubTitle:String,

    @SerializedName("create_at")
    @Expose
    var create_at:String

):Parcelable

@Parcelize
data class CollectionRecordsClass(

    @SerializedName("id")
    @Expose
    var id:String,

    @SerializedName("collectionType")
    @Expose
    var collectionType:String,

    @SerializedName("collectionImage")
    @Expose
    var collectionImage:String,

    @SerializedName("collectionTitle")
    @Expose
    var collectionTitle:String,

    @SerializedName("collectionArtical")
    @Expose
    var collectionArtical:String,

    @SerializedName("create_at")
    @Expose
    var create_at:String

):Parcelable
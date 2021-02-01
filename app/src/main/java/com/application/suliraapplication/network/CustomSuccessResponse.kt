package com.application.suliraapplication.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomSuccessResponse(
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("msg")
    @Expose
    var msg:String)
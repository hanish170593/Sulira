package com.application.suliraapplication.pojo

import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddPostCollectionModel {

    lateinit var userId:RequestBody
    lateinit var postTitle:RequestBody
    lateinit var postDecription:RequestBody
    lateinit var heartRate:RequestBody
    lateinit var sugar:RequestBody
    lateinit var weight:RequestBody
    lateinit var sleep:RequestBody
    lateinit var mood:RequestBody
    lateinit var postImage:MultipartBody.Part

}
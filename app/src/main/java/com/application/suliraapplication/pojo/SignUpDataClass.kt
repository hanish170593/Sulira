package com.application.suliraapplication.pojo

import okhttp3.MultipartBody
import okhttp3.RequestBody


class SignUpDataClass{

    lateinit var fullName:RequestBody
    lateinit var email:RequestBody
    lateinit var phone:RequestBody
    lateinit var age:RequestBody
    lateinit var password:RequestBody
    lateinit var gender:RequestBody
    lateinit var weight:RequestBody
    lateinit var height:RequestBody
    lateinit var bloodGroup:RequestBody
    lateinit var exercise:RequestBody
    lateinit var exerciseName:RequestBody
    lateinit var notification:RequestBody
    lateinit var deviceType:RequestBody
    lateinit var deviceToken:RequestBody
    lateinit var interestArray:RequestBody
    lateinit var healthArray:RequestBody
    lateinit var purposeArray:RequestBody
    lateinit var profilePic: MultipartBody.Part

}
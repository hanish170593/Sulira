package com.application.suliraapplication.network

import com.application.suliraapplication.models.*
import com.application.suliraapplication.pojo.AddPostCollectionModel
import com.application.suliraapplication.pojo.SignUpDataClass
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserDataManager {
    private val apiManager = ApiManager.instance
    fun getUser(): Observable<CustomResponse> {
        return apiManager.userService.getUsers()
    }

    fun getInterestList(): Observable<InterestModel> {
        return apiManager.userService.getInterests()
    }

    fun getHowCanHelpData(): Observable<HowCanHelpModel> {
        return apiManager.userService.getHowCanHelp()
    }

    fun getHealthConditionModel(): Observable<HealthConditionModel> {
        return apiManager.userService.getHealthConditionItemsList()
    }

    fun signUp(signUpDataClass: SignUpDataClass): Observable<SignUpModel> {
        return apiManager.userService.registerUser(
            signUpDataClass.fullName,
            signUpDataClass.email,
            signUpDataClass.phone,
            signUpDataClass.age,
            signUpDataClass.password,
            signUpDataClass.gender,
            signUpDataClass.weight,
            signUpDataClass.height,
            signUpDataClass.bloodGroup,
            signUpDataClass.exercise,
            signUpDataClass.exerciseName,
            signUpDataClass.notification,
            signUpDataClass.deviceType,
            signUpDataClass.deviceToken,
            signUpDataClass.interestArray,
            signUpDataClass.healthArray,
            signUpDataClass.purposeArray,
            signUpDataClass.profilePic
        )
    }

    fun getOtp(mobileNumber: String): Observable<OTPModel> {
        return apiManager.userService.getOtp(mobileNumber)
    }

    fun updateHealthData(
        userId: String,
        healthType: String,
        healthValue: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.updateHealthData(userId, healthType, healthValue)
    }

    fun logout(
        userId: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.logout(userId)
    }

    fun otpForForgetPassword(mobileNumber: String): Observable<OTPforForgetPasswordModel> {
        return apiManager.userService.otpForForgetPassword(mobileNumber)
    }

    fun forgetPassword(
        mobile: String,
        otp: String,
        password: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.forgetPassword(mobile, otp, password)
    }

    fun changePassword(
        userId: String,
        oldPassword: String,
        password: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.changePassword(userId, oldPassword, password)
    }

    fun editUser(
        userId: RequestBody,
        fullName: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        age: RequestBody,
        profilePic: MultipartBody.Part
    ): Observable<SignUpModel> {
        return apiManager.userService.editUser(userId, fullName, email, phone, age, profilePic)
    }

    fun login(
        phone: String,
        email: String,
        FieldType: String,
        Password: String,
        deviceToken: String,
        deviceType: String
    ): Observable<SignUpModel> {
        return apiManager.userService.login(
            phone,
            email,
            FieldType,
            Password,
            deviceToken,
            deviceType
        )
    }

    fun termsAndPrivacy(): Observable<PolicyModel> {
        return apiManager.userService.getPolicyData()
    }

    fun getHomeData(userId: String): Observable<GetHomeModel> {
        return apiManager.userService.getHomeData(userId)
    }

    fun feedPost(addPostCollectionModel: AddPostCollectionModel): Observable<CustomSuccessResponse> {
        return apiManager.userService.addPost(
            addPostCollectionModel.userId,
            addPostCollectionModel.postTitle,
            addPostCollectionModel.postDecription,
            addPostCollectionModel.heartRate,
            addPostCollectionModel.sugar,
            addPostCollectionModel.weight,
            addPostCollectionModel.sleep,
            addPostCollectionModel.mood,
            addPostCollectionModel.postImage
        )
    }

    fun getExplore(userId: String): Observable<ExploreModel> {
        return apiManager.userService.getExplore(userId)
    }

    fun getNewsFeed(userId: String): Observable<NewsFeedModel> {
        return apiManager.userService.getNewsFeed(userId)
    }

    fun getPremiumMemberUserInfo(userId: String): Observable<MemberProfileModel> {
        return apiManager.userService.getPremiumMemberUserInfo(userId)
    }

    fun getPostDetail(userId: String, postId: String): Observable<PostDetailModel> {
        return apiManager.userService.getPostDetail(userId, postId)
    }

    fun addRating(userId: String, postId: String, rate: String): Observable<CustomSuccessResponse> {
        return apiManager.userService.addRating(userId, postId, rate)
    }

    fun addComment(
        postId: String,
        userId: String,
        rateId: String,
        comment: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.addComment(postId, userId, rateId, comment)
    }

    fun getDoctorDetail(doctorId: String): Observable<DoctorDetailModel> {
        return apiManager.userService.getDoctorDetail(doctorId)
    }

    fun followRequest(
        userId: String,
        followTo: String,
        isFollow: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.followRequest(userId, followTo, isFollow)
    }

    fun getHealthLogData(
        userId: String,
        timePeriod: String,
        healthType: String
    ): Observable<HealthProgressDataModel> {
        return apiManager.userService.getHealthLogData(userId, timePeriod, healthType)
    }

    fun getChatList(userId: String, userType: String): Observable<ChatListModel> {
        return apiManager.userService.getChatList(userId, userType)
    }

    fun getChatHistory(chatRef: String): Observable<ChatHistoryModel> {
        return apiManager.userService.getChatHistory(chatRef)
    }

    fun sendMessage(
        senderId: String,
        receiverId: String,
        messageText: String,
        chatRef: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.sendMessage(senderId, receiverId, messageText, chatRef)
    }

    fun addToChat(userId: String, receiverId: String): Observable<AddToChatModel> {
        return apiManager.userService.addToChat(userId, receiverId)
    }

    fun getPlan(): Observable<PremiumPlanModel> {
        return apiManager.userService.getPlan()
    }

    fun getSavedCardsList(stripeToken: String): Observable<CardListModel> {
        return apiManager.userService.getSavedCards(stripeToken)
    }

    fun proceedToPay(
        userId: String,
        stripeToken: String,
        cardId: String,
        planId: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.proceedToPay(userId, stripeToken, cardId, planId)
    }

    fun getNotification(userId: String): Observable<NotificationModel> {
        return apiManager.userService.getNotifications(userId)
    }

    fun getUserInfo(userId: String): Observable<SignUpModel> {
        return apiManager.userService.getUserInfo(userId)
    }

    fun addFeedBack(
        userId: String,
        userFeedback: String,
        userSuggestion: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.addFeedBack(userId, userFeedback, userSuggestion)
    }

    fun updateSleepHealthLog(
        userId: String,
        healthType: String,
        healthValue: String,
        sleep_at: String,
        wakeup_at: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.updateSleepHealthLog(
            userId,
            healthType,
            healthValue,
            sleep_at,
            wakeup_at
        )
    }

    fun updateUserSchedule(
        userId: String,
        type: String,
        days: String
    ): Observable<CustomSuccessResponse> {
        return apiManager.userService.updateUserSchedule(userId, type, days)
    }

    fun getUserSchedule(userId: String, type: String): Observable<GetUserScheduleModel> {
        return apiManager.userService.getUserSchedule(userId, type)
    }

    fun postFeeling(userId: String, feelingTitle: String): Observable<CustomSuccessResponse> {
        return apiManager.userService.postFeeling(userId, feelingTitle)
    }

}
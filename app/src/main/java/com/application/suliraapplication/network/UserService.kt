package com.application.suliraapplication.network

import com.application.suliraapplication.models.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * This interface describes the methods related to Users REST API
 */
interface UserService {

    /**
     * This function returns all users of the system
     * @return an observable instance of a list of [User]s
     */
    @GET("users")
    fun getUsers(): Observable<CustomResponse>

    @Multipart
    @PUT("user")
    fun updateUserWithFile(
        @Part image: MultipartBody.Part,
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part("location") location: RequestBody,
        @Part("occupation") occupation: RequestBody,
        @Part("organization") organization: RequestBody,
        @Part("interests") interests: RequestBody,
        @Part("skills") skills: RequestBody,
        @Part("needMentoring") needMentoring: RequestBody,
        @Part("availableToMentor") availableToMentor: RequestBody
    ): Observable<CustomResponse>

    /**
     * This function deletes current user
     * @return an observable instance of a [CustomResponse]
     */
    @DELETE("user")
    fun deleteUser(): Observable<CustomResponse>

    /*Get interest list*/
    @GET(BaseUrl.GET_INTEREST)
    fun getInterests(): Observable<InterestModel>

    /*Get home can help list items*/
    @GET(BaseUrl.GET_HOE_CAN_HELP)
    fun getHowCanHelp(): Observable<HowCanHelpModel>

    /*Get health condition if any list when user selected yes from the screen*/
    @GET(BaseUrl.GET_HEALTH_CONDITION)
    fun getHealthConditionItemsList(): Observable<HealthConditionModel>

    /*Register user.. many of the option can be empty but we need to send.
    if user skip the option from the screen in that case need to send empty params.
    but params is compulsory to send Whether its empty or not  */
    @POST(BaseUrl.SIGN_UP)
    @Multipart
    fun registerUser(
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("age") age: RequestBody,
        @Part("password") password: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("height") height: RequestBody,
        @Part("bloodGroup") bloodGroup: RequestBody,
        @Part("exercise") exercise: RequestBody,
        @Part("exerciseName") exerciseName: RequestBody,
        @Part("notification") notification: RequestBody,
        @Part("deviceType") deviceType: RequestBody,
        @Part("deviceToken") deviceToken: RequestBody,
        @Part("interestArray") interestArray: RequestBody,
        @Part("healthArray") healthArray: RequestBody,
        @Part("purposeArray") purposeArray: RequestBody,
        @Part profilePic: MultipartBody.Part
    ): Observable<SignUpModel>

    @POST(BaseUrl.VERIFY_MOB_GET_OTP)
    @FormUrlEncoded
    fun getOtp(@Field("mobile") mobile: String): Observable<OTPModel>

    @POST(BaseUrl.UPDATE_HEALTH_DATA)
    @FormUrlEncoded
    fun updateHealthData(
        @Field("userId") userId: String,
        @Field("healthType") healthType: String,
        @Field("healthValue") healthValue: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.LOGOUT)
    @FormUrlEncoded
    fun logout(
        @Field("userId") userId: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.OTP_FOR_FORGET_PASSWORD)
    @FormUrlEncoded
    fun otpForForgetPassword(@Field("mobile") mobile: String): Observable<OTPforForgetPasswordModel>

    @POST(BaseUrl.FORGET_PASSWORD)
    @FormUrlEncoded
    fun forgetPassword(
        @Field("mobile") mobile: String,
        @Field("otp") otp: String,
        @Field("password") password: String
    ): Observable<CustomSuccessResponse>


    @POST(BaseUrl.CHANGE_PASSWORD)
    @FormUrlEncoded
    fun changePassword(
        @Field("userId") userId: String,
        @Field("oldPassword") oldPassword: String,
        @Field("password") password: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.EDIT_USER)
    @Multipart
    fun editUser(
        @Part("userId") userId: RequestBody,
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("age") age: RequestBody,
        @Part profilePic: MultipartBody.Part
    ): Observable<SignUpModel>

    @POST(BaseUrl.LOGIN)
    @FormUrlEncoded
    fun login(
        @Field("phone") phone: String, @Field("email") email: String,
        @Field("FieldType") FieldType: String, @Field("Password") Password: String,
        @Field("deviceToken") deviceToken: String, @Field("deviceType") deviceType: String
    ): Observable<SignUpModel>

    @GET(BaseUrl.TERM_CONDITION)
    fun getPolicyData(): Observable<PolicyModel>

    @POST(BaseUrl.GET_HOME_DATA)
    @FormUrlEncoded
    fun getHomeData(@Field("userId") userId: String): Observable<GetHomeModel>

    @POST(BaseUrl.ADD_POST)
    @Multipart
    fun addPost(
        @Part("userId") userId: RequestBody,
        @Part("postTitle") postTitle: RequestBody,
        @Part("postDecription") postDecription: RequestBody,
        @Part("heartRate") heartRate: RequestBody,
        @Part("sugar") sugar: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("sleep") sleep: RequestBody,
        @Part("mood") mood: RequestBody,
        @Part postImage: MultipartBody.Part
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.GET_EXPLORE)
    @FormUrlEncoded
    fun getExplore(@Field("userId") userId: String): Observable<ExploreModel>

    @POST(BaseUrl.GET_NEWS_FEED)
    @FormUrlEncoded
    fun getNewsFeed(@Field("userId") userId: String): Observable<NewsFeedModel>

    @POST(BaseUrl.PREMIUM_MEMBER_USER_INFO)
    @FormUrlEncoded
    fun getPremiumMemberUserInfo(@Field("userId") userId: String): Observable<MemberProfileModel>

    @POST(BaseUrl.POST_DETAIL)
    @FormUrlEncoded
    fun getPostDetail(
        @Field("userId") userId: String,
        @Field("postId") postId: String
    ): Observable<PostDetailModel>

    @POST(BaseUrl.ADD_RATING)
    @FormUrlEncoded
    fun addRating(
        @Field("userId") userId: String,
        @Field("postId") postId: String,
        @Field("rate") rate: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.ADD_COMMENT)
    @FormUrlEncoded
    fun addComment(
        @Field("postId") postId: String,
        @Field("userId") userId: String,
        @Field("rateId") rateId: String,
        @Field("comment") comment: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.FOLLOW_REQUEST)
    @FormUrlEncoded
    fun followRequest(
        @Field("userId") userId: String,
        @Field("followTo") followTo: String,
        @Field("isFollow") isFollow: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.DOCTOR_DETAIL)
    @FormUrlEncoded
    fun getDoctorDetail(@Field("doctorId") doctorId: String): Observable<DoctorDetailModel>

    @POST(BaseUrl.GET_HEALTH_LOG_DATA)
    @FormUrlEncoded
    fun getHealthLogData(
        @Field("userId") userId: String,
        @Field("timePeriod") timePeriod: String,
        @Field("healthType") healthType: String
    ): Observable<HealthProgressDataModel>

    @POST(BaseUrl.GET_CHAT_LIST)
    @FormUrlEncoded
    fun getChatList(
        @Field("userId") userId: String,
        @Field("userType") userType: String
    ): Observable<ChatListModel>

    @POST(BaseUrl.GET_CHAT_HISTORY)
    @FormUrlEncoded
    fun getChatHistory(@Field("chatRef") chatRef: String): Observable<ChatHistoryModel>

    @POST(BaseUrl.SEND_MESSAGE)
    @FormUrlEncoded
    fun sendMessage(
        @Field("senderId") senderId: String,
        @Field("receiverId") receiverId: String,
        @Field("messageText") messageText: String,
        @Field("chatRef") chatRef: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.ADD_TO_CHAT)
    @FormUrlEncoded
    fun addToChat(
        @Field("userId") userId: String,
        @Field("receiverId") receiverId: String
    ): Observable<AddToChatModel>

    @GET(BaseUrl.PREMIUM_PLAN)
    fun getPlan(): Observable<PremiumPlanModel>

    @POST(BaseUrl.GET_SAVED_CARDS)
    @FormUrlEncoded
    fun getSavedCards(@Field("stripeToken") stripeToken: String): Observable<CardListModel>

    @POST(BaseUrl.PROCEED_TO_PAY)
    @FormUrlEncoded
    fun proceedToPay(
        @Field("userId") userId: String, @Field("stripeToken") stripeToken: String,
        @Field("cardId") cardId: String, @Field("planId") planId: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.GET_NOTIFICATIONS)
    @FormUrlEncoded
    fun getNotifications(@Field("userId") userId: String): Observable<NotificationModel>

    @POST(BaseUrl.GET_USER_INFO)
    @FormUrlEncoded
    fun getUserInfo(@Field("userId") userId: String): Observable<SignUpModel>

    @POST(BaseUrl.ADD_FEEDBACK)
    @FormUrlEncoded
    fun addFeedBack(
        @Field("userId") userId: String, @Field("userFeedback") userFeedback: String,
        @Field("userSuggestion") userSuggestion: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.UPDATE_HEALTH_DATA)
    @FormUrlEncoded
    fun updateSleepHealthLog(
        @Field("userId") userId: String,
        @Field("healthType") healthType: String,
        @Field("healthValue") healthValue: String,
        @Field("sleep_at") sleep_at: String,
        @Field("wakeup_at") wakeup_at: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.USER_NOTIFY_SCHEDULE)
    @FormUrlEncoded
    fun updateUserSchedule(
        @Field("userId") userId: String,
        @Field("type") type: String,
        @Field("days") days: String
    ): Observable<CustomSuccessResponse>

    @POST(BaseUrl.GET_USER_SCHEDULE)
    @FormUrlEncoded
    fun getUserSchedule(
        @Field("userId") userId: String,
        @Field("type") type: String
    ): Observable<GetUserScheduleModel>

    @POST(BaseUrl.POST_FEELING)
    @FormUrlEncoded
    fun postFeeling(
        @Field("userId") userId: String,
        @Field("feelingTitle") feelingTitle: String
    ): Observable<CustomSuccessResponse>
}

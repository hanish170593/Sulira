package com.application.suliraapplication.network


/**
 * Object to keep settings such as URL parts, regions, s3 bucket names.
 */
object BaseUrl {
    const val apiBaseUrl: String = "http://hourlylancer.com/devlop/sulira/api/v1/"

    const val SUCCESS = "success"
    const val ERROR = "error"

    const val GET_INTEREST = "getInterest"
    const val GET_HOE_CAN_HELP = "getPurpose"
    const val GET_HEALTH_CONDITION = "getHealthCondition"
    const val SIGN_UP = "signup"
    const val VERIFY_MOB_GET_OTP = "getOTP"
    const val UPDATE_HEALTH_DATA = "healthUpdate"
    const val LOGOUT = "logout"
    const val OTP_FOR_FORGET_PASSWORD = "otpToForget"
    const val FORGET_PASSWORD = "ForgetPassword"
    const val CHANGE_PASSWORD = "changePassword"
    const val EDIT_USER = "editUser"
    const val LOGIN = "login"
    const val TERM_CONDITION = "termsAndCondiation"
    const val GET_HOME_DATA = "getHome"
    const val ADD_POST = "addPost"
    const val GET_EXPLORE = "getExplore"
    const val GET_NEWS_FEED = "getNewsFeed"
    const val PREMIUM_MEMBER_USER_INFO = "getPremiumUserInfo"
    const val POST_DETAIL = "postDetail"
    const val ADD_RATING = "addRatting"
    const val ADD_COMMENT = "addComment"
    const val FOLLOW_REQUEST = "addFollow"
    const val DOCTOR_DETAIL = "getDoctorDetail"
    const val GET_HEALTH_LOG_DATA = "getHealthLog"
    const val GET_CHAT_LIST = "getChatList"
    const val GET_CHAT_HISTORY = "chatHistory"
    const val SEND_MESSAGE = "sendMessage"
    const val ADD_TO_CHAT = "addChat"
    const val PREMIUM_PLAN = "getPlan"
    const val GET_SAVED_CARDS = "getSavedCard"
    const val PROCEED_TO_PAY = "proceedToPay"
    const val GET_NOTIFICATIONS= "getNotification"
    const val GET_USER_INFO= "getUserInfo"
    const val ADD_FEEDBACK = "addFeedback"
    const val USER_NOTIFY_SCHEDULE = "setUserSchedules"
    const val GET_USER_SCHEDULE= "getUserSchedules"
    const val POST_FEELING = "postFeeling"
}

package com.whattowhere.data.remote

object ApiConstant {
    const val BASE_URL = "http://whattowhere.net.162-222-225-85.plesk-web9.webhostbox.net/api/"
    const val PACKAGE_USER = "user/"

    //<editor-fold desc="API Listing">
    const val API_LOGIN = "driver/login"
    const val API_SIGNUP = "${PACKAGE_USER}SignUpUser"
    const val API_SIGN_IN = "${PACKAGE_USER}SignInUser"
    const val API_GET_VERIFICATION_CODE_BY_EMAIL_FOR_SIGNUP =
        "${PACKAGE_USER}GetVerificationCodeByEmailForSignUp"
    const val API_FORGOT_PASSWORD = "${PACKAGE_USER}ForgotPassword"
    const val API_USER_DETAILS="user/GetUserDataByUserId"
    const val API_CHANGE_PASSWORD="user/ResetPassword"
    const val API_LOGOUT="user/SignOutUse"
    const val API_FOLLOWERS_LIST="follow/GetFollowerList"
    const val API_FOLLOWINGS_LIST="follow/GetFollowingList"

    //</editor-fold>

    const val DEVICE_TYPE_ANDROID = "android"

}
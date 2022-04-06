package com.myvagon.driver.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.swagger.client.models.UserModel
import javax.inject.Inject


open class UserPreference @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val context: Context, private val gson: Gson
) {


    interface SharedPrefKey {
        companion object {
            const val USER_PREF = "user_pref"
            const val REGISTRATION_REQUEST = "registration_request"
            const val FCM_TOKEN = "fcm_token"
            const val USER_DETAILS = "user_details"
            const val IN_PROCESS_SHIPMENT = "in_process_shipment"
            const val UPDATE_LOCATION_INTERVAL = "update_location_interval"
            const val IS_SHOW_INTRO_SCREEN = "is_need_to_show_intro"

        }
    }


    fun saveUserSession(key: String?, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun getUserSession(key: String): String? {
        return if (context != null) {
            val prefs = sharedPreferences
            prefs.getString(key, "")
        } else {
            ""
        }
    }

    fun getFcmToken(): String {
        return getUserSession(SharedPrefKey.FCM_TOKEN) ?: ""
    }

    fun getUserId(): String {
        val data = getUserSession(SharedPrefKey.USER_DETAILS)
        if (!data.isNullOrEmpty()) {
            val userModel = gson.fromJson(data, UserModel::class.java)
            userModel.UserId?.let {
                return it.toString()
            }
        }
        return ""
    }

    fun getProfileDetails(): UserModel? {
        val data = getUserSession(SharedPrefKey.USER_DETAILS)
        if (!data.isNullOrEmpty()) {
            val userModel = gson.fromJson(data, UserModel::class.java)
            return userModel
        }
        return null
    }

    fun getLoginType(): String {
        val data = getUserSession(SharedPrefKey.USER_DETAILS)
        val userModel = gson.fromJson(data, UserModel::class.java)
        return userModel.LoginType ?: ""
    }

    open fun clearUserSession() {
        val editor = sharedPreferences.edit()
        val fcmToken = getFcmToken()
        editor.clear()
        editor.apply()
        saveUserSession(SharedPrefKey.FCM_TOKEN, fcmToken)
    }

}
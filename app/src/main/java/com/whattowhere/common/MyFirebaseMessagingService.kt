package com.whattowhere.common

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.myvagon.driver.utils.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by Utsav.B on 14-12-2021.
 */
@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        const val TAG = "Notification Service"
    }

    @Inject
    lateinit var userPreference: UserPreference

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PrintLog.e(TAG, "token : $token")
        userPreference.saveUserSession(UserPreference.SharedPrefKey.FCM_TOKEN, token)
    }
}
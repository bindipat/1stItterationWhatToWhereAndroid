package com.myvagon.driver.utils

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat


object PermissionManagerUtils {

    private fun shouldAskPermission(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    }

     fun shouldAskPermission(context: Context, permission: String): Boolean {
        if (shouldAskPermission()) {
            val permissionResult = ActivityCompat.checkSelfPermission(context, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    fun checkPermission(
        context: Context,
        activity: Activity,
        permissionList: Array<String>,
        sessionManager: PermissionSessionManager,
        listener: PermissionAskListener
    ) {
        var isPermissionGrant = false
        for (permission in permissionList) {
            if (shouldAskPermission(context, permission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    )
                ) {
                    listener.onPermissionPreviouslyDenied()
                } else {
                    if (sessionManager.isFirstTimeAsking(permission)) {
                        sessionManager.firstTimeAsking(permission, false)
                        listener.onNeedPermission()
                    } else {
                        listener.onPermissionPreviouslyDeniedWithNeverAskAgain()
                    }
                }
                isPermissionGrant = false
                break
            }
            else {
                isPermissionGrant = true
//                listener.onPermissionGranted()
            }
        }

        if (isPermissionGrant){
            listener.onPermissionGranted()
        }
    }

    interface PermissionAskListener {
        fun onNeedPermission()
        fun onPermissionPreviouslyDenied()
        fun onPermissionPreviouslyDeniedWithNeverAskAgain()
        fun onPermissionGranted()
    }

    class PermissionSessionManager(context: Context) {
        var sharedPreferences: SharedPreferences
        var editor: SharedPreferences.Editor? = null
        private val MY_PREF = "my_preferences"

        init {
            sharedPreferences = context.getSharedPreferences(MY_PREF, MODE_PRIVATE)
        }

        fun firstTimeAsking(permission: String, isFirstTime: Boolean) {
            doEdit()
            editor?.putBoolean(permission, isFirstTime)
            doCommit()
        }

        fun isFirstTimeAsking(permission: String): Boolean {
            return sharedPreferences.getBoolean(permission, true)
        }

        private fun doEdit() {
            if (editor == null) {
                editor = sharedPreferences.edit()
            }
        }

        private fun doCommit() {
            if (editor != null) {
                editor?.commit()
                editor = null
            }
        }
    }

}

package com.whattowhere.ui.base

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.R
import com.whattowhere.extension.hideKeyboard
import com.whattowhere.ui.splash.SplashActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity(),
    CoroutineScope {
    abstract val layoutId: Int
    lateinit var activity: Activity
    abstract val bindingVariable: Int
    val RQ_CODE_CAMERA = 100
    val RQ_CODE_LOCATION = 101
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    abstract fun setUpObserver()
    abstract fun init()

    @Inject
    lateinit var userPreference: UserPreference

    @Inject
    lateinit var gson: Gson


    @Inject
    lateinit var mViewModel: V

    lateinit var binding: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        performDataBinding()
    }

    private fun performDataBinding() {
        activity = this
        binding = DataBindingUtil.setContentView(activity, layoutId)
        binding.setVariable(bindingVariable, mViewModel)
        binding.executePendingBindings()
        init()
        setUpObserver()

    }

    fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    open fun setStatusBarColor(color: Int = R.color.light_pink) {
        window.statusBarColor = ContextCompat.getColor(activity, color)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard()
    }


    open fun showNotification(title: String, message: String, data: Map<String, String>?) {
        var intent = Intent(activity, SplashActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent =
            PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // This code targets Android O and Above (Channels).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val DEFAULT_CHANNEL_ID = "12313213"
            val NOTIFICATION_CHANNEL_NAME: CharSequence = "notification_channel_school_app"
            val CHANNEL_DESCRIPTION = "channel_description"
            val notificationChannel = NotificationChannel(
                DEFAULT_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = CHANNEL_DESCRIPTION
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
            val notificationBuilder: Notification.Builder =
                Notification.Builder(this, DEFAULT_CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(message)
                    .setAutoCancel(true)
                    .setBadgeIconType(Notification.BADGE_ICON_LARGE)
                    .setNumber(10)
                    .setContentIntent(pendingIntent)
            notificationManager.notify(0, notificationBuilder.build())
        }


        // This Code targets Android N and lower.
        val notificationBuilder: Notification.Builder = Notification.Builder(this)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }
}
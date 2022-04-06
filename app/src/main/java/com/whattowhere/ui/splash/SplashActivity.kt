package com.whattowhere.ui.splash

import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.PrintLog
import com.whattowhere.databinding.ActivitySplashBinding
import com.whattowhere.extension.startNewActivityWithClear
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.home.HomeActivity
import com.whattowhere.ui.intro.IntroActivity
import com.whattowhere.ui.splashSelection.SplashSelection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.MessageDigest

@AndroidEntryPoint
class SplashActivity(
    override val layoutId: Int = R.layout.activity_splash,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    val TAG = SplashActivity::class.java.name

    override fun setUpObserver() {
        lifecycleScope.launchWhenCreated {
            delay(3000)
            launch(Dispatchers.Main) {
                if (userPreference.getUserSession(UserPreference.SharedPrefKey.IS_SHOW_INTRO_SCREEN)
                        .equals("1")
                ) {
                    gotoNextScreen()
                } else {
                    startNewActivityWithClear(IntroActivity::class.java)
                }
            }
        }

    }

    private fun gotoNextScreen() {
        if (userPreference.getUserId().isEmpty()){
            startNewActivityWithClear(SplashSelection::class.java)
        }else{
            startNewActivityWithClear(HomeActivity::class.java)
        }
    }

    override fun init() {
        val token = userPreference.getUserSession(UserPreference.SharedPrefKey.FCM_TOKEN)
        PrintLog.e(TAG, "token : $token")
        getKeyHash()
    }
    fun getKeyHash(){
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e(
                    TAG,
                    "Splash KeyHash:- " + Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Splash Activity Exception = " + e.message)
        }
    }
}
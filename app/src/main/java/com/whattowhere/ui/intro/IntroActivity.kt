package com.whattowhere.ui.intro

import android.graphics.drawable.Drawable
import android.os.Handler
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ActivityIntroBinding
import com.whattowhere.extension.gone
import com.whattowhere.extension.startNewActivityWithClear
import com.whattowhere.extension.visible
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.home.HomeActivity
import com.whattowhere.ui.splashSelection.SplashSelection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class IntroActivity(
    override val layoutId: Int = R.layout.activity_intro,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityIntroBinding, IntroViewModel>() {
    lateinit var drawableList: ArrayList<Drawable?>

    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 2000

    var currentPosition = 0
    private val handler: Handler? = null

    override fun init() {

        drawableList = arrayListOf(
            ContextCompat.getDrawable(activity, R.drawable.onbording_1),
            ContextCompat.getDrawable(activity, R.drawable.onbording_2),
            ContextCompat.getDrawable(activity, R.drawable.onbording_3),
            ContextCompat.getDrawable(activity, R.drawable.onbording_4)
        )

        var dashBoardPageAdapter = object : IntroAdapter(activity, drawableList) {

        }
        binding.vpIntro.adapter = dashBoardPageAdapter
        binding.dotsIndicator.setViewPager(binding.vpIntro)
        binding.tvSkip.setOnClickListener {
            gotoLoginScreen()
        }
        binding.vpIntro.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
                if (currentPosition == drawableList.size - 1) {
                    binding.tvSkip.gone()
                    lifecycleScope.launchWhenCreated {

                        delay(2000)
                        launch(Dispatchers.Main) {
                            gotoLoginScreen()
                        }
                    }
                } else {
                    binding.tvSkip.visible()
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
//        autoScrollDashBoard()
    }

    fun autoScrollDashBoard() {
        val handler = Handler()
        val runnable = Runnable {

            binding.vpIntro.setCurrentItem(currentPosition++, true)
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(runnable)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    override fun setUpObserver() {

    }

    fun gotoLoginScreen() {
        userPreference.saveUserSession(UserPreference.SharedPrefKey.IS_SHOW_INTRO_SCREEN, "1")
        if (userPreference.getUserId().isEmpty()) {
            startNewActivityWithClear(SplashSelection::class.java)
        } else {
            startNewActivityWithClear(HomeActivity::class.java)
        }

    }
}
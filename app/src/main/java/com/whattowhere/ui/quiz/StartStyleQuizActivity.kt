package com.whattowhere.ui.quiz

import android.os.Bundle
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ActivityStartStyleQuizBinding
import com.whattowhere.extension.startNewActivityWithClear
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartStyleQuizActivity(override val layoutId: Int=R.layout.activity_start_style_quiz, override val bindingVariable: Int=BR.viewmodel) :
    BaseActivity<ActivityStartStyleQuizBinding, StartStyleQuizViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setUpObserver() {

    }

    override fun init() {
        binding.tvStartProfile.setOnClickListener{
            startNewActivityWithClear(HomeActivity::class.java)
        }
        binding.tvSkip.setOnClickListener{
            startNewActivityWithClear(HomeActivity::class.java)
        }
    }
}
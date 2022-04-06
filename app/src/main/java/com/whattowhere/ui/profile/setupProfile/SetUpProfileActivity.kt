package com.whattowhere.ui.profile.setupProfile

import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ActivitySetUpProfileBinding
import com.whattowhere.extension.startNewActivity
import com.whattowhere.extension.startNewActivityWithClear
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.login.LoginActivity
import com.whattowhere.ui.profile.EditProfileActivity
import com.whattowhere.ui.quiz.StartStyleQuizActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetUpProfileActivity(
    override val layoutId: Int = R.layout.activity_set_up_profile,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivitySetUpProfileBinding, SetUpProfileViewModel>() {

    override fun setUpObserver() {

    }

    override fun init() {
        binding.tvEditProfile.setOnClickListener {
            startNewActivity(EditProfileActivity::class.java)
        }
        binding.tvSkip.setOnClickListener {

            startNewActivity(
                StartStyleQuizActivity::class.java
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startNewActivityWithClear(LoginActivity::class.java)
    }
}
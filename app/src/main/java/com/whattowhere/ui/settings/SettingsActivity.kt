package com.whattowhere.ui.settings


import androidx.core.content.ContextCompat
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.extension.gone
import com.whattowhere.extension.startNewActivity
import com.whattowhere.extension.startNewActivityWithClear
import com.whattowhere.extension.visible
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.profile.EditProfileActivity
import com.whattowhere.ui.splashSelection.SplashSelection
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsActivity(
    override val layoutId: Int = R.layout.fragment_settings,
    override val bindingVariable: Int = BR.viewModel
) : BaseActivity<com.whattowhere.databinding.FragmentSettingsBinding, SettingsViewModel>() {


    override fun init() {
        initList()
    }

    private fun initList() {
        binding.cvEditProfile.setOnClickListener{
            activity.startNewActivity(EditProfileActivity::class.java)
        }

        binding.tvLogOut.setOnClickListener{
            userPreference.clearUserSession()
            activity.startNewActivityWithClear(SplashSelection::class.java)
        }
        binding.cvSecurity.setOnClickListener {
            if (binding.ivSecurity.tag == "0") {
                binding.ivSecurity.tag = "1"
                binding.cvSecurity.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.grey_hint
                    )
                )
                binding.llSecuritySubMenu.visible()
                binding.ivSecurity.rotation = 90f
            } else {
                binding.ivSecurity.tag = "0"
                binding.cvSecurity.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
                binding.llSecuritySubMenu.gone()
                binding.ivSecurity.rotation = 0f
            }
        }
        binding.cvHelp.setOnClickListener {
            if (binding.ivHelp.tag == "0") {
                binding.ivHelp.tag = "1"
                binding.cvHelp.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.grey_hint
                    )
                )
                binding.llHelpSubMenu.visible()
                binding.ivHelp.rotation = 90f
            } else {
                binding.ivHelp.tag = "0"
                binding.cvHelp.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
                binding.llHelpSubMenu.gone()
                binding.ivHelp.rotation = 0f
            }
        }
        binding.cvAboutUs.setOnClickListener {
            if (binding.ivAboutUs.tag == "0") {
                binding.ivAboutUs.tag = "1"
                binding.cvAboutUs.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.grey_hint
                    )
                )
                binding.llAboutUs.visible()
                binding.ivAboutUs.rotation = 90f
            } else {
                binding.ivAboutUs.tag = "0"
                binding.cvAboutUs.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white
                    )
                )
                binding.llAboutUs.gone()
                binding.ivAboutUs.rotation = 0f
            }
        }
    }

    override fun setUpObserver() {

    }

}
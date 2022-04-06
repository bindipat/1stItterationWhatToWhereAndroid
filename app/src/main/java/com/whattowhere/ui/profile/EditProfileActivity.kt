package com.whattowhere.ui.profile

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.Constants
import com.whattowhere.databinding.ActivityEditProfileBinding
import com.whattowhere.extension.gone
import com.whattowhere.extension.setRectDrawable
import com.whattowhere.extension.visible
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.customView.CustomTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity(
    override val layoutId: Int = R.layout
        .activity_edit_profile, override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {

    override fun setUpObserver() {

    }

    override fun init() {
        binding.tvShe.setOnClickListener {
            resetPronouns(binding.tvShe)
        }
        binding.tvHe.setOnClickListener {
            resetPronouns(binding.tvHe)
        }
        binding.tvThey.setOnClickListener {
            resetPronouns(binding.tvThey)
        }

        val adapter =
            ArrayAdapter(
                applicationContext,
                R.layout.list_item,
                resources.getStringArray(R.array.genders)
            )
        (binding.acGenders as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.tabEditProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    binding.llEditProfile.visible()
                    binding.llChangePassword.gone()
                } else {
                    binding.llEditProfile.gone()
                    binding.llChangePassword.visible()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        val loginType = userPreference.getLoginType()
        when(Constants.LoginType.valueOfLoginType(loginType)){
            Constants.LoginType.Normal -> binding.tabEditProfile.visible()
            Constants.LoginType.FaceBook -> binding.tabEditProfile.gone()
            Constants.LoginType.Google -> binding.tabEditProfile.gone()
            null -> {


            }
        }
    }

    fun resetPronouns(tv: CustomTextView) {
        binding.tvShe.setRectDrawable(
            rectColor = ContextCompat.getColor(
                activity,
                R.color.transparent
            )
        )
        binding.tvShe.setTextColor(ContextCompat.getColor(activity, R.color.black))

        binding.tvHe.setRectDrawable(
            rectColor = ContextCompat.getColor(
                activity,
                R.color.transparent

            )
        )
        binding.tvHe.setTextColor(ContextCompat.getColor(activity, R.color.black))

        binding.tvThey.setRectDrawable(
            rectColor = ContextCompat.getColor(
                activity,
                R.color.transparent

            )
        )

        binding.tvThey.setTextColor(ContextCompat.getColor(activity, R.color.black))

        tv.setRectDrawable(0f, ContextCompat.getColor(activity, R.color.black))
        tv.setTextColor(ContextCompat.getColor(activity, R.color.white))
    }
}
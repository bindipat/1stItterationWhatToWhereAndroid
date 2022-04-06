package com.whattowhere.ui.fragments.myProfile

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.myvagon.driver.ui.base.BaseFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.dto.ProfileDetailsDTO
import com.whattowhere.databinding.FragmentMyProfileBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.fragments.inspiration.InspirationFragment
import com.whattowhere.ui.fragments.wardrobe.WardrobeFragment
import com.whattowhere.ui.profile.follow.FollowersActivity
import com.whattowhere.ui.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment(
    override val layoutId: Int = R.layout.fragment_my_profile,
    override val bindingVariable: Int = BR.viewModel
) : BaseFragment<FragmentMyProfileBinding, MyProfileViewModel>() {
    override fun setupObservable() {
        mViewModel.userDetailsObserver.observe(this) { it ->
            it?.getContentIfNotHandled()?.let {
                when (it.status) {
                    ApiResources.Status.SUCCESS -> {
                        activity.hideLoader()
                        it.data?.data?.let { it1 -> setDetails(it1) }
                    }
                    ApiResources.Status.ERROR -> {
                        activity.hideLoader()
                        activity.showMessageDialog(message = it.message!!)
                    }
                    ApiResources.Status.LOADING -> {
                        activity.showLoader(childFragmentManager)
                    }
                    ApiResources.Status.NO_INTERNET_CONNECTION -> {}
                    ApiResources.Status.UNKNOWN -> {}
                    ApiResources.Status.SHIMMER_EFFECT -> {}
                }
            }
        }
    }

    private fun setDetails(data: ProfileDetailsDTO.Data) {
        binding.tvUserName.text = data.userName
        binding.tvProfileDetails.text = data.description
        binding.tvFollowersCount.text = data.followerCount.toString()
        binding.tvFollowingCount.text = data.followingCount.toString()
        binding.ivProfile.loadImage(data.profileImage, context = activity)
        if (!data.isEmailVerified) {
            binding.ivProfileVerify.gone()
        }

    }

    override fun init() {
        binding.ivSettings.setOnClickListener {
            activity.startNewActivity(SettingsActivity::class.java)
        }
        binding.llFollowers.setOnClickListener{
            activity.startNewActivity(FollowersActivity::class.java)
        }
        binding.llFollowing.setOnClickListener{
            activity.startNewActivity( FollowersActivity::class.java)
        }
        mViewModel.getProfile()
        setCurrentFragment(WardrobeFragment())
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    setCurrentFragment(WardrobeFragment())
                } else if (tab.position == 1) {
                    binding.fl.visible()
                    setCurrentFragment(InspirationFragment())
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setCurrentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(binding.fl.id, fragment)
            commit()
        }
    }
}
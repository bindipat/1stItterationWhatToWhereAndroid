package com.whattowhere.ui.profile.follow

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ActivityFollowersBinding
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.customView.CustomTypefaceSpan
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersActivity(
    override val layoutId: Int = R.layout.activity_followers,
    override val bindingVariable: Int = BR.viewModel
) : BaseActivity<ActivityFollowersBinding, FollowersViewModel>() {

    override fun setUpObserver() {

    }

    override fun init() {
        val dmSansMedium: Typeface = ResourcesCompat.getFont(activity, R.font.dm_sans_bold)!!
        val dmSansRegular: Typeface = ResourcesCompat.getFont(activity, R.font.dm_sans_regular)!!
        var followersCount = "392 "
        var followingCount = "192 "
        val followTitle = SpannableStringBuilder(followersCount + getString(R.string.lbl_followers))
        val followingTitle =
            SpannableStringBuilder(followersCount + getString(R.string.lbl_following))
        followTitle.setSpan(
            CustomTypefaceSpan("", dmSansMedium),
            0,
            followersCount.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        followTitle.setSpan(
            CustomTypefaceSpan("", dmSansRegular),
            followersCount.length,
            getString(R.string.lbl_followers).length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )

        followingTitle.setSpan(
            CustomTypefaceSpan("", dmSansMedium),
            0,
            followingCount.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        followingTitle.setSpan(
            CustomTypefaceSpan("", dmSansRegular),
            followingCount.length,
            getString(R.string.lbl_following).length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )

        binding.tab.addTab(binding.tab.newTab().setText(followTitle))
        userPreference.getProfileDetails()?.let {
            binding.toolbar.setTitle(it.UserName!!)
        }
        binding.tab.addTab(binding.tab.newTab().setText(followingTitle))
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    setAdapter(true)
                } else if (tab.position == 1) {
                    setAdapter(false)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })
        setAdapter()
    }

    fun setAdapter(isFollower: Boolean = true) {

        val adapter = FollowAdapter(getFollowerList(isFollower))
        binding.rv.adapter = adapter
    }

    private fun getFollowerList(isFollower: Boolean = true): ArrayList<ViewType> {
        val list = arrayListOf<ViewType>()
        for (i in 0..4) {
            if (isFollower){
                list.add(ViewType(ViewType.TYPE_FOLLOW))
            }else{
                list.add(ViewType(ViewType.TYPE_FOLLOWING))
            }
        }
        return list
    }

}
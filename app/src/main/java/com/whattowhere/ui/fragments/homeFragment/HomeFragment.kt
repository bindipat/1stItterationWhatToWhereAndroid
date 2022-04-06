package com.whattowhere.ui.fragments.homeFragment


import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.myvagon.driver.ui.base.BaseFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.FragmentHomeBinding
import com.whattowhere.extension.visible
import com.whattowhere.ui.fragments.inspiration.InspirationFragment
import com.whattowhere.ui.fragments.reviewFregment.ReviewFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment(
    override val layoutId: Int = R.layout.fragment_home,
    override val bindingVariable: Int = BR.viewModel
) : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>() {
    override fun setupObservable() {

    }

    override fun init() {
        setCurrentFragment(ReviewFragment())
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == 0) {
                    setCurrentFragment(ReviewFragment())
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
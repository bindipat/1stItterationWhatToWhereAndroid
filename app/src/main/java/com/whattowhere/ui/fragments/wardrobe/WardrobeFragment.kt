package com.whattowhere.ui.fragments.wardrobe

import com.myvagon.driver.ui.base.BaseFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.FragmentWardrobeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WardrobeFragment(
    override val layoutId: Int = R.layout.fragment_wardrobe,
    override val bindingVariable: Int = BR.viewModel
) : BaseFragment<FragmentWardrobeBinding, WardrobeViewModel>() {
    override fun setupObservable() {

    }

    override fun init() {
        setAdapter()
    }

    fun setAdapter() {
        val list = getReviewList()
        val adapter =WardrobeAdapter(object : WardrobeAdapter.OnWardrobeListener{
            override fun onWardrobeClick(data: String, position: Int) {

            }

        })
        adapter.filterList(list)
        binding.rv.adapter = adapter
    }



    private fun getReviewList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (i in 0..17) {
            list.add("")
        }
        return list
    }
}
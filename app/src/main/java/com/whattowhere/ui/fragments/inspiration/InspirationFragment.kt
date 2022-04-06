package com.whattowhere.ui.fragments.inspiration

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myvagon.driver.ui.base.BaseFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.FragmentInspirationBinding
import com.whattowhere.extension.startNewActivity
import com.whattowhere.extension.startNewActivityWithClear
import com.whattowhere.ui.postDetails.PostDetailsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InspirationFragment(
    override val layoutId: Int = R.layout.fragment_inspiration,
    override val bindingVariable: Int = BR.viewModel
) : BaseFragment<FragmentInspirationBinding, InspirationViewModel>() {
    lateinit var adapter: InspirationAdapter
    override fun setupObservable() {

    }

    override fun init() {
        setInsPrinationAdapter()
    }

    private fun setInsPrinationAdapter() {
        adapter = InspirationAdapter(object : InspirationAdapter.OnInspirationClick {
            override fun onSharePost(data: String, position: Int) {
                activity.startNewActivity(PostDetailsActivity::class.java)
            }
        }, activity)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rv.layoutManager = manager
        adapter.filterList(getInspirationList())
        binding.rv.setHasFixedSize(true)
        binding.rv.adapter = adapter

    }

    private fun getInspirationList(): ArrayList<String> {
        val list = arrayListOf<String>()
        list.add("https://picsum.photos/100/100?random=2")
        list.add("https://picsum.photos/100/150?random=2")
        list.add("https://picsum.photos/100/260?random=2")
        list.add("https://picsum.photos/100/200?random=2")
        list.add("https://picsum.photos/100/100?random=2")
        list.add("https://picsum.photos/100/250?random=2")
        list.add("https://picsum.photos/100/200?random=2")
        list.add("https://picsum.photos/100/220?random=2")

/*        list.add("https://picsum.photos/250/300?random=1")
        list.add("https://picsum.photos/200/400?random=2")
        list.add("https://picsum.photos/240/600?random=3")

        list.add("https://picsum.photos/250/100?random=4")
        list.add("https://picsum.photos/200/150?random=6")
        list.add("https://picsum.photos/140/200?random=5")

        list.add("https://picsum.photos/250/300?random=1")
        list.add("https://picsum.photos/200/400?random=2")
        list.add("https://picsum.photos/240/600?random=3")

        list.add("https://picsum.photos/250/100?random=4")
        list.add("https://picsum.photos/200/150?random=6")
        list.add("https://picsum.photos/140/200?random=5")*/
        return list
    }
}
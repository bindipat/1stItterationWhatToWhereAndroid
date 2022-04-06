package com.whattowhere.ui.fragments.reviewFregment

import com.myvagon.driver.ui.base.BaseFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.FragmentReviewBinding
import com.whattowhere.extension.startNewActivity
import com.whattowhere.ui.comments.CommentsActivity
import com.whattowhere.ui.dialog.MoreOptionsDialog
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment(
    override val layoutId: Int = R.layout.fragment_review,
    override val bindingVariable: Int = BR.viewModel
) : BaseFragment<FragmentReviewBinding, ReviewViewModel>() {
    override fun setupObservable() {

    }

    override fun init() {
        setAdapter()
    }

    fun setAdapter() {
        val list = getReviewList()
        val adapter = ReviewAdapter(object : ReviewAdapter.OnReviewClick {
            override fun onSharePost(data: SharePostWithFriendModel, position: Int) {

            }

            override fun onMoreOptions(data: String, position: Int) {
                openMoreOptionsDialog(data, position)
            }

            override fun onViewCommentsClick(data: String, position: Int) {
                activity.startNewActivity(CommentsActivity::class.java)
            }

        }, activity)
        adapter.filterList(list)
        binding.rv.adapter = adapter
    }

    private fun openMoreOptionsDialog(data: String, position: Int) {
        val dialog = MoreOptionsDialog(object : MoreOptionsDialog.OnMoreOptionsDialogListener {
            override fun onReport() {

            }

            override fun onBlock() {

            }

            override fun onUnFollow() {

            }

            override fun onShare() {

            }

        })
        dialog.show(childFragmentManager, "")
    }

    private fun getReviewList(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (i in 0..20) {
            list.add("")
        }
        return list
    }
}
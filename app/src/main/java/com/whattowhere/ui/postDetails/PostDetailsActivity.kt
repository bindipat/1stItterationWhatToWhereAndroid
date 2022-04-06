package com.whattowhere.ui.postDetails

import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.data.remote.CommentsDTO
import com.whattowhere.databinding.ActivityPostDetailsBinding
import com.whattowhere.ui.adapter.CommentsAdapter
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsActivity(
    override val layoutId: Int = R.layout.activity_post_details,
    override val bindingVariable: Int = BR.viewModel
) : BaseActivity<ActivityPostDetailsBinding, PostDetailsViewModel>() {

    override fun setUpObserver() {

    }

    override fun init() {
        setCommentsAdapter()

    }

    private fun setCommentsAdapter() {

        val list = arrayListOf<CommentsDTO>()
        for (i in 0..10) {
            list.add(CommentsDTO(getString(R.string.dummy_comments), getCommentsList()))
        }
        val adapter = CommentsAdapter(object : CommentsAdapter.OnCommentReplay {
            override fun onSharePost(data: SharePostWithFriendModel, position: Int) {

            }

        })
        adapter.filterList(list)
        binding.rvComments.adapter = adapter
    }

    private fun getCommentsList(): ArrayList<String> {
        val comments = arrayListOf<String>()
        for (i in 0..4) {
            comments.add("Yeah Right.")
        }
        return comments
    }
}
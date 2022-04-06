package com.whattowhere.ui.comments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.data.remote.CommentsDTO
import com.whattowhere.ui.adapter.CommentsAdapter
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsActivity(override val layoutId: Int=R.layout.activity_comments, override val bindingVariable: Int=BR.viewModel) : BaseActivity<com.whattowhere.databinding.ActivityCommentsBinding,CommentViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
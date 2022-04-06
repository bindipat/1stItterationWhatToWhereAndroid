package com.whattowhere.ui.post.publishPost

import android.net.Uri
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.CommonKeys
import com.whattowhere.databinding.ActivityPublishPostBinding
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.post.searchWardobe.SearchWardrobeBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishPostActivity(
    override val layoutId: Int = R.layout.activity_publish_post,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityPublishPostBinding, PublishPostViewModel>() {
    override fun setUpObserver() {

    }

    override fun init() {
        intent?.extras?.let {
            val uri = it.getString(CommonKeys.Data)
            binding.ivPost.setImageURI(Uri.parse(uri))
        }
        binding.tvPublish.setOnClickListener{
            openWardobeBottomSheet()
        }

    }

    private fun openWardobeBottomSheet() {
        val pickupLocationDialog = SearchWardrobeBottomSheet()
        val ft = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        pickupLocationDialog.show(supportFragmentManager, "dialog")
    }
}
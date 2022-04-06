package com.whattowhere.ui.boardDetails

import android.os.Bundle
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ActivityBoardDetailsBinding
import com.whattowhere.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardDetailsActivity(
    override val layoutId: Int = R.layout.activity_board_details,
    override val bindingVariable: Int = BR.viewModel
) : BaseActivity<ActivityBoardDetailsBinding, BoardDetailsViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setUpObserver() {

    }

    override fun init() {

    }
}
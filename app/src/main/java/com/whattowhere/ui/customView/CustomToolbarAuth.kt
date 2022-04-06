package com.whattowhere.ui.customView

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.whattowhere.R
import com.whattowhere.common.PrintLog
import com.whattowhere.databinding.ViewToolbarAuthBinding

class CustomToolbarAuth @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    val binding: ViewToolbarAuthBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_toolbar_auth, this, true)
    private var onBackListener: (() -> Unit?)? = null
    private var menuOneClickListener: (() -> Unit?)? = null
    private var menuTwoClickListener: (() -> Unit?)? = null


    fun setToolbarBackListener(listener: () -> (Unit)) {
        this.onBackListener = listener
    }

    fun setMenuOneClickListener(listener: () -> Unit) {
            this.menuOneClickListener=listener
    }

    fun setMenuTwoClickListener(listener: () -> Unit) {
            this.menuTwoClickListener=listener
    }


    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MVToolBar)
        val title = a.getString(R.styleable.MVToolBar_title)


        val isTitleVisible = a.getBoolean(R.styleable.MVToolBar_isTitleVisible, false)

        val isBackArrowVisible = a.getBoolean(R.styleable.MVToolBar_isBackArrowVisible, false)
        PrintLog.e("title", "== $title")
        PrintLog.e("isTitleVisible", "== $isTitleVisible")

        if (isBackArrowVisible) {
            binding.ivBack.visibility = View.VISIBLE
        } else {
            binding.ivBack.visibility = View.GONE
        }

        binding.ivBack.setOnClickListener {
            onBackListener?.invoke()
            if (context is Activity) {
                context.onBackPressed()
            }
        }
    }


}
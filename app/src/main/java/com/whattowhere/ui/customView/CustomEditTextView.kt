package com.whattowhere.ui.customView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.whattowhere.R
import com.whattowhere.ui.customView.TypeFace.getTypeface

class CustomEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatEditText(context, attrs, defStyle) {
    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MVTextView)
        val textStyle = a.getInt(R.styleable.MVTextView_textStyle, 0)
        this.setTypeface(textStyle.getTypeface(context))
        a.recycle()
    }
}
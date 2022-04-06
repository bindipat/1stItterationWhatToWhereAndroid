package com.whattowhere.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputLayout
import com.whattowhere.R

class CustomTextInputLayoutBorder : TextInputLayout {

    constructor(context: Context) : super(
        ContextThemeWrapper(
            context,
            R.style.OutlinedBoxStyle
        )
    ) {
        setFontTypeFace(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(ContextThemeWrapper(context, R.style.OutlinedBoxStyle), attrs) {
        if (attrs != null) {
            if (attrs.getAttributeBooleanValue(R.styleable.MVTextView_textInputStyleCustom,false)) {

            }
        }
        setFontTypeFace(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        ContextThemeWrapper(context, R.style.OutlinedBoxStyle   ),
        attrs,
        defStyleAttr
    ) {
        setFontTypeFace(context)
    }

    private fun setFontTypeFace(context: Context) {
        val face = ResourcesCompat.getFont(context, R.font.dm_sans_regular)
        typeface = face
    }
}

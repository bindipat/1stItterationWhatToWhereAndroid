package com.whattowhere.ui.customView

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.whattowhere.R

object TypeFace {
    open fun Int.getTypeface(context: Context): Typeface? {
        when (this) {
            0 -> {
                return ResourcesCompat.getFont(context, R.font.dm_sans_regular)
            }
            1 -> {
                return ResourcesCompat.getFont(context, R.font.dm_sans_bold)
            }
            2 -> {
                return ResourcesCompat.getFont(context, R.font.dm_sans_medium)
            }
            3 -> {
                return ResourcesCompat.getFont(context, R.font.dalime)
            }
            4->{
                return ResourcesCompat.getFont(context, R.font.ooch_baby_regular)
            }


        }
        return ResourcesCompat.getFont(context, R.font.dm_sans_regular)
    }
}
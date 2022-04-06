package com.whattowhere.extension

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.whattowhere.R

@BindingAdapter("rectDrawable", "rectColor", requireAll = false)
fun View.setRectDrawable(radius: Float = 15F, rectColor: Int) {
    val drawable =
        GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(rectColor, rectColor)
        )
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.cornerRadius = radius
    this.background = drawable
}

@BindingAdapter("circularDrawable", requireAll = false)
fun View.setCircularDrawable(color: Int) {
    val drawable =
        GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(color, color))
    drawable.shape = GradientDrawable.OVAL
    this.background = drawable
}

fun Context.getTintDrawable(drawable: Drawable?, color: Int): Drawable? {
    if (drawable!=null) {
        val wrappedDrawable: Drawable = drawable.let { DrawableCompat.wrap(it) }
        DrawableCompat.setTint(wrappedDrawable, color)
        return drawable
    }
    return null


}

@BindingAdapter("setTextColor", requireAll = false)
fun TextView.setTextColor(color: ColorStateList) {
    this.setTextColor(color)
}

@BindingAdapter(
    "borderRectDrawable",
    "strokeWidth",
    "strokeColor",
    "startColor",
    "endColor",
    requireAll = false
)
fun View.setBorderRectDrawable(
    radius: Float=0.0f,
    strokeWidth: Int? = null,
    strokeColor: Int,
    startColor: Int? = null,
    endColor: Int? = null
) {
    val drawable =
        GradientDrawable(
            GradientDrawable.Orientation.RIGHT_LEFT,
            intArrayOf(startColor ?: Color.TRANSPARENT, endColor ?: Color.TRANSPARENT)
        )
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.setStroke(strokeWidth ?: 1, strokeColor ?: context.getColor(R.color.purple_200))
    drawable.cornerRadius = radius
    this.background = drawable
}
fun Context.getBitmapFromVectorDrawable(drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(this, drawableId)
    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}


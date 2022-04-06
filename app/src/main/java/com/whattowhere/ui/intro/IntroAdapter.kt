package com.whattowhere.ui.intro

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.viewpager.widget.PagerAdapter
import com.whattowhere.R


open class IntroAdapter(val context: Context,val images: ArrayList<Drawable?>) :
    PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.item_intro, collection, false) as ViewGroup
        val ivImage: AppCompatImageView = layout.findViewById(R.id.ivIntro)
        val introDrawable: Drawable? = images[position]
        ivImage.setImageDrawable(introDrawable)
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    fun onViewPagerClick(drawable: Drawable?, position: Int) {}
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


}

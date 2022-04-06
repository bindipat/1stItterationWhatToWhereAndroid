package com.whattowhere.ui.fragments.wardrobe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.R
import com.whattowhere.databinding.ItemWardrobeNewBinding

class WardrobeAdapter(
    val listener: WardrobeAdapter.OnWardrobeListener

) :
    RecyclerView.Adapter<WardrobeAdapter.ViewHolder>() {
    var selectedPosition = -1
    var wardrobeList: ArrayList<String> = arrayListOf()

    inner class ViewHolder(var binding: ItemWardrobeNewBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String, position: Int) {
            try {
                if (position == 0) {
                    binding.ivPic1.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.wardrobe_pic_1
                        )
                    )
                    binding.ivPic2.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.wardrobe_pic_2
                        )
                    )
                    binding.ivPic3.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.wardrobe_pic_3
                        )
                    )
                }
                binding.root.setOnClickListener {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterList(friendsList: ArrayList<String>) {
        this.wardrobeList = friendsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWardrobeNewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val data = wardrobeList[position]
        holder.onBind(data, position)
    }

    override fun getItemCount(): Int {
        return wardrobeList.size
    }

    interface OnWardrobeListener {
        fun onWardrobeClick(data: String, position: Int)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
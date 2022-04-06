package com.whattowhere.ui.fragments.inspiration

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.R
import com.whattowhere.databinding.ItemInspirationBinding
import com.whattowhere.extension.loadImage

class InspirationAdapter(
    val listener: InspirationAdapter.OnInspirationClick,
    val activity: Activity
) :
    RecyclerView.Adapter<InspirationAdapter.ViewHolder>() {
    var selectedPosition = -1
    var wardrobeList: ArrayList<String> = arrayListOf()

    inner class ViewHolder(var binding: ItemInspirationBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String, position: Int) {
            try {
             /*   try {
                    binding.iv.loadImage(data, context = context)
                } catch (e: Exception) {
                    e.printStackTrace()
                }*/

                when (position) {
                    2 -> {
                        binding.iv.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity,
                                R.drawable.dummy_pic_5
                            )
                        )
                    }
                    3 -> {
                        binding.iv.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity,
                                R.drawable.dummy_pic_4
                            )
                        )
                    }
                    1 -> {
                        binding.iv.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity,


                                R.drawable.dummy_pic_2
                            )
                        )
                    }
                    5 -> {
                        binding.iv.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity,
                                R.drawable.dummy_pic
                            )
                        )
                    }
                    7 -> {
                        binding.iv.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity,
                                R.drawable.dummy_pic_4
                            )
                        )
                    }
                    else -> {

                        binding.iv.loadImage(data, context = activity)
                    }
                }
                binding.root.setOnClickListener{
                    listener.onSharePost(data = data,position)
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
            ItemInspirationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    fun onSharePost(data: String, position: Int) {
        listener.onSharePost(data, position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val data = wardrobeList[position]
        holder.onBind(data, position)
    }

    override fun getItemCount(): Int {
        return wardrobeList.size
    }

    interface OnInspirationClick {
        fun onSharePost(data: String, position: Int)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
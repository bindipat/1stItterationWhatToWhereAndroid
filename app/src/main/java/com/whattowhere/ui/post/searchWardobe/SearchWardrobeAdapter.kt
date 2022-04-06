package com.whattowhere.ui.post.searchWardobe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ItemWardrobeBinding

class SearchWardrobeAdapter :
    RecyclerView.Adapter<SearchWardrobeAdapter.ViewHolder>() {
    var selectedPosition = -1
    var wardrobeList: ArrayList<SearchWardrobeModel> = arrayListOf()

    inner class ViewHolder(var binding: ItemWardrobeBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SearchWardrobeModel, position: Int) {
            binding.setVariable(BR.data, data)
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
            if (position == selectedPosition) {
                binding.cv.strokeColor = ContextCompat.getColor(context, R.color.color_primary)
                binding.cv.strokeWidth = 1
                binding.ivSelected.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_selected_circle_icon
                    )
                )
                binding.cv.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.light_pink
                    )
                )
            } else {
                binding.cv.strokeColor = ContextCompat.getColor(context, R.color.white)
                binding.cv.strokeWidth = 1
                binding.cv.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
                binding.ivSelected.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_create_board_icon
                    )
                )
            }
        }
    }

    fun filterList(filterdNames: ArrayList<SearchWardrobeModel>) {
        this.wardrobeList = filterdNames
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWardrobeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = wardrobeList[position]
        holder.onBind(data, position)
    }

    override fun getItemCount(): Int {
        return wardrobeList.size
    }
}
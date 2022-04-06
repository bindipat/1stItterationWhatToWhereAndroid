package com.whattowhere.ui.fragments.reviewFregment

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.databinding.ItemReviewBinding
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendModel

class ReviewAdapter(
    val listener: ReviewAdapter.OnReviewClick,
    val activity: Activity
) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    var selectedPosition = -1
    var wardrobeList: ArrayList<String> = arrayListOf()

    inner class ViewHolder(var binding: ItemReviewBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String, position: Int) {
            binding.ivMore.setOnClickListener {
                listener.onMoreOptions(data,position)
            }
            binding.tvCommentsCount.setOnClickListener{
                listener.onViewCommentsClick(data,position)
            }
        }
    }

    fun filterList(friendsList: ArrayList<String>) {
        this.wardrobeList = friendsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    fun onSharePost(data: SharePostWithFriendModel, position: Int) {
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

    interface OnReviewClick {
        fun onSharePost(data: SharePostWithFriendModel, position: Int)
        fun onMoreOptions(data: String,position: Int)
        fun onViewCommentsClick(data: String,position: Int)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
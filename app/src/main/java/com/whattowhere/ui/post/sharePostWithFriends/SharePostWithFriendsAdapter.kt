package com.whattowhere.ui.post.sharePostWithFriends

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.R
import com.whattowhere.databinding.ItemFriendShareBinding
import com.whattowhere.extension.loadImage

class SharePostWithFriendsAdapter(val listener: OnClickSharePost) :
    RecyclerView.Adapter<SharePostWithFriendsAdapter.ViewHolder>() {
    var selectedPosition = -1
    var wardrobeList: ArrayList<SharePostWithFriendModel> = arrayListOf()

    inner class ViewHolder(var binding: ItemFriendShareBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SharePostWithFriendModel, position: Int) {
            binding.ivUser.loadImage(data.image, context = context)
            binding.tvSend.setOnClickListener {
                if (!data.isSharePost) {
                    data.isSharePost = !data.isSharePost
                }
                onSharePost(data, position)
            }
            binding.tvUserName.text = data.userName
            binding.tvFullName.text = data.fullName
            if (!data.isSharePost) {
                binding.cv.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
                binding.tvSend.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.cv.strokeColor = ContextCompat.getColor(context, R.color.black)
                binding.cv.strokeWidth = 3
                binding.tvSend.setTextColor(ContextCompat.getColor(context, R.color.black))
                binding.tvSend.text = context.getString(R.string.lbl_sent)
                binding.cv.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.transparent
                    )
                )
            }
        }
    }

    fun filterList(friendsList: ArrayList<SharePostWithFriendModel>) {
        this.wardrobeList = friendsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFriendShareBinding.inflate(
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
        val data = wardrobeList[position]
        holder.onBind(data, position)
    }

    override fun getItemCount(): Int {
        return wardrobeList.size
    }

    interface OnClickSharePost {
        fun onSharePost(data: SharePostWithFriendModel, position: Int)
    }
}
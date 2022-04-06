package com.whattowhere.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.R
import com.whattowhere.data.remote.CommentsDTO
import com.whattowhere.databinding.ItemCommentBinding
import com.whattowhere.databinding.ItemSubCommentBinding
import com.whattowhere.extension.gone
import com.whattowhere.extension.visible
import com.whattowhere.ui.post.sharePostWithFriends.SharePostWithFriendModel

class CommentsAdapter(
    val listener: CommentsAdapter.OnCommentReplay,
) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    var selectedPosition = -1
    var wardrobeList: ArrayList<CommentsDTO> = arrayListOf()

    inner class ViewHolder(var binding: ItemCommentBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: CommentsDTO, position: Int) {
            binding.tvSubCommentCount.text = String.format(
                context.getString(
                    R.string.dynemic_view_more_replies
                ), data.listComment.size.toString()
            )
            for ((pos, data) in data.listComment.withIndex()) {
                val item =
                    ItemSubCommentBinding.inflate(
                        LayoutInflater.from(context),
                        binding.llSubComments,
                        false
                    )
                binding.llSubComments.addView(item.root)

            }
            binding.llSubCommentCount.setOnClickListener {
                if (binding.llSubCommentCount.tag == "0") {
                    binding.llSubCommentCount.tag = "1"
                    binding.llSubComments.visible()
                    binding.tvSubCommentCount.text = context.getString(R.string.lbl_hide_replies)
                } else {
                    binding.llSubCommentCount.tag = "0"
                    binding.llSubComments.gone()
                    binding.tvSubCommentCount.text = String.format(
                        context.getString(
                            R.string.dynemic_view_more_replies
                        ), data.listComment.size.toString()
                    )
                }
            }


        }

    }

    fun filterList(friendsList: ArrayList<CommentsDTO>) {
        this.wardrobeList = friendsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommentBinding.inflate(
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

    interface OnCommentReplay {
        fun onSharePost(data: SharePostWithFriendModel, position: Int)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
package com.whattowhere.ui.profile.follow

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.databinding.ItemFollowBinding
import com.whattowhere.extension.gone

class FollowAdapter(var list: ArrayList<ViewType>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        when (viewType) {
            ViewType.TYPE_FOLLOW->{
                return FollowViewHolder(
                    ItemFollowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ViewType.TYPE_FOLLOWING->{
                return FollowingViewHolder(
                    ItemFollowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else->{

                    return FollowViewHolder(
                        ItemFollowBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )

            }
        }
    }

    inner class FollowViewHolder(var binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun onBind(){
                binding.icMore.gone()
                binding.tvFollow.setText("Remove")
            }

        }
    inner class FollowingViewHolder(var binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                when(holder.itemViewType){
                    ViewType.TYPE_FOLLOWING->{
                        (holder as FollowingViewHolder).onBind()
                    }
                    ViewType.TYPE_FOLLOW->{
                        (holder as FollowViewHolder).onBind()
                    }

                }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    override fun getItemCount(): Int {
            return list.size
    }
}
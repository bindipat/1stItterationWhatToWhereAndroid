package com.whattowhere.ui.fragments.notification

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.whattowhere.R
import com.whattowhere.data.remote.NotificationViewTypePojo
import com.whattowhere.data.remote.dto.DateDTO
import com.whattowhere.data.remote.dto.NotificationDTO
import com.whattowhere.databinding.ItemDateBinding
import com.whattowhere.databinding.ItemNotificationFollowBinding
import com.whattowhere.databinding.ItemNotificationFollowRequestBinding
import com.whattowhere.databinding.ItemNotificationLikeBinding
import com.whattowhere.extension.convertToPx
import com.whattowhere.ui.customView.CustomTypefaceSpan


class NotificationAdapter(val list: ArrayList<NotificationViewTypePojo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            NotificationViewTypePojo.TYPE_DATE -> {
                return DateViewHolder(
                    ItemDateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), parent.context
                )

            }
            NotificationViewTypePojo.TYPE_FOLLOWING_REQ -> {
                return FollowingReqViewHolder(
                    ItemNotificationFollowRequestBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), parent.context
                )

            }
            NotificationViewTypePojo.TYPE_FOLLOW_REQ -> {
                return FollowReqViewHolder(
                    ItemNotificationFollowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), parent.context
                )

            }
            NotificationViewTypePojo.TYPE_LIKE_REQ -> {
                return LikeViewHolder(
                    ItemNotificationLikeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), parent.context
                )
            }
            else -> {
                return LikeViewHolder(
                    ItemNotificationLikeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), parent.context
                )
            }
        }
    }

    inner class FollowReqViewHolder(
        var binding: com.whattowhere.databinding.ItemNotificationFollowBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NotificationDTO, position: Int) {
            val message = getNotificationMessage(data, context)
            binding.tvNotificationMsg.text = message
        }
    }

    private fun getNotificationMessage(
        data: NotificationDTO,
        context: Context
    ): SpannableStringBuilder {

        val dmSansMedium: Typeface = ResourcesCompat.getFont(context, R.font.dm_sans_medium)!!
        val dmSansRegular: Typeface = ResourcesCompat.getFont(context, R.font.dm_sans_regular)!!
        val message = SpannableStringBuilder(data.userName + data.message + data.time)
        message.setSpan(
            CustomTypefaceSpan("", dmSansMedium),
            0,
            data.userName.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        message.setSpan(
            AbsoluteSizeSpan(context.convertToPx(19)),
            0,
            data.userName.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        message.setSpan(
            CustomTypefaceSpan("", dmSansRegular),
            data.userName.length,
            message.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )

        message.setSpan(
            AbsoluteSizeSpan(context.convertToPx(14)),
            data.userName.length,
            message.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )

        message.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.black_70
                )
            ),  data.userName.length,
            message.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return message
    }

    inner class FollowingReqViewHolder(
        var binding: com.whattowhere.databinding.ItemNotificationFollowRequestBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NotificationDTO, position: Int) {
            val message = getNotificationMessage(data, context)
            binding.tvNotificationMsg.text = message

        }
    }


    inner class LikeViewHolder(
        var binding: com.whattowhere.databinding.ItemNotificationLikeBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NotificationDTO, position: Int) {
            val message = getNotificationMessage(data, context)
            binding.tvNotificationMsg.text = message

        }
    }

    inner class DateViewHolder(
        var binding: com.whattowhere.databinding.ItemDateBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: DateDTO, position: Int) {


        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType){
            NotificationViewTypePojo.TYPE_FOLLOWING_REQ->{
                val data: NotificationDTO = list[position] as NotificationDTO
                (holder as FollowingReqViewHolder).onBind(data,position)
            }
            NotificationViewTypePojo.TYPE_FOLLOW_REQ->{
                val data: NotificationDTO = list[position] as NotificationDTO
                (holder as FollowReqViewHolder).onBind(data,position)
            }
            NotificationViewTypePojo.TYPE_LIKE_REQ->{
                val data: NotificationDTO = list[position] as NotificationDTO
                (holder as LikeViewHolder).onBind(data,position)
            }
            NotificationViewTypePojo.TYPE_DATE->{
                val data: DateDTO = list[position] as DateDTO
                (holder as DateViewHolder).onBind(data,position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
}
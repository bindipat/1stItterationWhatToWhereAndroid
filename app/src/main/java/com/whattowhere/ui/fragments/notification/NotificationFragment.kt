package com.whattowhere.ui.fragments.notification

import com.myvagon.driver.ui.base.BaseFragment
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.data.remote.NotificationViewTypePojo
import com.whattowhere.data.remote.dto.DateDTO
import com.whattowhere.data.remote.dto.NotificationDTO
import com.whattowhere.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment(
    override val layoutId: Int = R.layout.fragment_notification,
    override val bindingVariable: Int = BR.viewModel
) : BaseFragment<FragmentNotificationBinding, NotificationViewModel>() {
    override fun setupObservable() {

    }

    override fun init() {
        setNotificationAdapter()
    }

    fun setNotificationAdapter() {
        val notificationList = getNotificationList()
        val notificationAdapter = NotificationAdapter(list = notificationList)
        binding.rvNotification.adapter = notificationAdapter
    }

    private fun getNotificationList(): ArrayList<NotificationViewTypePojo> {
        val list = arrayListOf<NotificationViewTypePojo>()
        var notificationModel =
            NotificationDTO(getString(R.string.dummy_name), " Started Following you ", "2h")
        notificationModel.viewType = NotificationViewTypePojo.TYPE_FOLLOW_REQ
        list.add(notificationModel)
        list.add(notificationModel)
        list.add(notificationModel)
        val date=DateDTO("Yesterday")
        date.viewType=NotificationViewTypePojo.TYPE_DATE
        list.add(date)
        notificationModel =
            NotificationDTO(getString(R.string.dummy_name), " Started Following you ", "2h")
        notificationModel.viewType = NotificationViewTypePojo.TYPE_LIKE_REQ
        list.add(notificationModel)
        notificationModel =
            NotificationDTO(getString(R.string.dummy_name), " Started Following you ", "2h")
        notificationModel.viewType = NotificationViewTypePojo.TYPE_FOLLOWING_REQ
        list.add(notificationModel)
        notificationModel =
            NotificationDTO(getString(R.string.dummy_name), " Started Following you ", "2h")
        notificationModel.viewType = NotificationViewTypePojo.TYPE_LIKE_REQ
        list.add(notificationModel)

        return list

    }
}
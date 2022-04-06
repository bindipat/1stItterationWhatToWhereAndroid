package com.whattowhere.data.remote

 abstract class NotificationViewTypePojo(var viewType: Int) {


    companion object {
   const val TYPE_DATE=1
        const val TYPE_FOLLOW_REQ=2
        const val TYPE_FOLLOWING_REQ=3
        const val TYPE_LIKE_REQ=4
    }
}

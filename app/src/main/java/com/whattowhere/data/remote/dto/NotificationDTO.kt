package com.whattowhere.data.remote.dto

import com.whattowhere.data.remote.NotificationViewTypePojo

class NotificationDTO(val userName: String, val message: String, val time: String) :
    NotificationViewTypePojo(NotificationViewTypePojo.TYPE_DATE)
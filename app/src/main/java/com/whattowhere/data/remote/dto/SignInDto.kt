package com.whattowhere.data.remote.dto

import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO
import io.swagger.client.models.UserModel

data class SignInDto (
    val data:UserModel?=null
): BaseDTO()
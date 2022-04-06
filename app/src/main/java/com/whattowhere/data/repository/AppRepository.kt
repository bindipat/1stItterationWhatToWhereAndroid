package com.whattowhere.data.repository

import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.data.remote.dto.ProfileDetailsDTO
import com.whattowhere.data.remote.dto.SignInDto
import com.whattowhere.data.remote.dto.request.ForgotPasswordRequestModel
import com.whattowhere.data.remote.dto.request.SignInRequestModel
import io.swagger.client.models.NewUserModel

interface AppRepository {
    suspend fun login(request: SignInRequestModel): ApiResources<SignInDto>
    suspend fun signUp(request: NewUserModel): ApiResources<SignInDto>
    suspend fun getVerificationCodeByEmail(request: String):ApiResources<ForgotPasswordDTO>
    suspend fun forgotPassword(request: ForgotPasswordRequestModel):ApiResources<BaseDTO>
    suspend fun getUserDetails(userId:Int):ApiResources<ProfileDetailsDTO>
    suspend fun getFollowerList(userId:Int):ApiResources<BaseDTO>
    suspend fun getFollowingList(userId: Int):ApiResources<BaseDTO>
}
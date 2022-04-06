package com.whattowhere.data.repository

import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.AppApiService
import com.whattowhere.data.remote.BaseDataSource
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.data.remote.dto.ProfileDetailsDTO
import com.whattowhere.data.remote.dto.SignInDto
import com.whattowhere.data.remote.dto.request.ForgotPasswordRequestModel
import com.whattowhere.data.remote.dto.request.SignInRequestModel
import io.swagger.client.models.NewUserModel
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppApiService, private val baseDataSource: BaseDataSource
) : AppRepository {
    override suspend fun login(request: SignInRequestModel): ApiResources<SignInDto> {

        val response = api.doLogin(request)
        return baseDataSource.getResult { response }
    }

    override suspend fun signUp(request: NewUserModel): ApiResources<SignInDto> {
        val response = api.doSignup(request)
        return baseDataSource.getResult { response }
    }

    override suspend fun getVerificationCodeByEmail(email: String): ApiResources<ForgotPasswordDTO> {
        val response = api.getVerificationCodeByEmail(email)
        return baseDataSource.getResult { response }
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequestModel): ApiResources<BaseDTO> {
        val response = api.doForgotPassword(request)
        return baseDataSource.getResult { response }


    }

    override suspend fun getUserDetails(userId: Int): ApiResources<ProfileDetailsDTO> {
val response=api.getUserDetails(userId)
        return baseDataSource.getResult { response }
    }

    override suspend fun getFollowerList(userId: Int): ApiResources<BaseDTO> {
        val response=api.getFollowerList(userId)
        return baseDataSource.getResult { response }
    }

    override suspend fun getFollowingList(userId: Int): ApiResources<BaseDTO> {
        val response=api.getFollowingList(userId)
        return baseDataSource.getResult { response }
    }

}
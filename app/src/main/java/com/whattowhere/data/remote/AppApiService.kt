package com.whattowhere.data.remote

import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.data.remote.dto.ProfileDetailsDTO
import com.whattowhere.data.remote.dto.SignInDto
import com.whattowhere.data.remote.dto.request.ForgotPasswordRequestModel
import com.whattowhere.data.remote.dto.request.SignInRequestModel
import io.swagger.client.models.NewUserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppApiService {

    @POST(ApiConstant.API_SIGN_IN)
    suspend fun doLogin(@Body signInModel: SignInRequestModel): Response<SignInDto>


    @POST(ApiConstant.API_SIGNUP)
    suspend fun doSignup(@Body newUserModel: NewUserModel): Response<SignInDto>


    @GET(ApiConstant.API_GET_VERIFICATION_CODE_BY_EMAIL_FOR_SIGNUP)
    suspend fun getVerificationCodeByEmail(@Query("email") email: String): Response<ForgotPasswordDTO>

    @POST(ApiConstant.API_FORGOT_PASSWORD)
    suspend fun doForgotPassword(@Body reqForgotPasswordDTO: ForgotPasswordRequestModel): Response<BaseDTO>


    @GET(ApiConstant.API_FOLLOWERS_LIST)
    suspend fun getFollowerList(@Query("UserId") userId: Int): Response<BaseDTO>


    @GET(ApiConstant.API_USER_DETAILS)
    suspend fun getUserDetails(@Query("userId") userId: Int): Response<ProfileDetailsDTO>

    @GET(ApiConstant.API_FOLLOWINGS_LIST)
    suspend fun getFollowingList(@Query("userId") userId: Int): Response<BaseDTO>
}
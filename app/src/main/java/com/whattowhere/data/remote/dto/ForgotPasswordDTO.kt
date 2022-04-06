package com.whattowhere.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO

data class ForgotPasswordDTO(
    @SerializedName("data")
    val `data`: Data,
):BaseDTO() {
    data class Data(
        @SerializedName("email")
        val email: String,
        @SerializedName("emailcode")
        val emailcode: String,
        @SerializedName("LoginType")
        val loginType: String,
        @SerializedName("UserId")
        val userId: Int
    )
}
package com.whattowhere.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO

data class ProfileDetailsDTO(
    @SerializedName("data")
    val data: Data,
):BaseDTO() {
    data class Data(
        @SerializedName("DOB")
        val dOB: Any?,
        @SerializedName("Description")
        val description: String,
        @SerializedName("DeviceType")
        val deviceType: String,
        @SerializedName("Email")
        val email: String,
        @SerializedName("FirebaseToken")
        val firebaseToken: String,
        @SerializedName("FollowerCount")
        val followerCount: Int,
        @SerializedName("FollowingCount")
        val followingCount: Int,
        @SerializedName("Gender")
        val gender: String,
        @SerializedName("IsActive")
        val isActive: Boolean,
        @SerializedName("IsEmailVerified")
        val isEmailVerified: Boolean,
        @SerializedName("LoginFirst")
        val loginFirst: Boolean,
        @SerializedName("LoginType")
        val loginType: String,
        @SerializedName("Mobile")
        val mobile: String,
        @SerializedName("Password")
        val password: String,
        @SerializedName("ProfileImage")
        val profileImage: String,
        @SerializedName("Pronouns")
        val pronouns: String,
        @SerializedName("tbl_useranswer")
        val tblUseranswer: List<Any>,
        @SerializedName("UserId")
        val userId: Int,
        @SerializedName("UserName")
        val userName: String
    )
}
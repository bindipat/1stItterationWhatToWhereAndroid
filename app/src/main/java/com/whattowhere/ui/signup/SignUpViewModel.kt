package com.whattowhere.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whattowhere.R
import com.whattowhere.common.Constants
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.Event
import com.whattowhere.data.remote.dto.SignInDto
import com.whattowhere.ui.base.BaseViewModel
import io.swagger.client.models.NewUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : BaseViewModel() {
    var fullName = ""
    var emailAddress = ""
    var password = ""
    var confirmPassword = ""


    private val _signUpResponse = MutableLiveData<Event<ApiResources<SignInDto>>>()
    val signUpResponseObserver: LiveData<Event<ApiResources<SignInDto>>> = _signUpResponse

    fun doSignUp(loginDetails: Constants.LoginType) {
        var isEmailVerify = false
        when (loginDetails) {
            Constants.LoginType.FaceBook -> {
                password = context.getString(R.string.app_name)
                isEmailVerify = true
            }
            Constants.LoginType.Google -> {
                password = context.getString(R.string.app_name)
                isEmailVerify = true
            }
        }
        _signUpResponse.value = Event(ApiResources.loading())
        viewModelScope.launch(Dispatchers.IO) {
            val signupModel = NewUserModel(
                fullName, isEmailVerify, "normal", emailAddress,
                password, loginDetails.loginType, userPreference.getFcmToken(), "android"
            )
            val response = appRepository.signUp(signupModel)
            launch(Dispatchers.Main) {

                _signUpResponse.value = Event(response)
            }
        }


    }

}
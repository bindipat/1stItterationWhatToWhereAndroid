package com.whattowhere.ui.resetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.utsav.mvvm_clean_architecture.data.remote.dto.BaseDTO
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.Event
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.data.remote.dto.request.ForgotPasswordRequestModel
import com.whattowhere.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(): BaseViewModel() {
    var password: String = ""
    var confirmPassword: String = ""
    lateinit var mForgotPassword: ForgotPasswordDTO.Data

    private val _resetPasswordResponse = MutableLiveData<Event<ApiResources<BaseDTO>>>()
    val resetPasswordObserver: LiveData<Event<ApiResources<BaseDTO>>> =
        _resetPasswordResponse

    fun doForgotPassword() {


        _resetPasswordResponse.value = Event(ApiResources.loading())
        viewModelScope.launch(Dispatchers.IO) {
            val request = ForgotPasswordRequestModel(mForgotPassword.userId.toLong(), password)
            val response = appRepository.forgotPassword(request)
            launch(Dispatchers.Main) {

                _resetPasswordResponse.value = Event(response)
            }
        }


    }
}
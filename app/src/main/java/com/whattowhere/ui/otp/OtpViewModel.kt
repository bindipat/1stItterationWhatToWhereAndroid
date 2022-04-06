package com.whattowhere.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.Event
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

class OtpViewModel @Inject constructor() : BaseViewModel() {
   lateinit var mForgotPassword : ForgotPasswordDTO.Data

   private val _signUpResponse = MutableLiveData<Event<ApiResources<ForgotPasswordDTO>>>()
   val signUpResponseObserver: LiveData<Event<ApiResources<ForgotPasswordDTO>>> = _signUpResponse

   fun doForgotPassword(email:String) {


      _signUpResponse.value = Event(ApiResources.loading())
      viewModelScope.launch(Dispatchers.IO) {
         val requestObject= JSONObject()
         requestObject.put("email",email)
         val response = appRepository.getVerificationCodeByEmail(email)
         launch(Dispatchers.Main) {

            _signUpResponse.value = Event(response)
         }
      }


   }
}
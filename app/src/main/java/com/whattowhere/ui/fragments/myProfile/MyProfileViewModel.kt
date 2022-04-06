package com.whattowhere.ui.fragments.myProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.Event
import com.whattowhere.data.remote.dto.ProfileDetailsDTO
import com.whattowhere.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyProfileViewModel @Inject constructor() : BaseViewModel() {
    private val _userDetails = MutableLiveData<Event<ApiResources<ProfileDetailsDTO>>>()
    val userDetailsObserver: LiveData<Event<ApiResources<ProfileDetailsDTO>>> = _userDetails

    fun getProfile() {
        _userDetails.value = Event(ApiResources.loading())
        viewModelScope.launch(Dispatchers.IO) {
            var response = appRepository.getUserDetails(userPreference.getUserId().toInt())
            launch(Dispatchers.Main) {

                _userDetails.value = Event(response)
            }
        }

    }
}
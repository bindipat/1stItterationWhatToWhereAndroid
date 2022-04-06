package com.whattowhere.ui.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.data.remote.BaseDataSource
import com.whattowhere.data.remote.Event
import com.whattowhere.data.repository.AppRepository
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var appRepository: AppRepository

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var userPreference: UserPreference

    @Inject
    lateinit var baseDataSource: BaseDataSource

    private val _validationState = MutableLiveData<Event<String>>()
    val validationStateObserver: LiveData<Event<String>> = _validationState

    fun setValidationValue(message: String) {
        _validationState.value = Event(message)
    }


}
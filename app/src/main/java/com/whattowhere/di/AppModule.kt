package com.whattowhere.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.data.remote.AppApiService
import com.whattowhere.data.remote.BaseDataSource
import com.whattowhere.data.repository.AppRepository
import com.whattowhere.data.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideCoinRepository(api: AppApiService,baseDataSource: BaseDataSource): AppRepository {
        return AppRepositoryImpl(api,baseDataSource)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideUserPref(sharedPreferences: SharedPreferences, context: Context,gson:Gson): UserPreference {
        return UserPreference(sharedPreferences, context,gson)
    }


    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            UserPreference.SharedPrefKey.USER_PREF,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun getLoginModel(): JSONObject = JSONObject()


}
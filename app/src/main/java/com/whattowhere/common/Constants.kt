package com.whattowhere.common

import com.google.gson.GsonBuilder

object Constants {
    fun <T> getObjectAsJson(product: T): String? {
        return GsonBuilder().setPrettyPrinting().create().toJson(product)
    }
    const val BASE_URL = "https://api.coinpaprika.com/"

    const val PARAM_COIN_ID = "coinId"

    const val GOOGLE_MAP_ZOOM_LEVEL = 17f
    var USER_PREFERENCE = "myvagon_driver"
    val EMAIL_PATTERN =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    const val PASSWORD_LIMIT = 8
    const val MOBILE_NO_LIMIT = 10
    const val DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    object FaceBook{
     const val  FACEBOOK_PERMISSION_EMAIL = "email"
        const val  FACEBOOK_PERMISSION_PUBLIC_PROFILE =
            "public_profile"
        const val  FACEBOOK_EMAIL= "email"
        const val  FACEBOOK_NAME = "name"
        const val  FACEBOOK_ID = "id"
    }



    enum class LoginType(val loginType: String) {
        Normal("normal"), FaceBook("facebook"),Google("gmail");

        companion object {
            private val map: MutableMap<String, LoginType> =
                HashMap()

            fun valueOfLoginType(pageType: String): LoginType? {
                return map[pageType]
            }

            init {
                for (pageType in LoginType.values()) {
                    map[pageType.loginType] = pageType
                }
            }
        }
    }

    enum class UserType(val userType: String) {
        Normal("normal"), Member("Member");

        companion object {
            private val map: MutableMap<String, UserType> =
                HashMap()

            fun valueOfLoginType(pageType: String): UserType? {
                return map[pageType]
            }

            init {
                for (pageType in UserType.values()) {
                    map[pageType.userType] = pageType
                }
            }
        }
    }
}



object CommonKeys{
    const val Data="data"
}
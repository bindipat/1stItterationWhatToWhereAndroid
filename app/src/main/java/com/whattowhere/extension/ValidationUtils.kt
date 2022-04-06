package com.whattowhere.extension

import com.whattowhere.common.Constants
import com.whattowhere.common.Constants.EMAIL_PATTERN
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {

    val pattern = Pattern.compile(EMAIL_PATTERN)
    val matcher: Matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.isMobileNoValid(): Boolean {

    return this.length < Constants.MOBILE_NO_LIMIT
}

fun String.isPasswordValid(): Boolean {
    return this.length <Constants.PASSWORD_LIMIT
}

package com.whattowhere.extension


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.enabled() {
    isEnabled = true
}

fun View.disabled() {
    isEnabled = false
}

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
}

fun Context.getDeviceModel(): String {
    return Build.MODEL
}


fun Context.hideKeyboard() {
    this as Activity
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun <T> Activity.startNewActivity(
    className: Class<T>,
    finish: Boolean = false,
    bundle: Bundle? = null,
    action:String=""
) {
    val intent = Intent(this, className)
    bundle?.let {
        intent.putExtras(it)
    }
    if (action.isNotEmpty()){
        intent.action = action
    }
    startActivity(intent)
    if (finish) {
        finish()
    }
}


fun <T> Activity.startNewActivityWithClear(
    className: Class<T>,
    finish: Boolean = false,
    bundle: Bundle? = null,
    action:String=""
) {
    val intent = Intent(this, className)

    bundle?.let {
        intent.putExtras(it)
    }
    if (action.isNotEmpty()){
        intent.action = action
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
//    overridePendingTransition(R.anim.left_in, R.anim.right_out)

}



fun Context.showMsgDialog(
    msg: String,
    positiveText: String? = "OK",
    listener: DialogInterface.OnClickListener? = null,
    negativeText: String? = "Cancel",
    negativeListener: DialogInterface.OnClickListener? = null,
    title: String? = "",
    icon: Int? = null,
    isCancelable: Boolean = false
) {

    val builder = androidx.appcompat.app.AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(msg)
    builder.setCancelable(isCancelable)
    builder.setPositiveButton(positiveText) { dialog, which ->

        listener?.onClick(dialog, which)
    }
    if (negativeListener != null) {
        builder.setNegativeButton(negativeText) { dialog, which ->

            negativeListener.onClick(dialog, which)
        }
    }
    if (icon != null) {
        builder.setIcon(icon)
    }
    if (isCancelable) {
        builder.setOnDismissListener {

        }
    }
    builder.create().show()




}
@RequiresApi(Build.VERSION_CODES.O)
fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}










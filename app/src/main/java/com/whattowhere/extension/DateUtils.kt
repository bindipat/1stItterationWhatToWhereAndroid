package com.whattowhere.extension

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.whattowhere.common.PrintLog
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


fun String.getDateFormat(
    inputFormat: String = "yyyy-MM-dd hh:mm:ss",
    outputFormat: String = "yyyy-MM-dd"
): String {
    val inputSimpleDateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    val outputDateFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return outputDateFormat.format(inputSimpleDateFormat.parse(this)).toString()
}


fun String.getFormattedDate( inputFormat: String,isNeedToAddTime:Boolean=true): String {
    val format = SimpleDateFormat(inputFormat)
    PrintLog.e("date", this)
    val dateDay = format.parse(this)
    PrintLog.e("date day", dateDay.date.toString())
    val cal = Calendar.getInstance()
    cal.timeInMillis = dateDay.time


    val dayNumberSuffix: String = getDateDay(cal.get(Calendar.DAY_OF_MONTH))
    val day = cal[Calendar.DATE]

    val dateFormat: DateFormat = SimpleDateFormat(" d'$dayNumberSuffix' MMMM")
    val yearFormat = SimpleDateFormat("yy")
    return if (isNeedToAddTime) {
        dateFormat.format(cal.time) + "'" + yearFormat.format(cal.time)
    }else{
        dateFormat.format(cal.time)
    }

}


fun getDateDay(day: Int): String {

    return if (day in 11..13) {
        "th"
    } else when (day % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.isPreviousDate(): Boolean {
    val localDate = LocalDate.now()
    val dateCurrent = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    if (SimpleDateFormat("yyyy-MM-dd").parse(this).before(dateCurrent)) {
        return true
    }
    return false
}


public fun Context.getCurrentDate(): String {
    val c = Calendar.getInstance().time
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return  df.format(c)
}


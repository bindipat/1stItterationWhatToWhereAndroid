package com.whattowhere.extension

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


/**
 * Created by Utsav.B on 29-11-2021.
 */


/**
 * @author -Utsav.B
 * @since -29-11-2021-09:57 AM
 */
fun Context.getLoadedJsonFile(fileName:String): String {
    var json: String = ""
    return try {
        val charset: Charset = Charsets.UTF_8
        val `is`: InputStream = this.assets.open(fileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, charset)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return ""
    }

}

fun String.getFormattedPrice(): String {

    try {
        try {
            if (this.contains(",")) {
                this.replace(",", "")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val convertedPrice = this.toFloat()
        return String.format("%.2f", convertedPrice)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return this

}
fun  Context.convertToPx(dp: Int): Int {
    // Get the screen's density scale
    val scale: Float = resources.getDisplayMetrics().density
    // Convert the dps to pixels, based on density scale
    return (dp * scale + 0.5f).toInt()
}
package com.myvagon.driver.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException
import java.io.InputStream

fun Context.isNetworkConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}

fun Context.loadJSONFromAsset(name:String): String? {
    var json: String? = null
    json = try {
        val json: InputStream = this.assets.open(name)
        val size: Int = json.available()
        val buffer = ByteArray(size)
        json.read(buffer)
        json.close()
        String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}


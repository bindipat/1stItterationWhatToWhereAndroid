package com.myvagon.driver.extension

import android.content.Context
import com.whattowhere.common.PrintLog


private const val TAG = "MyVagon"
fun Context.logMsg(tag: String = "MyVagon", msg: String) {
      PrintLog.e(TAG + tag, "= $msg")
}
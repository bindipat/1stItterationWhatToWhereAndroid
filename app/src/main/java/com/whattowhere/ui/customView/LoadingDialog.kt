package com.whattowhere.ui.customView

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.whattowhere.R


object LoadingDialog {
    var dialog: Dialog? = null

    fun showLoadDialog(context: Context) {
        dialog?.let {
            if (it.isShowing) {
                try {
                    it.dismiss()
                } catch (e: Exception) {
                }
            }
        }
        dialog = Dialog(context)

        try {
            dialog?.apply {
                window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(R.layout.dialog_loading)
                show()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoadDialog() {
        try {
            dialog?.dismiss()
        }catch (e:java.lang.Exception){

        }
    }

}
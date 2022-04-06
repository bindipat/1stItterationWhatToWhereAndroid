package com.whattowhere.ui

import android.content.Context
import androidx.fragment.app.FragmentManager


object LoadingDialog {
    var dialog: LoadingDialogFragment? = null

    fun showLoadDialog(context: Context, fragmentManager: FragmentManager) {
        dialog = LoadingDialogFragment.display(fragmentManager)
    }

    fun hideLoadDialog() {
        try {
            dialog?.dismiss()
        } catch (e: java.lang.Exception) {

        }
    }

}
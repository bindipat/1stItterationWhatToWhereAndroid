package com.whattowhere.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.whattowhere.R


class LoadingDialogFragment :DialogFragment(){


companion object{

    fun display(fragmentManager: FragmentManager): LoadingDialogFragment? {
        val exampleDialog = LoadingDialogFragment()
        exampleDialog.show(fragmentManager, "")
        return exampleDialog
    }

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.dialog_loading, container, false)
        return view
    }


}
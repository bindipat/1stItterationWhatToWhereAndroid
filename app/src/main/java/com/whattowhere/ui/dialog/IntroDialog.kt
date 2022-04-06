package com.whattowhere.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.whattowhere.databinding.ItemIntroDialogBinding

class IntroDialog : DialogFragment() {
    var position: Int = 0
    lateinit var introDialogBinding: ItemIntroDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dialog = Dialog(requireContext(), android.R.style.Theme_NoTitleBar)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val introDialogBinding = ItemIntroDialogBinding.inflate(layoutInflater)
        introDialogBinding.tvGotIt.setOnClickListener {
            dismiss()
        }
        introDialogBinding.ivClose.setOnClickListener {
            dismiss()
        }
        dialog.setContentView(introDialogBinding.root)
        return dialog

    }
}
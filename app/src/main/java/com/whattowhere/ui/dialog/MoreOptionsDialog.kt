package com.whattowhere.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.whattowhere.R
import com.whattowhere.databinding.DialogMoreOptionsBinding
import com.whattowhere.databinding.ItemIntroDialogBinding

class MoreOptionsDialog(var listener: OnMoreOptionsDialogListener) : DialogFragment() {
    var position: Int = 0
    lateinit var introDialogBinding: ItemIntroDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dialog = Dialog(requireContext(), R.style.Theme_Dialog)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val moreOptionsBinding = DialogMoreOptionsBinding.inflate(layoutInflater)
        moreOptionsBinding.tvBlockUser.setOnClickListener {
            dismiss()
            listener.onBlock()
        }
        moreOptionsBinding.tvReport.setOnClickListener {
            dismiss()
            listener.onReport()
        }
        moreOptionsBinding.tvShare.setOnClickListener {
            dismiss()
            listener.onShare()
        }
        moreOptionsBinding.tvUnfollow.setOnClickListener {
            dismiss()
            listener.onUnFollow()
        }
        dialog.setContentView(moreOptionsBinding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog

    }
    interface OnMoreOptionsDialogListener {
        fun onReport()
        fun onBlock()
        fun onUnFollow()
        fun onShare()
    }
}


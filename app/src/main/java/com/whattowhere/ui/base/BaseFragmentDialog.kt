package com.myvagon.driver.ui.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel

abstract class BaseFragmentDialog<T : ViewDataBinding> :   DialogFragment() {
    abstract val layoutId: Int
    lateinit var activity: Activity
    abstract val bindingVariable: Int
    abstract val viewModel:ViewModel
    abstract fun setUpObserver()
    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDataBinding()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState)

    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(requireActivity(), layoutId)

        binding.setVariable(bindingVariable, viewModel)
        binding.executePendingBindings()
        activity=requireActivity()
        setUpObserver()

    }
}
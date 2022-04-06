package com.myvagon.driver.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.myvagon.driver.utils.UserPreference
import com.whattowhere.R
import java.time.format.DateTimeFormatter
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding, V : ViewModel> :
    BottomSheetDialogFragment() {
    @Inject
    lateinit var mViewModel: V
    lateinit var binding: T

    @Inject
    lateinit var gson: Gson
    lateinit var activity: Activity

    @Inject
    lateinit var userPreference: UserPreference

    abstract val layoutId: Int
    abstract val bindingVariable: Int
    abstract fun init()

    @RequiresApi(Build.VERSION_CODES.O)
    val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    abstract fun setupObservable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal!!).peekHeight =
                Resources.getSystem().displayMetrics.heightPixels
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDataBinding(inflater, container)

        return binding.root
    }

    fun startActivityWithFinish(target: Class<*>, bundle: Bundle?) {
        //Call new activity with finish current activity
        val intent = Intent(requireActivity(), target)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        binding.setVariable(bindingVariable, mViewModel)
        binding.executePendingBindings()
        activity = requireActivity()
        init()
        setupObservable()
    }

    private fun logOut() {

    }

    open fun checkIsSessionOut(code: Int): Boolean {
        if (code == 403) {
            logOut()
        }
        return code == 403
    }


}
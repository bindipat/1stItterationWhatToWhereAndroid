package com.myvagon.driver.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

import com.google.gson.Gson
import com.myvagon.driver.utils.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<T : ViewDataBinding, V : ViewModel> : Fragment(), CoroutineScope {
    @Inject
    lateinit var mViewModel: V
    lateinit var binding: T
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    @Inject
    lateinit var gson: Gson
    lateinit var activity: Activity

    @Inject
    lateinit var userPreference: UserPreference

    abstract val layoutId: Int
    abstract val bindingVariable: Int

    @RequiresApi(Build.VERSION_CODES.O)
    val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    abstract fun setupObservable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        performDataBinding(inflater, container!!)
        init()
        return binding.root
    }


  abstract fun init()
    fun startActivityWithFinish(target: Class<*>, bundle: Bundle?) {
        //Call new activity with finish current activity
        val intent = Intent(requireActivity(), target)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)
        requireActivity().finish()
    }

    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(bindingVariable, mViewModel)
        binding.executePendingBindings()
        activity = requireActivity()
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
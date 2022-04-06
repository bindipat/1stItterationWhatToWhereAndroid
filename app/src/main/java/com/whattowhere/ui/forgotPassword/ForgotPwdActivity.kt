package com.whattowhere.ui.forgotPassword

import android.os.Bundle
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.CommonKeys
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.databinding.ActivityForgotPwdBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPwdActivity(
    override val layoutId: Int = R.layout.activity_forgot_pwd,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityForgotPwdBinding, ForgotPwdViewModel>() {

    override fun setUpObserver() {
        mViewModel.signUpResponseObserver.observe(this) { it ->
            it?.getContentIfNotHandled()?.let {
                when (it.status) {
                    ApiResources.Status.SUCCESS -> {
                        hideLoader()
                        val bundle = Bundle()
                        showNotification(getString(R.string.app_name), "OTP - ${it.data!!.data.emailcode}", null)
                        bundle.putString(CommonKeys.Data, gson.toJson(it.data.data))
                        startNewActivity(OtpActivity::class.java, bundle = bundle)
                    }
                    ApiResources.Status.ERROR -> {
                        hideLoader()
                        showMessageDialog(message = it.message!!)
                    }
                    ApiResources.Status.LOADING -> {
                        showLoader(supportFragmentManager)
                    }
                    ApiResources.Status.NO_INTERNET_CONNECTION -> {}
                    ApiResources.Status.UNKNOWN -> {}
                    ApiResources.Status.SHIMMER_EFFECT -> {}
                }
            }
        }
    }

    override fun init() {

        setStatusBarColor()
        binding.tvSubmit.setOnClickListener {
            if (isValidDetails()) mViewModel.doForgotPassword()
        }
    }

    private fun isValidDetails(): Boolean {
        if (mViewModel.email.trim().isEmpty()) {
            binding.edtEmail.error = getString(R.string.please_enter_email_address)
        } else if (!mViewModel.email.trim().isEmailValid()) {
            binding.edtEmail.error = getString(R.string.please_enter_valid_email_address)
        } else {
            return true
        }
        return false
    }
}
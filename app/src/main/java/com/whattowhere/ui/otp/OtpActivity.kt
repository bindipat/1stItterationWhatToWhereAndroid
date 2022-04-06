package com.whattowhere.ui.otp

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.CommonKeys
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.databinding.ActivityOtpBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.resetPassword.ResetPasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity(
    override val layoutId: Int = R.layout.activity_otp,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityOtpBinding, OtpViewModel>() {

    override fun setUpObserver() {
        mViewModel.signUpResponseObserver.observe(this) { it ->
            it?.getContentIfNotHandled()?.let {
                when (it.status) {
                    ApiResources.Status.SUCCESS -> {
                        hideLoader()
                        val bundle = Bundle()
                        showAlertDialog(message =  "otp send to your email")
                        showNotification(
                            getString(R.string.app_name),
                            "OTP - ${it.data!!.data.emailcode}",
                            null
                        )
                        mViewModel.mForgotPassword = it.data.data
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
        val data = intent.extras
        binding.tvError.gone()
        data?.let {
            val userDetails =
                gson.fromJson(it.getString(CommonKeys.Data), ForgotPasswordDTO.Data::class.java)
            mViewModel.mForgotPassword = userDetails
        }
        binding.otpView.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                binding.tvError.gone()
            }

            override fun onOTPComplete(otp: String) {
                isValidOtp()
            }

        }
        binding.tvVerify.setOnClickListener {
            isValidOtp()

        }
        binding.tvResned.setOnClickListener {
            mViewModel.doForgotPassword(mViewModel.mForgotPassword.email)
        }

    }

    fun isValidOtp() {
        binding.tvError.gone()
        if (binding.otpView.otp?.equals(mViewModel.mForgotPassword.emailcode) == true) {
            val bundle = Bundle()
            bundle.putString(CommonKeys.Data, gson.toJson(mViewModel.mForgotPassword))
            startNewActivity(ResetPasswordActivity::class.java, bundle = bundle)
        } else {
            binding.otpView.showError()
            binding.tvError.text = getString(R.string.lbl_wrong_enter_otp)
            binding.tvError.visible()
        }
    }
}
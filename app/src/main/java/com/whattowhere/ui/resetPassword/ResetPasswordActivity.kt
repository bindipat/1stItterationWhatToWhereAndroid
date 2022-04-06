package com.whattowhere.ui.resetPassword

import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.CommonKeys
import com.whattowhere.data.remote.ApiResources
import com.whattowhere.data.remote.dto.ForgotPasswordDTO
import com.whattowhere.databinding.ActivityResetPasswordBinding
import com.whattowhere.extension.*
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordActivity(
    override val layoutId: Int = R.layout.activity_reset_password,
    override val bindingVariable: Int = BR.viewModel
) : BaseActivity<ActivityResetPasswordBinding, ResetPasswordViewModel>() {
    override fun setUpObserver() {

        mViewModel.resetPasswordObserver.observe(this) { it ->
            it?.getContentIfNotHandled()?.let {
                when (it.status) {
                    ApiResources.Status.SUCCESS -> {
                        hideLoader()
                        showAlertDialog(message = "Password reset successfully.", positiveClick = {
                            startNewActivityWithClear(LoginActivity::class.java)
                        })
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
        intent.extras?.let {
            val data = it.getString(CommonKeys.Data)
            mViewModel.mForgotPassword = gson.fromJson(data, ForgotPasswordDTO.Data::class.java)
        }
        binding.tvSubmit.setOnClickListener{
            if (isValidDetails()){
                mViewModel.doForgotPassword()
            }
        }
    }

    fun isValidDetails(): Boolean {
        var isValidDetails = true
        if (mViewModel.password.trim().isEmpty()) {
            binding.edtPassword.error = getString(R.string.enter_your_password)
            isValidDetails = false
        } else if (mViewModel.password.trim().length < 6) {
            binding.edtPassword.error = getString(R.string.lbl_must_be_6_char)
            isValidDetails = false
        }
        if (mViewModel.confirmPassword.trim().isEmpty()) {
            binding.edtConfirmPassword.error = getString(R.string.this_field_is_requried)
            isValidDetails = false
        } else if (!mViewModel.password.trim().equals(mViewModel.confirmPassword.trim())) {
            binding.edtConfirmPassword.error = getString(R.string.password_and_confirm_password_not_same)
            isValidDetails = false
        }
        return isValidDetails
    }

}
package com.whattowhere.extension

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.myvagon.driver.extension.dpToPx
import com.whattowhere.R
import com.whattowhere.databinding.DialogErrorBinding
import com.whattowhere.databinding.ViewSnackbarBinding
import com.whattowhere.ui.LoadingDialog


fun Context.showAlertDialog(
    title: String = resources.getString(R.string.app_name),
    message: String = resources.getString(R.string.ok),
    isCancelAble: Boolean = false,
    positiveBtnText: String = resources.getString(R.string.ok),
    negativeBtnText: String? = null,
    negativeClick: (() -> Unit)? = null,
    positiveClick: (() -> Unit)? = null,
    positiveButtonColor: Int = ContextCompat.getColor(this, R.color.color_primary)
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(isCancelAble)


    builder.setPositiveButton(positiveBtnText) { _, _ ->
        positiveClick?.invoke()
    }
    negativeBtnText?.let {
        builder.setNegativeButton(negativeBtnText) { _, _ ->
            negativeClick?.invoke()
        }
    }
    val alertDialog = builder.create()
    alertDialog.show()

    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setTextColor(positiveButtonColor)
    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        .setTextColor(ContextCompat.getColor(this, R.color.black))
}


fun Context.showInternetDialog(
    isCancelAble: Boolean = false,
    positiveClick: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(this.getString(R.string.app_name))
    builder.setMessage(this.getString(R.string.network_connection_error))
    builder.setPositiveButton(this.getString(R.string.ok)) { _, _ ->

        positiveClick?.invoke()
    }


    // builder.setPositiveButton(this.getString(R.string.ok),  positiveClick?.invoke())
    builder.setCancelable(isCancelAble)
    val alertDialog = builder.create()
    alertDialog.show()
}

fun Context.showMessageDialog(title: String = "", message: String = "") {
    val dialog = Dialog(this)
    val inflater = LayoutInflater.from(this)
    val binding: DialogErrorBinding =
        DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_error, null, false)


    dialog.apply {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(binding.root)
        if (title.isNotEmpty()) {
            binding.tvTitle.text = title
        }
        if (message.isNotEmpty()) {
            binding.tvMessage.text = message
        }
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        show()
    }

}

fun Context.checkImagePermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        applicationContext,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) + ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) != PackageManager.PERMISSION_GRANTED
}

fun Context.requestImagePermission(activity: Activity?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
    }
}

fun Context.showLoader(fragmentManager: FragmentManager) {
    LoadingDialog.showLoadDialog(this, fragmentManager)
}

fun Context.hideLoader() {
    LoadingDialog.hideLoadDialog()
}

fun Context.requestImageGalleryPermission(activity: Activity?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 200
        )
    }

}


fun String.removeSpace(): String {
    return this.replace(" ", " ")
}

fun Context.showSnackBar(id: ViewGroup, message: String = "", isError: Boolean = true) {

    val mLength: Long = 1500
    val h = Handler(Looper.getMainLooper())
    val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
    val animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

    val mParent = (this as Activity).window.decorView as ViewGroup
    val mToastHolder = FrameLayout(this)
    val inflater = LayoutInflater.from(this)
    val binding: ViewSnackbarBinding = ViewSnackbarBinding.inflate(inflater)
    binding.tvMessage.text = message
    binding.isError = isError
    val mLayoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        Gravity.TOP or Gravity.CENTER
    )
    mLayoutParams.setMargins(
        this.dpToPx(10f),
        this.dpToPx(30f),
        this.dpToPx(10f),
        this.dpToPx(30f),
    )
    mToastHolder.layoutParams = mLayoutParams

    val isShown = mToastHolder.isShown
    if (isShown) {
        mToastHolder.startAnimation(animFadeOut)
        mToastHolder.removeAllViews()
        mParent.removeView(mToastHolder)
    } else {
        mToastHolder.bringToFront()

        mParent.addView(mToastHolder)
        mToastHolder.startAnimation(animFadeIn)
    }

    mToastHolder.addView(binding.root)
    h.postDelayed({
        if (mToastHolder.isShown) {
            mToastHolder.startAnimation(animFadeOut)
        }
        mParent.removeView(mToastHolder)
    }, mLength)
    mParent.visibility = View.VISIBLE
    mToastHolder.visibility = View.VISIBLE
    mToastHolder.bringToFront()
    mParent.bringToFront()
    id.bringChildToFront(mParent)

}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}



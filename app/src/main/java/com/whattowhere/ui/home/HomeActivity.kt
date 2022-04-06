package com.whattowhere.ui.home

import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myvagon.driver.utils.PermissionManagerUtils
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.databinding.ActivityHomeBinding
import com.whattowhere.extension.gone
import com.whattowhere.extension.showMsgDialog
import com.whattowhere.extension.startNewActivity
import com.whattowhere.extension.visible
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.fragments.homeFragment.HomeFragment
import com.whattowhere.ui.fragments.myProfile.MyProfileFragment
import com.whattowhere.ui.fragments.notification.NotificationFragment
import com.whattowhere.ui.post.createPost.CreatePostActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity(
    override val layoutId: Int = R.layout.activity_home,
    override val bindingVariable: Int = BR.viewmodel
) : BaseActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun setUpObserver() {

    }


    override fun init() {
        setupBottomNavigation()
        setCurrentFragment(HomeFragment())
        setKeyBoardListener()
    }

    private fun setKeyBoardListener() {
        val keyboardLayoutList = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val heightDiff: Int = binding.root.height -
                        binding.root.height
                if (activity != null) {
                    val contentViewTop =
                        activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
                    val broadcastManager = LocalBroadcastManager.getInstance(activity)
                    val r = Rect()
                    binding.root.getWindowVisibleDisplayFrame(r)
                    val screenHeight: Int = binding.root.rootView.height

                    // r.bottom is the position above soft keypad or device button.
                    // if keypad is shown, the r.bottom is smaller than that before.
                    val keypadHeight: Int = screenHeight - r.bottom
                    if (keypadHeight > screenHeight * 0.15) {
                        binding.bottomNavigation.gone()
                    } else {
                        binding.bottomNavigation.visible()
                    }
                } else {
                }
            }

        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(keyboardLayoutList)
    }

    private fun setupBottomNavigation() {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> {
                        setCurrentFragment(HomeFragment())
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_camera -> {
                        checkPermissionAndOpenImageSelection()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_notification -> {
                        setCurrentFragment(NotificationFragment())
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_profile -> {
                        setCurrentFragment(MyProfileFragment())
                        return@OnNavigationItemSelectedListener true
                    }

                }
                false
            }

        binding.bottomNavigation.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
    }

    private fun checkPermissionAndOpenImageSelection() {


        PermissionManagerUtils.checkPermission(
            activity,
            activity,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PermissionManagerUtils.PermissionSessionManager(activity),
            object : PermissionManagerUtils.PermissionAskListener {
                override fun onNeedPermission() {

                    ActivityCompat.requestPermissions(
                        activity, arrayOf(

                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), RQ_CODE_CAMERA
                    )
                }

                override fun onPermissionPreviouslyDenied() {
                    ActivityCompat.requestPermissions(
                        activity, arrayOf(

                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 1
                    )
                }

                override fun onPermissionPreviouslyDeniedWithNeverAskAgain() {
                    activity.showMsgDialog(
                        activity.resources.getString(R.string.camera_permissions_needed),
                        activity.resources.getString(R.string.open_setting),
                        { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

                            val uri: Uri =
                                Uri.fromParts("package", activity.packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                    )
                }

                override fun onPermissionGranted() {
                    startNewActivity(CreatePostActivity::class.java)
                }

            })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RQ_CODE_CAMERA) {
            checkPermissionAndOpenImageSelection()
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fl.id, fragment)
            commit()
        }
    }

}
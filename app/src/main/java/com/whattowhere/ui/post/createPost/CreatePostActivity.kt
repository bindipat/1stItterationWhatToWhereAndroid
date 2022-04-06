package com.whattowhere.ui.post.createPost

import android.content.ContentValues
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.whattowhere.BR
import com.whattowhere.R
import com.whattowhere.common.CommonKeys
import com.whattowhere.databinding.ActivityCreatePostBinding
import com.whattowhere.extension.rotationAnimation
import com.whattowhere.extension.startNewActivity
import com.whattowhere.ui.base.BaseActivity
import com.whattowhere.ui.dialog.IntroDialog
import com.whattowhere.ui.post.publishPost.PublishPostActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class CreatePostActivity(
    override val layoutId: Int = R.layout.activity_create_post,
    override val bindingVariable: Int = BR.viewmodel


) : BaseActivity<ActivityCreatePostBinding, CreatePostViewModel>() {
    lateinit var imageCapture: ImageCapture

    companion object {
        private const val TAG = "Create Post Activity"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private lateinit var cameraExecutor: ExecutorService
    private var lensFacing = CameraSelector.DEFAULT_FRONT_CAMERA
    override fun setUpObserver() {

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, ImageAnalysis.Analyzer { luma ->
                    })
                }

            val cameraSelector = lensFacing

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {

            }

        }, ContextCompat.getMainExecutor(this))

    }

    private fun flipCamera() {
        if (lensFacing === CameraSelector.DEFAULT_FRONT_CAMERA) lensFacing =
            CameraSelector.DEFAULT_BACK_CAMERA else if (lensFacing === CameraSelector.DEFAULT_BACK_CAMERA) lensFacing =
            CameraSelector.DEFAULT_FRONT_CAMERA
        startCamera()
    }

    override fun init() {
        setStatusBarColor(R.color.black)
        binding.ivTakePicture.setOnClickListener {
            takePhoto()
        }
        binding.ivSwitchCamera.setOnClickListener {
            binding.ivSwitchCamera.rotationAnimation(binding.ivSwitchCamera.rotation)
            flipCamera()

        }
        setupCameraPreview()
        openIntroDialog()

    }

    fun setupCameraPreview() {
        startCamera()
        setLastCaptureImage()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun openIntroDialog() {
        val dialog = IntroDialog()
        dialog.show(supportFragmentManager, "")
    }

    /**@author=utsav.b
     * @since-13-03-22 11:45 PM
     * @sample - using for set last capture image
     * */
    fun setLastCaptureImage() {
        lifecycleScope.launch(Dispatchers.Default) {
            val lastImage = getLastImage()
            launch(Dispatchers.Main) {
                lastImage?.let {
                    binding.ivGallery.setImageBitmap(it)
                }
            }
        }
    }

    fun takePhoto() {

        val imageCapture = imageCapture
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    setLastCaptureImage()
                    var bundle = Bundle()
                    bundle.putString(CommonKeys.Data, output.savedUri.toString())
                    startNewActivity(PublishPostActivity::class.java, bundle = bundle)
                }
            }
        )
    }


    fun getLastImage(): Bitmap? {

        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.MIME_TYPE
        )
        val cursor: Cursor? = contentResolver
            .query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
            )
        if (cursor?.moveToFirst() == true) {
            val imageLocation: String = cursor.getString(1)
            val imageFile = File(imageLocation)
            if (imageFile.exists()) {
                val bm = BitmapFactory.decodeFile(imageLocation)
                return bm
            }
        }
        return null
    }
}
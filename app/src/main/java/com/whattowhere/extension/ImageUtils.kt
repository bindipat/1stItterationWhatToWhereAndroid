package com.whattowhere.extension

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.whattowhere.R
import com.whattowhere.common.PrintLog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


fun ImageView.rotationAnimation(rotationDegree:Float){
    this.animate().rotation(rotationDegree+180f)
}
fun AppCompatImageView.loadImage(
    path: String,
    placeholder: Int = R.drawable.ic_launcher_background,
    context: Context,
    baseUrl: String = ""
) {

    val drawable = CircularProgressDrawable(context)
    drawable.strokeWidth = 10f
    drawable.centerRadius = 30f
    drawable.start()
    drawable.setColorFilter(
        context.resources.getColor(R.color.purple_200),
        PorterDuff.Mode.SRC_IN
    )
    Glide.with(context)
        .load(path)
        .error(ContextCompat.getDrawable(context,R.drawable.overview_1))
        .into(this)
}


fun Context.requestImages(uri: Uri): MultipartBody.Part {
    val file = ImageUtilNew.from(this, uri)

    PrintLog.d("IMAGE PATH", "file : ${file.path}")
    var selectedFile = saveBitmapToFile(file)

    // create RequestBody instance from file

    val requestFile: RequestBody =
        selectedFile!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    // MultipartBody.Part is used to send also the actual file name
    return MultipartBody.Part.createFormData("images[]", file.name, requestFile)
}

@Throws(IOException::class)
fun getFile(context: Context, uri: Uri): File {
    val destinationFilename =
        File(context.filesDir.path + File.separatorChar + queryName(context, uri))
    try {
        context.contentResolver.openInputStream(uri).use { ins ->
            ins?.let {
                createFileFromStream(
                    it,
                    destinationFilename
                )
            }
        }
    } catch (ex: Exception) {
        ex.message?.let {   PrintLog.e("Save File", it) }
        ex.printStackTrace()
    }
    return destinationFilename
}


fun createFileFromStream(ins: InputStream, destination: File?) {
    try {
        FileOutputStream(destination).use { os ->
            val buffer = ByteArray(4096)
            var length: Int
            while (ins.read(buffer).also { length = it } > 0) {
                os.write(buffer, 0, length)
            }
            os.flush()
        }
    } catch (ex: Exception) {
        ex.message?.let {   PrintLog.e("Save File", it) }
        ex.printStackTrace()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val returnCursor: Cursor = context.contentResolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}

fun saveBitmapToFile(file: File): File? {
    return try {

        // BitmapFactory options to downsize the image
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        o.inSampleSize = 6
        // factor of downsizing the image
        var inputStream = FileInputStream(file)
        //Bitmap selectedBitmap = null;
        BitmapFactory.decodeStream(inputStream, null, o)
        inputStream.close()

        // The new size we want to scale to
        val REQUIRED_SIZE = 75

        // Find the correct scale value. It should be the power of 2.
        var scale = 1
        while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
            o.outHeight / scale / 2 >= REQUIRED_SIZE
        ) {
            scale *= 2
        }
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        inputStream = FileInputStream(file)
        val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
        inputStream.close()

        // here i override the original image file
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        file
    } catch (e: java.lang.Exception) {
        null
    }

}



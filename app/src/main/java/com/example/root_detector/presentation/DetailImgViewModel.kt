package com.example.root_detector.presentation

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.root_detector.R
import com.example.root_detector.common.IMG_NAME_RESPONSE
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class DetailImgViewModel : ViewModel() {
    private val _imgDetail = MutableStateFlow<Bitmap?>(null)
    val imgDetail = _imgDetail

    private val _isSavingImg = MutableStateFlow(false)
    val isSavingImg = _isSavingImg

    private var bitmap: Bitmap? = null

    fun getImageFromCache(context: Context) {
        val cacheFile = File(context.cacheDir, IMG_NAME_RESPONSE)

        bitmap = BitmapFactory.decodeFile(cacheFile.absolutePath)

        //Rotate bitmap if is necessary
        _imgDetail.value = if (bitmap!!.height < bitmap!!.width) {
            val matrix = android.graphics.Matrix()
            matrix.postRotate(-90f)
            Bitmap.createBitmap(bitmap!!, 0, 0, bitmap!!.width, bitmap!!.height, matrix, true)
        } else {
            bitmap
        }
    }

    fun saveToGallery(context: Context) {
        if (_isSavingImg.value) {
            return
        }
        _isSavingImg.value = true

        val fileName = "root_image_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/RootDetector")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }
        }

        Toast.makeText(context, context.getString(R.string.image_saved), Toast.LENGTH_SHORT).show()

        _isSavingImg.value = false
    }
}
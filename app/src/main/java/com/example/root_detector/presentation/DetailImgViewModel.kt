package com.example.root_detector.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.example.root_detector.common.IMG_NAME_RESPONSE
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

class DetailImgViewModel : ViewModel() {
    private val _imgDetail = MutableStateFlow<Bitmap?>(null)
    val imgDetail = _imgDetail

    fun getImageFromCache(context: Context) {
        val cacheFile = File(context.cacheDir, IMG_NAME_RESPONSE)

        val bitmap = BitmapFactory.decodeFile(cacheFile.absolutePath)

        //Rotate bitmap if is necessary
        _imgDetail.value = if (bitmap.height < bitmap.width) {
            val matrix = android.graphics.Matrix()
            matrix.postRotate(-90f)
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else {
            bitmap
        }

    }
}
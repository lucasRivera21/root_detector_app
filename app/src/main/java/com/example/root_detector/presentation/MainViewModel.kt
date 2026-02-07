package com.example.root_detector.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val _imageSelected = MutableStateFlow<Bitmap?>(null)
    val imageSelected = _imageSelected

    fun onSelectImg(uri: Uri, context: Context) {
        val contentResolver = context.contentResolver

        try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }

            val widthBitmap = bitmap.width
            val heightBitmap = bitmap.height

            //rotate image
            _imageSelected.value = if(widthBitmap < heightBitmap) {
                val matrix = android.graphics.Matrix()
                matrix.postRotate(-90f)
                Bitmap.createBitmap(bitmap, 0, 0, widthBitmap, heightBitmap, matrix, true)
            } else {
                bitmap
            }
        } catch (e: Exception) {
            Log.e(TAG, "onSelectImg: ", e)
        }
    }
}
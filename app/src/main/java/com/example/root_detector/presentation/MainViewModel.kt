package com.example.root_detector.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.root_detector.common.Resource
import com.example.root_detector.domain.SendImageToApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    companion object {
        private val sendImageToApiUseCase = SendImageToApiUseCase()
    }

    private val _imageSelected = MutableStateFlow<Bitmap?>(null)
    val imageSelected = _imageSelected

    private val _isImageUpload = MutableStateFlow(false)
    val isImageUpload = _isImageUpload

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

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

            //rotate image if is necessary
            _imageSelected.value = if (widthBitmap < heightBitmap) {
                val matrix = android.graphics.Matrix()
                matrix.postRotate(-90f)
                Bitmap.createBitmap(bitmap, 0, 0, widthBitmap, heightBitmap, matrix, true)
            } else {
                bitmap
            }

            _isImageUpload.value = true
        } catch (e: Exception) {
            Log.e(TAG, "onSelectImg: ", e)
        }
    }

    fun onSendRequest() {
        if (!_isLoading.value) {
            _isLoading.value = true

            viewModelScope.launch(Dispatchers.IO) {

                when (val result = sendImageToApiUseCase(_imageSelected.value!!)) {
                    is Resource.Success -> {
                        Log.d(TAG, "onSendRequest: ${result.data}")
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "onSendRequest: ${result.message}")
                    }

                    is Resource.AruconDontFound -> {
                        Log.d(TAG, "onSendRequest: ${result.message}")
                    }

                    is Resource.ServerError -> {
                        Log.d(TAG, "onSendRequest: ${result.message}")
                    }
                }

                _isLoading.value = false
            }
        }
    }
}
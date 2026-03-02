package com.example.root_detector.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.root_detector.R
import com.example.root_detector.common.Resource
import com.example.root_detector.common.hasNetworkConnection
import com.example.root_detector.domain.SendImageToApiUseCase
import com.example.root_detector.domain.models.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _arucoDontFound = MutableStateFlow(false)
    val arucoDontFound = _arucoDontFound

    private val _rootValues = MutableStateFlow<ResponseModel?>(null)
    val rootValues = _rootValues

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

    fun onSendRequest(context: Context) {
        if (!_isLoading.value) {
            _isLoading.value = true
            _arucoDontFound.value = false
            _rootValues.value = null

            if (!hasNetworkConnection(context)) {
                Toast.makeText(
                    context,
                    context.getString(R.string.without_connection),
                    Toast.LENGTH_SHORT
                ).show()

                _isLoading.value = false
                return
            }
            viewModelScope.launch(Dispatchers.IO) {
                when (val result = sendImageToApiUseCase(_imageSelected.value!!)) {
                    is Resource.Success -> {
                        _rootValues.value = result.data
                    }

                    is Resource.AruconDontFound -> {
                        _arucoDontFound.value = true
                    }

                    else -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.server_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                _isLoading.value = false
            }
        }
    }
}
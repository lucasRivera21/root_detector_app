package com.example.root_detector.domain

import android.graphics.Bitmap
import com.example.root_detector.common.Resource
import com.example.root_detector.data.dto.toResponseModel
import com.example.root_detector.data.repository.Repository
import com.example.root_detector.domain.models.ResponseModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class SendImageToApiUseCase {
    companion object {
        private val repository = Repository()
    }

    suspend operator fun invoke(imageSelected: Bitmap): Resource<ResponseModel> {
        val stream = ByteArrayOutputStream()
        imageSelected.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)

        val imageRequestBody =
            MultipartBody.Part.createFormData("img_uploaded", "image.jpg", requestFile)

        return try {
            val response = repository.uploadImage(imageRequestBody)
            if (response.isSuccessful) {
                val responseModel = response.body()!!.toResponseModel()
                Resource.Success(responseModel)
            } else {
                if (response.code() == 400) {
                    Resource.AruconDontFound(response.raw().toString())
                } else {
                    Resource.Error(response.code())
                }
            }
        } catch (e: Exception) {
            Resource.ServerError(e.message.toString())
        }
    }
}
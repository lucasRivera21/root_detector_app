package com.example.root_detector.domain

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import com.example.root_detector.common.Resource
import com.example.root_detector.data.repository.Repository
import com.example.root_detector.domain.models.ResponseModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

private const val IMG_NAME_RESPONSE = "image_response.jpg"

class SendImageToApiUseCase {
    companion object {
        private val repository = Repository()
    }

    private var cacheFile: File? = null

    suspend operator fun invoke(imageSelected: Bitmap, context: Context): Resource<ResponseModel> {
        val stream = ByteArrayOutputStream()
        imageSelected.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)

        val imageRequestBody =
            MultipartBody.Part.createFormData("img_uploaded", "image.jpg", requestFile)

        return try {
            val response = repository.uploadImage(imageRequestBody)
            if (response.isSuccessful) {
                val responseModel = response.body()!!

                //Convert base64 to byteArray
                val byteArray = Base64.decode(responseModel.imgBase64, Base64.DEFAULT)

                //Save image in cache
                if (cacheFile == null) {
                    cacheFile = File(context.cacheDir, IMG_NAME_RESPONSE)
                }
                cacheFile?.writeBytes(byteArray)

                //Get Uri from cache
                val uri = Uri.fromFile(cacheFile)

                val rootValues = ResponseModel(
                    primary = responseModel.primary,
                    secondary = responseModel.secondary,
                    tertiary = responseModel.tertiary,
                    quaternary = responseModel.quaternary,
                    rootPercent = responseModel.rootPercent,
                    uriImage = uri
                )
                Resource.Success(rootValues)
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
package com.example.root_detector.data.repository

import com.example.root_detector.data.ApiHelper
import okhttp3.MultipartBody

class Repository {
    companion object {
        private val apiInterface = ApiHelper.apiInterface
    }

    suspend fun uploadImage(multipartBody: MultipartBody.Part) =
        apiInterface.predictModelApi(multipartBody)
}
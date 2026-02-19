package com.example.root_detector.data

import com.example.root_detector.data.dto.ResponseModelDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {

    @Multipart
    @POST("predict")
    suspend fun predictModelApi(@Part image: MultipartBody.Part): Response<ResponseModelDto>
}
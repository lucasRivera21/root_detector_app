package com.example.root_detector.data

import com.example.root_detector.data.dto.ResponseModelDto
import retrofit2.Response
import retrofit2.http.POST

interface ApiInterface {

    @POST("predict")
    suspend fun predictModelApi(): Response<ResponseModelDto>
}
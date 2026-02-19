package com.example.root_detector.data.dto

import com.example.root_detector.domain.models.ResponseModel
import com.google.gson.annotations.SerializedName

data class ResponseModelDto(
    val primary: Int,
    val secondary: Int,
    val tertiary: Int,
    val quaternary: Int,
    @SerializedName("root_percent")
    val rootPercent: Float,
    @SerializedName("img_base64")
    val imgBase64: String
)

fun ResponseModelDto.toResponseModel() = ResponseModel(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    quaternary = quaternary,
    rootPercent = rootPercent,
    imgBase64 = imgBase64
)
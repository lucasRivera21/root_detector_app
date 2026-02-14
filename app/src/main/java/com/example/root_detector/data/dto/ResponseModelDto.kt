package com.example.root_detector.data.dto

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

package com.example.root_detector.domain.models

data class ResponseModel(
    val primary: Int,
    val secondary: Int,
    val tertiary: Int,
    val quaternary: Int,
    val rootPercent: Float,
    val imgBase64: String
)
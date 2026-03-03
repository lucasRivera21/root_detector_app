package com.example.root_detector.domain.models

import android.graphics.Bitmap

data class ResponseModel(
    val primary: Int,
    val secondary: Int,
    val tertiary: Int,
    val quaternary: Int,
    val rootPercent: Float,
    val imgBitmap: Bitmap
)
package com.lucasdev.root_detector.domain.models

import android.net.Uri

data class ResponseModel(
    val primary: Int,
    val secondary: Int,
    val tertiary: Int,
    val quaternary: Int,
    val rootPercent: Float,
    val uriImage: Uri
)
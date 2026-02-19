package com.example.root_detector.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.root_detector.R

@Composable
fun MainScreen(paddingValues: PaddingValues, mainViewModel: MainViewModel) {
    val context = LocalContext.current

    val imageSelected by mainViewModel.imageSelected.collectAsState()
    val isImageUpload by mainViewModel.isImageUpload.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                mainViewModel.onSelectImg(uri, context)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.surface))
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_main_screen),
            color = colorResource(R.color.on_surface),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Upload Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(212.dp)
                .dashedBorder(
                    strokeWidth = 1.dp,
                    color = colorResource(R.color.primary),
                    cornerRadius = 12.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            if (imageSelected != null) {
                Image(
                    bitmap = imageSelected!!.asImageBitmap(),
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_upload),
                        contentDescription = "Upload",
                        tint = colorResource(R.color.on_surface),
                        modifier = Modifier.size(
                            36
                                .dp
                        )
                    )

                    Text(
                        text = stringResource(R.string.upload_image_main_screen),
                        color = colorResource(R.color.on_surface),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Button(
                        onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.primary),
                            contentColor = colorResource(R.color.on_primary)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.browse_button_main_screen),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Start Button
        Button(
            onClick = { mainViewModel.onSendRequest(context) },
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary),
                contentColor = colorResource(R.color.on_primary),
                disabledContentColor = colorResource(R.color.on_surface),
                disabledContainerColor = colorResource(R.color.on_surface).copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = isImageUpload
        ) {
            if (!isLoading) {
                Text(
                    text = stringResource(R.string.button_main_screen),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = colorResource(R.color.on_primary)
                )
            }
        }
    }
}

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadius: Dp) = this.drawBehind {
    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(cornerRadius.toPx())
    )
}


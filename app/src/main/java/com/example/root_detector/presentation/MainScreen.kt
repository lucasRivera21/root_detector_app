package com.example.root_detector.presentation

import android.content.Context
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.root_detector.R
import com.example.root_detector.domain.models.ResponseModel
import com.example.root_detector.navigation.Screens
import com.example.root_detector.presentation.components.ButtonComponent
import com.example.root_detector.presentation.components.ChartComponent
import com.example.root_detector.presentation.components.ClasifierBox

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current

    val imageSelected by mainViewModel.imageSelected.collectAsState()
    val isImageUpload by mainViewModel.isImageUpload.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()
    val arucoDontFound by mainViewModel.arucoDontFound.collectAsState()
    val rootValues by mainViewModel.rootValues.collectAsState()

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
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
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
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(212.dp)
                    .dashedBorder(
                        strokeWidth = 1.dp,
                        color = if (arucoDontFound) colorResource(R.color.error) else colorResource(
                            R.color.primary
                        ),
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
                            .clickable {
                                if (!isLoading) {
                                    pickMedia.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                            },
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
                            onClick = {
                                pickMedia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
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

            if (arucoDontFound) {
                Text(
                    stringResource(R.string.aruco_dont_found),
                    fontSize = 12.sp,
                    color = colorResource(R.color.error)
                )
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Start Button
        ButtonComponent(
            modifier = Modifier,
            textButton = stringResource(R.string.button_main_screen),
            isEnabled = isImageUpload,
            isLoading = isLoading
        ) {
            mainViewModel.onSendRequest(context)
        }

        Spacer(modifier = Modifier.size(32.dp))

        if (rootValues != null) {
            ResultsScreen(rootValues!!, navController, context)
        }
    }
}

@Composable
fun ResultsScreen(rootValues: ResponseModel, navController: NavHostController, context: Context) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = context.getString(R.string.results_tilte),
            fontSize = 20.sp,
            color = colorResource(R.color.on_surface),
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.size(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(48.dp)) {
                ClasifierBox(
                    context.getString(R.string.primary),
                    rootValues.primary,
                    colorResource(R.color.green),
                    modifier = Modifier.weight(1f)
                )
                ClasifierBox(
                    context.getString(R.string.secondary),
                    rootValues.secondary,
                    colorResource(R.color.blue),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(48.dp)) {
                ClasifierBox(
                    context.getString(R.string.tertiary),
                    rootValues.tertiary,
                    colorResource(R.color.orange),
                    modifier = Modifier.weight(1f)
                )
                ClasifierBox(
                    context.getString(R.string.quaternary),
                    rootValues.quaternary,
                    colorResource(R.color.red),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.size(40.dp))

        Box {
            ChartComponent(rootValues.rootPercent, context)
        }

        Spacer(Modifier.size(32.dp))

        Box {
            AsyncImage(
                model = rootValues.uriImage,
                contentDescription = "Root image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(212.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        navController.navigate(Screens.DetailImgScreen.route)
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResultScreen() {
    val navController = NavHostController(LocalContext.current)
    MainScreen(PaddingValues(), navController, MainViewModel())
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


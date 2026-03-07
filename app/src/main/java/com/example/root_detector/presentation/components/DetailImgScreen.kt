package com.example.root_detector.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.root_detector.R

@Composable
fun DetailImgScreen(paddingValues: PaddingValues, navController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(context.getColor(R.color.surface)))
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = colorResource(R.color.on_surface)
                )
            }

            Text(
                "Detalles",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.on_surface),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.root_img),
            contentDescription = "Root image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        ButtonComponent(modifier = Modifier, textButton = "Descargar", isLoading = false) {
            //TODO: Download image
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailImgScreen() {
    val navController = NavHostController(LocalContext.current)
    DetailImgScreen(PaddingValues(), navController)
}
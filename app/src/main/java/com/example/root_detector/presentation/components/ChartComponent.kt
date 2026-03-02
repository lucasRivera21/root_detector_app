package com.example.root_detector.presentation.components

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.root_detector.R
import kotlin.math.roundToInt

@Composable
fun ChartComponent(rootPercent: Float, context: Context) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .padding(7.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            //BackGround Circle
            drawCircle(
                color = Color(context.getColor(R.color.surface_container_highest)),
                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                radius = size.minDimension / 2,
                style = Stroke(40F)
            )

            drawArc(
                color = Color(context.getColor(R.color.purple)),
                -90f,
                rootPercent * 360f,
                useCenter = false,
                style = Stroke(14.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                stringResource(R.string.title_chart),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = colorResource(R.color.on_surface)
            )
            Text(
                "${(rootPercent * 100).roundToInt()}%",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = colorResource(R.color.on_surface)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChartComponent() {
    val context = LocalContext.current
    ChartComponent(0.8f, context)
}
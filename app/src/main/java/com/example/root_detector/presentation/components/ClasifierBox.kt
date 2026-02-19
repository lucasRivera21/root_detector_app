package com.example.root_detector.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.root_detector.R

@Composable
fun ClasifierBox(name: String, value: Int, bgColor: Color, modifier: Modifier = Modifier) {
    Box(modifier.clipToBounds()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Text(name, fontSize = 12.sp, color = Color.White)
            Text("$value", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopEnd)
                .offset(y = (-20).dp)
                .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopEnd)
                .offset(x = 20.dp)
                .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
        )
    }
}

@Preview
@Composable
fun PreviewClasifierBox() {
    ClasifierBox("Secundaria", 100, colorResource(R.color.green))
}
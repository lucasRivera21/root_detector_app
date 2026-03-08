package com.lucasdev.root_detector.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucasdev.root_detector.R

@Composable
fun ButtonComponent(
    modifier: Modifier,
    textButton: String,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(59.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.primary),
            contentColor = colorResource(R.color.on_primary),
            disabledContentColor = colorResource(R.color.on_surface),
            disabledContainerColor = colorResource(R.color.on_surface).copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = isEnabled
    ) {
        if (!isLoading) {
            Text(
                text = textButton,
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
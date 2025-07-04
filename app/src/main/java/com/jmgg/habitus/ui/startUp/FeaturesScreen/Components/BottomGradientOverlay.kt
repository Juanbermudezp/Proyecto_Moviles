package com.jmgg.habitus.ui.startUp.FeaturesScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



@Composable
fun BottomGradientOverlay(
    modifier: Modifier = Modifier,
    height: Dp = 180.dp,
    startColor: Color = Color.Transparent,
    endColor: Color = Color(0xFF131212),
    gradientHeight: Float = 400f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                Brush.verticalGradient(
                    colors = listOf(startColor, endColor),
                    startY = 0f,
                    endY = gradientHeight
                )
            )
    )
}


package com.jmgg.habitus.ui.startUp.FeaturesScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.R

@Composable
fun FourthFeatureScreen() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        val totalHeight = maxHeight
        var textHeight by remember { mutableStateOf(0) }


        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        textHeight = coordinates.size.height
                    }
                    .padding(horizontal = 16.dp, vertical = 13.dp)
            ) {
                Text(
                    text = "FOLLOW AND PERFORM PRE-MADE CUSTOM ROUTINES",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF6366F1),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp, top = 30.dp)
                )

                Text(
                    text = "Don't worry if you don't know how to start a routine, you can select from multiple routines with their respective personalized habits to help you improve.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            val remainingHeight = with(LocalDensity.current) {
                (totalHeight.toPx() - textHeight).toDp()
            }

            Image(
                painter = painterResource(id = R.drawable.feature4),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(remainingHeight),
                contentScale = ContentScale.Fit
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFF131212)),
                        startY = 0f,
                        endY = 400f
                    )
                )
        )
    }
}
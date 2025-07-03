package com.jmgg.habitus.ui.startUp.FeaturesScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.jmgg.habitus.R
import com.jmgg.habitus.ui.startUp.FeaturesScreens.Components.FeatureRow

@Composable
fun PremiumVersionOfferScreen(
    onContinue: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(horizontal = 21.dp, vertical = 70.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GET THE FULL EXPERIENCE\nOF HABITUS NOW",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6366F1),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 50.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FeatureRow(
                    iconRes = R.drawable.list_icon,
                    text = "Get access to personalized habits"
                )
                Spacer(Modifier.height(45.dp))
                FeatureRow(
                    iconRes = R.drawable.statistics_icon,
                    text = "Be able to see with statistics your progress"
                )
                Spacer(Modifier.height(45.dp))
                FeatureRow(
                    iconRes = R.drawable.infinite_icon,
                    text = "Unlimited habits and tasks"
                )
            }

            Spacer(Modifier.height(14.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1E293B))
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF10B981))) {
                                append("Lifetime ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFF8FAFC))) {
                                append("purchase")
                            }
                        },
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "$4.99",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF10B981)
                    )

                    Spacer(Modifier.height(15.dp))

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(50.dp)
                    ) {
                        Text(
                            "Get it now",
                            color = Color(0xFFF8FAFC),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

    }
}



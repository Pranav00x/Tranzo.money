package com.tranzo.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Splash Screen
 * App launch screen with gradient logo
 */
@Composable
fun SplashScreenClay() {
    var showContent by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (showContent) 1f else 0.5f,
        animationSpec = tween(1000, easing = EaseOutQuart),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(1000, easing = EaseInOutQuad),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.7f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo with gradient
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                TranzoColors.PrimaryBlue,
                                TranzoColors.PrimaryPurple
                            )
                        ),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .scale(scale),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "₮",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 64.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Brand text
            Text(
                "Tranzo",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 40.sp,
                modifier = Modifier.scale(scale)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Crypto Card + Smart Wallet",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
                modifier = Modifier.scale(scale)
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Loading indicator
            if (showContent) {
                LoadingIndicatorClay()
            }
        }
    }
}

@Composable
private fun LoadingIndicatorClay() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.PrimaryBlue,
                        TranzoColors.PrimaryPurple
                    )
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp))
    )
}

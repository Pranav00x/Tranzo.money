package com.tranzo.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreenModern() {
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(2000, easing = LinearEasing)),
        label = "rotation",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D9488),
                        Color(0xFF06B6D4),
                    ),
                ),
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.White, RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text("T", fontSize = 60.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D9488))
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("Tranzo", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Your crypto. Your control.", fontSize = 16.sp, color = Color(0xFFCEFCE8))
        Spacer(modifier = Modifier.height(48.dp))

        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
                .rotate(rotation),
            color = Color.White,
            strokeWidth = 4.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Loading...", fontSize = 14.sp, color = Color(0xFFCEFCE8))
    }
}

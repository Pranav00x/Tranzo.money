package com.tranzo.app.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreenModern() {
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
        ),
        label = "rotation",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Logo Box - Rounded square
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Black, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "T",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Tranzo",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your crypto. Your control.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .rotate(rotation),
                color = Color.Black,
                strokeWidth = 2.5.dp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Loading...",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
        )
    }
}

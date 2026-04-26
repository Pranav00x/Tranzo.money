package com.tranzo.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreenClay(onSplashComplete: () -> Unit = {}) {
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { animationStarted = true; delay(2000); onSplashComplete() }

    val scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0.3f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "logo scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f, animationSpec = tween(700), label = "alpha"
    )

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground), contentAlignment = Alignment.Center) {
        // Background decorative blobs
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.07f), 280f, Offset(size.width * 0.8f, size.height * 0.2f))
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.06f), 220f, Offset(size.width * 0.15f, size.height * 0.7f))
            drawCircle(TranzoColors.ClayGreen.copy(alpha = 0.04f), 160f, Offset(size.width * 0.6f, size.height * 0.85f))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp)) {
            // Clay logo — puffy indigo square with T
            Box(
                Modifier.size(100.dp).scale(scale)
                    .shadow(28.dp, RoundedCornerShape(32.dp), ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.4f), spotColor = TranzoColors.ClayBlue.copy(alpha = 0.3f))
                    .clip(RoundedCornerShape(32.dp))
                    .background(Brush.linearGradient(listOf(TranzoColors.ClayBlue, Color(0xFF7B5CE8))))
                    .drawBehind {
                        drawRoundRect(
                            Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.3f), Color.Transparent), startY = 0f, endY = size.height * 0.4f),
                            cornerRadius = CornerRadius(32.dp.toPx()), size = Size(size.width, size.height * 0.4f)
                        )
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text("T", style = MaterialTheme.typography.displayLarge, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 52.sp)
            }

            Text("Tranzo", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary, modifier = Modifier.alpha(alpha), letterSpacing = 1.sp)
            Text("Smart Crypto Wallet", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, modifier = Modifier.alpha(alpha))
        }
    }
}

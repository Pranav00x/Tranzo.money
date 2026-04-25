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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

/**
 * Claymorphism Splash Screen — Baby blue bg, bouncing clay logo.
 */
@Composable
fun SplashScreenClay(
    onSplashComplete: () -> Unit = {},
) {
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationStarted = true
        delay(2000)
        onSplashComplete()
    }

    val scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow,
        ),
        label = "logo scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(600),
        label = "content alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            // Clay logo
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .scale(scale)
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(32.dp),
                        ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.35f),
                        spotColor = TranzoColors.ClayBlue.copy(alpha = 0.25f),
                    )
                    .clip(RoundedCornerShape(32.dp))
                    .background(TranzoColors.ClayBlue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "T",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                )
            }

            Text(
                "Tranzo",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.alpha(alpha),
            )

            Text(
                "Smart Crypto Wallet",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
                modifier = Modifier.alpha(alpha),
            )
        }
    }
}

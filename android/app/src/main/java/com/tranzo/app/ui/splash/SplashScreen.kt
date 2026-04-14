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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

/**
 * Splash screen — Clean white background, minimalist Tranzo diamond logo + text.
 * Auto-navigates after 2 seconds.
 */
@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    isLoggedIn: Boolean = false,
) {
    // Animation states
    val logoScale = remember { Animatable(0.8f) }
    val logoAlpha = remember { Animatable(0f) }
    val wordmarkAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Logo fades in and scales up
        logoAlpha.animateTo(1f, animationSpec = tween(400, easing = FastOutSlowInEasing))
        logoScale.animateTo(1f, animationSpec = tween(500, easing = FastOutSlowInEasing))

        // Wordmark fades in
        delay(200)
        wordmarkAlpha.animateTo(1f, animationSpec = tween(400))

        // Bottom text fades in
        delay(200)
        textAlpha.animateTo(1f, animationSpec = tween(300))

        // Wait and navigate
        delay(1200)

        if (isLoggedIn) {
            onNavigateToHome()
        } else {
            onNavigateToOnboarding()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.CardSurface) // Clean white
            .systemBarsPadding(),
    ) {
        // Centered logo + wordmark (horizontal arrangement matching the images)
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Squircle container containing the black diamond
            Box(
                modifier = Modifier
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value)
                    .size(72.dp)
                    .shadow(
                        elevation = 8.dp, 
                        shape = RoundedCornerShape(20.dp),
                        spotColor = Color.Black.copy(alpha = 0.1f)
                    )
                    .background(TranzoColors.CardSurface, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Black Diamond (rotated square)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .rotate(45f)
                        .background(Color(0xFF1A1A1A), RoundedCornerShape(6.dp))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Wordmark
            Text(
                text = "Tranzo",
                modifier = Modifier.alpha(wordmarkAlpha.value),
                color = Color(0xFF1A1A1A),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-1).sp
            )
        }

        // Security text at bottom (No Emojis)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(textAlpha.value),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Self-Custody  -  Non-Custodial",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextSecondary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "100% Secure",
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.TextSecondary,
                )
            }
        }
    }
}

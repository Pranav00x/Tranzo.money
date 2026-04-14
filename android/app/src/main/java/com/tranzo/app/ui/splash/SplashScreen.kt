package com.tranzo.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.components.SecurityBadges
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

/**
 * Splash screen — full green background, centered Tranzo logo + wordmark,
 * security badges at bottom. Matches CheQ's splash exactly.
 *
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
    val badgesAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Logo fades in and scales up
        logoAlpha.animateTo(1f, animationSpec = tween(400, easing = FastOutSlowInEasing))
        logoScale.animateTo(1f, animationSpec = tween(500, easing = FastOutSlowInEasing))

        // Wordmark fades in
        delay(200)
        wordmarkAlpha.animateTo(1f, animationSpec = tween(400))

        // Badges fade in
        delay(200)
        badgesAlpha.animateTo(1f, animationSpec = tween(300))

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
            .background(TranzoColors.PrimaryGreen)
            .systemBarsPadding(),
    ) {
        // Centered logo + wordmark
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Logo icon (placeholder — use your SVG asset)
            Text(
                text = "T",
                modifier = Modifier
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value),
                color = TranzoColors.TextOnGreen,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Wordmark
            Text(
                text = "tranzo",
                modifier = Modifier.alpha(wordmarkAlpha.value),
                color = TranzoColors.TextOnGreen,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        // Security badges at bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(badgesAlpha.value),
        ) {
            // White-tinted badges for green background
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "🔒 Self-Custody  ·  🛡️ Non-Custodial",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextOnGreen.copy(alpha = 0.8f),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "── 100% Secure ──",
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.TextOnGreen.copy(alpha = 0.7f),
                )
            }
        }
    }
}

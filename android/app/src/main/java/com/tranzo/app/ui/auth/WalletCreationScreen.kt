package com.tranzo.app.ui.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

/**
 * Wallet creation loading screen.
 *
 * Full green background, "Setting up your Wallet", animated progress bar.
 * Matches CheQ's "Setting up your Credit Profile" screen.
 *
 * Behind the scenes (via Openfort):
 * 1. Create Openfort player (linked to email identity)
 * 2. Openfort generates embedded signer (smart account)
 * 3. Compute counterfactual smart account address
 * 4. Register account on backend
 * 5. Account deploys lazily on first transaction
 */
@Composable
fun WalletCreationScreen(
    onComplete: () -> Unit,
    onSkip: () -> Unit,
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing,
            ),
        )
        delay(500)
        onComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.PrimaryGreen)
            .systemBarsPadding(),
    ) {
        // Centered content
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Setting up your\nWallet",
                style = MaterialTheme.typography.headlineLarge,
                color = TranzoColors.TextOnGreen,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Progress bar — white track, light green fill
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(TranzoColors.TextOnGreen.copy(alpha = 0.3f)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = progress.value)
                        .clip(RoundedCornerShape(4.dp))
                        .background(TranzoColors.TextOnGreen),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Your smart wallet, powered by Openfort.",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextOnGreen.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
            )
        }

        // Skip at bottom
        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
        ) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextOnGreen.copy(alpha = 0.7f),
            )
        }
    }
}

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
 * Behind the scenes (via ZeroDev):
 * 1. Generate local signer key
 * 2. Create Kernel smart account (ERC-4337)
 * 3. Compute counterfactual smart account address
 * 4. Register account on backend
 * 5. Account deploys lazily on first transaction (gasless)
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
            .background(TranzoColors.PrimaryBlack)
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Setting up your\nSmart Wallet",
                style = MaterialTheme.typography.headlineLarge,
                color = TranzoColors.White,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(TranzoColors.White.copy(alpha = 0.2f)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = progress.value)
                        .clip(RoundedCornerShape(4.dp))
                        .background(TranzoColors.White),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Powered by ZeroDev — ERC-4337 smart account.",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
            )
        }

        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
        ) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.White.copy(alpha = 0.5f),
            )
        }
    }
}

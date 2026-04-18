package com.tranzo.app.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

/**
 * Bold wallet creation screen - Shows progress and achievement
 */
@Composable
fun WalletCreationScreenPro(
    viewModel: AuthViewModel = hiltViewModel(),
    onComplete: () -> Unit = {},
    onSkip: () -> Unit = {},
) {
    var step by remember { mutableStateOf(0) }
    val isCreating = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // Large header with animation
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = TranzoColors.PrimaryBlue.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Wallet,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryBlue,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    "Creating your wallet",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = TranzoColors.TextPrimary
                )

                Text(
                    "Setting up your smart account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Steps progress
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StepItem(
                    number = 1,
                    title = "Initializing",
                    description = "Setting up your blockchain account",
                    completed = step > 0,
                    current = step == 0,
                    isCreating = isCreating.value && step == 0
                )

                StepItem(
                    number = 2,
                    title = "Securing",
                    description = "Generating your wallet keys",
                    completed = step > 1,
                    current = step == 1,
                    isCreating = isCreating.value && step == 1
                )

                StepItem(
                    number = 3,
                    title = "Finalizing",
                    description = "Registering your account",
                    completed = step > 2,
                    current = step == 2,
                    isCreating = isCreating.value && step == 2
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TranzoButton(
                    text = if (step < 3) "Creating..." else "Continue",
                    onClick = {
                        if (step >= 3) {
                            onComplete()
                        } else {
                            if (!isCreating.value) {
                                isCreating.value = true
                            }
                        }
                    },
                    enabled = step >= 3,
                    isLoading = step < 3 && isCreating.value,
                    modifier = Modifier.fillMaxWidth()
                )

                if (step < 3) {
                    TextButton(
                        onClick = onSkip,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Skip for now",
                            color = TranzoColors.TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Simulate wallet creation steps
    LaunchedEffect(isCreating.value) {
        if (isCreating.value && step < 3) {
            delay(2000)
            step++
            if (step < 3) {
                // Continue to next step
            } else {
                isCreating.value = false
            }
        }
    }
}

@Composable
private fun StepItem(
    number: Int,
    title: String,
    description: String,
    completed: Boolean,
    current: Boolean,
    isCreating: Boolean,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isCreating) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = when {
            completed -> TranzoColors.Success.copy(alpha = 0.08f)
            current -> TranzoColors.PrimaryBlue.copy(alpha = 0.08f)
            else -> TranzoColors.SurfaceLight
        },
        border = BorderStroke(
            width = if (current) 2.dp else 1.dp,
            color = when {
                completed -> TranzoColors.Success
                current -> TranzoColors.PrimaryBlue
                else -> TranzoColors.TextTertiary.copy(alpha = 0.2f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Step number
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = when {
                            completed -> TranzoColors.Success
                            current -> TranzoColors.PrimaryBlue
                            else -> TranzoColors.TextTertiary.copy(alpha = 0.2f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                when {
                    completed -> {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Completed",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    isCreating -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer(rotationZ = rotation),
                            color = TranzoColors.PrimaryBlue,
                            strokeWidth = 2.dp
                        )
                    }
                    else -> {
                        Text(
                            "$number",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (current) Color.White else TranzoColors.TextTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Step description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary
                )
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary
                )
            }
        }
    }
}
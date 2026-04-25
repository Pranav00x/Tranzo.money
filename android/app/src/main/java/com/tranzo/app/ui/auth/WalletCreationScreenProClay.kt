package com.tranzo.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClaySuccessCheckmark
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.launch

/**
 * Claymorphism Wallet Creation Screen
 * Shows wallet deployment progress
 */
@Composable
fun WalletCreationScreenProClay(
    viewModel: AuthViewModel = hiltViewModel(),
    onWalletCreated: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
        coroutineScope.launch {
            viewModel.createWallet()
        }
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

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
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Header
            Text(
                "Creating Your Wallet",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                "Deploying your ZeroDev Kernel account",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Progress steps
            WalletCreationStep(
                stepNumber = 1,
                label = "Generating Keys",
                isCompleted = state.walletStage.ordinal > 0,
                isActive = state.walletStage.ordinal == 0
            )

            WalletCreationStep(
                stepNumber = 2,
                label = "Deploying Account",
                isCompleted = state.walletStage.ordinal > 1,
                isActive = state.walletStage.ordinal == 1
            )

            WalletCreationStep(
                stepNumber = 3,
                label = "Activating Validators",
                isCompleted = state.walletStage.ordinal > 2,
                isActive = state.walletStage.ordinal == 2
            )

            WalletCreationStep(
                stepNumber = 4,
                label = "Complete!",
                isCompleted = state.walletStage.ordinal > 3,
                isActive = state.walletStage.ordinal == 3
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Info box
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(
                    TranzoColors.Success.copy(alpha = 0.08f),
                    TranzoColors.PrimaryGreen.copy(alpha = 0.05f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Security,
                        contentDescription = "Secure",
                        tint = TranzoColors.Success,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        "Your wallet is secured by ZeroDev Kernel validators",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Continue button (appears when done)
            if (state.walletStage.ordinal > 3) {
                ClayButton(
                    text = "Start Using Tranzo",
                    onClick = onWalletCreated,
                    gradientStart = TranzoColors.PrimaryBlue,
                    gradientEnd = TranzoColors.PrimaryPurple,
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = TranzoColors.PrimaryBlue,
                    strokeWidth = 3.dp
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
private fun WalletCreationStep(
    stepNumber: Int,
    label: String,
    isCompleted: Boolean,
    isActive: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = when {
                    isCompleted -> TranzoColors.Success.copy(alpha = 0.08f)
                    isActive -> TranzoColors.PrimaryBlue.copy(alpha = 0.08f)
                    else -> TranzoColors.BackgroundLight
                }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Step indicator
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    brush = when {
                        isCompleted -> Brush.linearGradient(
                            colors = listOf(
                                TranzoColors.Success,
                                TranzoColors.PrimaryGreen
                            )
                        )
                        isActive -> Brush.linearGradient(
                            colors = listOf(
                                TranzoColors.PrimaryBlue,
                                TranzoColors.BlueLight
                            )
                        )
                        else -> Brush.linearGradient(
                            colors = listOf(
                                TranzoColors.DividerGray,
                                TranzoColors.SurfaceAlt
                            )
                        )
                    },
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    Icons.Outlined.Check,
                    contentDescription = "Completed",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else if (isActive) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    stepNumber.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.TextTertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Label
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextPrimary,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}

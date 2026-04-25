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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.launch

/**
 * Claymorphism Wallet Creation — Baby blue bg, stepper with solid colored icons.
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
        coroutineScope.launch { viewModel.createWallet() }
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                "Creating Your Wallet",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                textAlign = TextAlign.Center,
            )

            Text(
                "Deploying your ZeroDev Kernel account",
                style = MaterialTheme.typography.bodyMedium,
                color = TranzoColors.TextSecondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Steps in a white card
            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    WalletStep(1, "Generating Keys", state.walletStage.ordinal > 0, state.walletStage.ordinal == 0)
                    WalletStep(2, "Deploying Account", state.walletStage.ordinal > 1, state.walletStage.ordinal == 1)
                    WalletStep(3, "Activating Validators", state.walletStage.ordinal > 2, state.walletStage.ordinal == 2)
                    WalletStep(4, "Complete!", state.walletStage.ordinal > 3, state.walletStage.ordinal == 3)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Outlined.Security,
                    contentDescription = null,
                    tint = TranzoColors.ClayGreen,
                    modifier = Modifier.size(20.dp),
                )
                Text(
                    "Your wallet is secured by ZeroDev Kernel validators",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextSecondary,
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (state.walletStage.ordinal > 3) {
                ClayButton(
                    text = "Start Using Tranzo",
                    onClick = onWalletCreated,
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = TranzoColors.ClayBlue,
                    strokeWidth = 3.dp,
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
private fun WalletStep(
    stepNumber: Int,
    label: String,
    isCompleted: Boolean,
    isActive: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                when {
                    isCompleted -> TranzoColors.ClayGreen.copy(alpha = 0.08f)
                    isActive -> TranzoColors.ClayBlue.copy(alpha = 0.08f)
                    else -> Color.Transparent
                }
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .shadow(
                    elevation = if (isActive || isCompleted) 8.dp else 0.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = when {
                        isCompleted -> TranzoColors.ClayGreen.copy(alpha = 0.3f)
                        isActive -> TranzoColors.ClayBlue.copy(alpha = 0.3f)
                        else -> Color.Transparent
                    },
                )
                .clip(RoundedCornerShape(12.dp))
                .background(
                    when {
                        isCompleted -> TranzoColors.ClayGreen
                        isActive -> TranzoColors.ClayBlue
                        else -> TranzoColors.DividerGray
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (isCompleted) {
                Icon(Icons.Outlined.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            } else if (isActive) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
            } else {
                Text(stepNumber.toString(), color = TranzoColors.TextTertiary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isActive || isCompleted) FontWeight.SemiBold else FontWeight.Medium,
            color = TranzoColors.TextPrimary,
        )
    }
}

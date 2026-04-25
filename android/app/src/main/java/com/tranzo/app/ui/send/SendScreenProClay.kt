package com.tranzo.app.ui.send

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Send/Transfer Screen
 */
@Composable
fun SendScreenProClay(
    viewModel: SendViewModel = hiltViewModel(),
    onConfirm: () -> Unit = {},
) {
    var recipient by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }
    val uiState by viewModel.state.collectAsState()

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    // Handle success - navigate to confirmation
    LaunchedEffect(uiState.isSent) {
        if (uiState.isSent) {
            onConfirm()
            viewModel.reset()
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
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TranzoColors.PrimaryBlue)
            }
        } else if (uiState.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Transfer Failed",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TranzoColors.Error
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    uiState.error ?: "Unknown error",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                ClayButton(
                    text = "Go Back",
                    onClick = { viewModel.reset() },
                    gradientStart = TranzoColors.PrimaryBlue,
                    gradientEnd = TranzoColors.BlueLight
                )
            }
        } else {
            Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Text(
                "Send Crypto",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Token selector
            Text(
                "Token",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            ClayCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundGradient = listOf(
                    Color.White,
                    TranzoColors.BackgroundLight.copy(alpha = 0.7f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    color = TranzoColors.PrimaryBlue.copy(alpha = 0.12f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "₌",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TranzoColors.PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column {
                            Text(
                                selectedToken,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = TranzoColors.TextPrimary
                            )
                            Text(
                                "Balance: $8,950",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.TextTertiary
                            )
                        }
                    }

                    Icon(
                        Icons.Outlined.AttachMoney,
                        contentDescription = "Change",
                        tint = TranzoColors.TextTertiary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recipient
            Text(
                "Recipient",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            ClayTextField(
                value = recipient,
                onValueChange = { recipient = it },
                placeholder = "0x742d... or @username",
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Amount
            Text(
                "Amount",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            ClayTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = "0.00",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                leadingIcon = {
                    Icon(
                        Icons.Outlined.AttachMoney,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )

            // Quick amount buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Button(
                        onClick = { amount = listOf("100", "500", "1000")[index] },
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TranzoColors.SurfaceLight,
                            contentColor = TranzoColors.PrimaryBlue
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text(
                            "$" + listOf("100", "500", "1000")[index],
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Send Button
            ClayButton(
                text = "Review Transfer",
                onClick = {
                    viewModel.sendToken(
                        to = recipient,
                        tokenSymbol = selectedToken,
                        amount = amount
                    )
                },
                enabled = recipient.isNotBlank() && amount.isNotBlank() && !uiState.isLoading,
                gradientStart = TranzoColors.PrimaryBlue,
                gradientEnd = TranzoColors.PrimaryPurple,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


}

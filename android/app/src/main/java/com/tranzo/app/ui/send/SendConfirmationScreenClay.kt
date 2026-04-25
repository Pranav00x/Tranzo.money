package com.tranzo.app.ui.send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClaySuccessCheckmark
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Send Confirmation Screen
 */
@Composable
fun SendConfirmationScreenClay(
    recipient: String = "0x742d...",
    amount: String = "500",
    token: String = "USDC",
    viewModel: SendViewModel = hiltViewModel(),
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    var isConfirmed by remember { mutableStateOf(false) }

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
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Text(
                "Confirm Transfer",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Transfer details card
            ClayCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp)),
                backgroundGradient = listOf(
                    Color.White,
                    TranzoColors.BackgroundLight.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Amount
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "$amount",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary,
                            fontSize = 48.sp
                        )
                        Text(
                            token,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary
                        )
                    }

                    Divider(color = TranzoColors.DividerGray)

                    // Recipient
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "To",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary
                        )
                        Text(
                            recipient,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = TranzoColors.TextPrimary
                        )
                    }

                    // Gas fee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Network Fee",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary
                        )
                        Text(
                            "< $0.10",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = TranzoColors.TextPrimary
                        )
                    }

                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary
                        )
                        Text(
                            "≈ ${amount.toDoubleOrNull()?.plus(0.05) ?: amount}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.PrimaryBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Confirm button
            ClayButton(
                text = "Confirm & Send",
                onClick = {
                    isConfirmed = true
                    viewModel.sendToken(recipient, token, amount)
                    onConfirm()
                },
                gradientStart = TranzoColors.PrimaryBlue,
                gradientEnd = TranzoColors.PrimaryPurple,
            )

            // Cancel button
            Button(
                onClick = onCancel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TranzoColors.SurfaceLight,
                    contentColor = TranzoColors.TextPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    "Cancel",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

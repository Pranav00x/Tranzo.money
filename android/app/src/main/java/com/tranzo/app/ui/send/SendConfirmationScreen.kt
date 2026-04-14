package com.tranzo.app.ui.send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Transfer confirmation bottom sheet / screen.
 *
 * Shows transfer summary before final send:
 * - To address (truncated)
 * - Token + Amount
 * - Network fee (sponsored)
 * - "Confirm & Send" CTA
 */
@Composable
fun SendConfirmationScreen(
    recipientAddress: String = "0x7a3b...f4c2",
    tokenSymbol: String = "USDC",
    amount: String = "100.00",
    usdValue: String = "$100.00",
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    var isSending by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        if (isSuccess) {
            // ── Success State ────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(48.dp))
                    .background(TranzoColors.PaleTeal),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = TranzoColors.PrimaryGreen,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Transfer Sent!",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$amount $tokenSymbol sent to ${recipientAddress.take(6)}...${recipientAddress.takeLast(4)}",
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.weight(1f))

            TranzoButton(
                text = "Done",
                onClick = onCancel,
            )
        } else {
            // ── Review State ─────────────────────────────────────
            Text(
                text = "Review Transfer",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Transfer summary card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.CardSurface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    SummaryRow("To", "${recipientAddress.take(10)}...${recipientAddress.takeLast(6)}")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    SummaryRow("Token", tokenSymbol)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    SummaryRow("Amount", "$amount $tokenSymbol")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    SummaryRow("USD Value", usdValue)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Network Fee",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                        )
                        Text(
                            text = "Sponsored ✨",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.PrimaryGreen,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security note
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.PaleTeal.copy(alpha = 0.5f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryGreen,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "This transaction is gasless and secured by your smart account.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            TranzoButton(
                text = "Confirm & Send",
                onClick = {
                    isSending = true
                    onConfirm()
                    // Simulate success
                    isSuccess = true
                    isSending = false
                },
                isLoading = isSending,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TranzoSecondaryButton(
                text = "Cancel",
                onClick = onCancel,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
    }
}

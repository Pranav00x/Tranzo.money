package com.tranzo.app.ui.send

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoSecondaryButton
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Transfer confirmation screen.
 *
 * Layout:
 * - Dark teal gradient header with "Review Transfer" title
 * - White card with: From wallet, To address, Token, Amount, USD Value, Network Fee (sponsored)
 * - Security note
 * - "Confirm & Send" green pill CTA
 * - Success state after confirmation
 */
@Composable
fun SendConfirmationScreen(
    fromAddress: String = "0xYour...Wallet",
    recipientAddress: String = "0x7a3b...f4c2",
    tokenSymbol: String = "USDC",
    amount: String = "100.00",
    usdValue: String = "$100.00",
    onConfirm: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    var isSending by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        if (isSuccess) {
            // ── Success State ────────────────────────────────────────
            SuccessContent(
                amount = amount,
                tokenSymbol = tokenSymbol,
                recipientAddress = recipientAddress,
                onDone = onBack,
            )
        } else {
            // ── Gradient Header ──────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(TranzoColors.Navy, TranzoColors.DarkTeal),
                        ),
                    )
                    .padding(horizontal = 24.dp)
                    .padding(top = 8.dp, bottom = 32.dp),
            ) {
                Column {
                    // Back button row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Back",
                                tint = TranzoColors.TextOnDark,
                            )
                        }
                        Text(
                            text = "Review Transfer",
                            style = MaterialTheme.typography.headlineMedium,
                            color = TranzoColors.TextOnDark,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Hero amount display
                    Text(
                        text = "$amount $tokenSymbol",
                        style = MaterialTheme.typography.displayMedium,
                        color = TranzoColors.TextOnDark,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = usdValue,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TranzoColors.LightTeal,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            // ── White Content (overlaps header by 20dp) ──────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(TranzoColors.Background)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(top = 28.dp),
            ) {
                // Transfer summary card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = TranzoColors.CardSurface,
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        SummaryRow(
                            label = "From",
                            value = formatAddress(fromAddress),
                        )
                        RowDivider()
                        SummaryRow(
                            label = "To",
                            value = formatAddress(recipientAddress),
                        )
                        RowDivider()
                        SummaryRow(label = "Token", value = tokenSymbol)
                        RowDivider()
                        SummaryRow(label = "Amount", value = "$amount $tokenSymbol")
                        RowDivider()
                        SummaryRow(label = "USD Value", value = usdValue)
                        RowDivider()
                        // Network fee row — special green "Sponsored" treatment
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Network Fee",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TranzoColors.TextSecondary,
                            )
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = TranzoColors.PaleTeal,
                            ) {
                                Text(
                                    text = "Sponsored ✨",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TranzoColors.PrimaryBlack,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                )
                            }
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
                            .padding(14.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Shield,
                            contentDescription = null,
                            tint = TranzoColors.PrimaryBlack,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 1.dp),
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "This transaction is gasless and secured by your smart account. " +
                                    "Always verify the recipient address before confirming.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))
            }

            // ── CTA Section ──────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TranzoColors.Background)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp, top = 8.dp),
            ) {
                TranzoButton(
                    text = "Confirm & Send",
                    onClick = {
                        isSending = true
                        onConfirm()
                        isSuccess = true
                        isSending = false
                    },
                    isLoading = isSending,
                )

                Spacer(modifier = Modifier.height(12.dp))

                TranzoSecondaryButton(
                    text = "Cancel",
                    onClick = onBack,
                )
            }
        }
    }
}

@Composable
private fun SuccessContent(
    amount: String,
    tokenSymbol: String,
    recipientAddress: String,
    onDone: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Success icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(TranzoColors.PaleTeal),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = TranzoColors.PrimaryBlack,
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Transfer Sent!",
            style = MaterialTheme.typography.headlineLarge,
            color = TranzoColors.TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$amount $tokenSymbol has been sent to",
            style = MaterialTheme.typography.bodyLarge,
            color = TranzoColors.TextSecondary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = formatAddress(recipientAddress),
            style = MaterialTheme.typography.bodyLarge,
            color = TranzoColors.PrimaryBlack,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Gasless badge
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = TranzoColors.PaleTeal,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = TranzoColors.PrimaryBlack,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Zero gas fees paid",
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.PrimaryBlack,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TranzoButton(
            text = "Done",
            onClick = onDone,
        )
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        color = TranzoColors.DividerGray,
    )
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
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
            color = TranzoColors.TextPrimary,
        )
    }
}

private fun formatAddress(address: String): String {
    return if (address.length > 12) {
        "${address.take(8)}...${address.takeLast(6)}"
    } else {
        address
    }
}

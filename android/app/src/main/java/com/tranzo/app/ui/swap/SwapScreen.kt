package com.tranzo.app.ui.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Swap screen — CheQ-inspired.
 *
 * Layout:
 * - "From" token card (with amount)
 * - Swap arrow button (circular, rotates on tap)
 * - "To" token card (with estimated amount)
 * - Rate display
 * - Slippage info
 * - "Swap" green pill CTA
 */
@Composable
fun SwapScreen(
    onBack: () -> Unit = {},
    onSwap: () -> Unit = {},
) {
    var fromToken by remember { mutableStateOf("USDC") }
    var toToken by remember { mutableStateOf("POL") }
    var fromAmount by remember { mutableStateOf("") }
    var isSwapped by remember { mutableStateOf(false) }

    // Mock exchange rate
    val exchangeRate = "1 USDC = 2.45 POL"
    val estimatedReceive = if (fromAmount.isNotEmpty()) {
        try {
            String.format("%.2f", fromAmount.toDouble() * 2.45)
        } catch (e: Exception) {
            "0.00"
        }
    } else "0.00"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        // ── Top Bar ──────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                )
            }
            Text(
                text = "Swap",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── From Card ────────────────────────────────────────
            SwapTokenCard(
                label = "From",
                token = fromToken,
                amount = fromAmount,
                balance = "1,234.56",
                onAmountChange = { fromAmount = it },
                onTokenClick = { /* token picker */ },
            )

            // ── Swap Arrow ───────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-8).dp),
                contentAlignment = Alignment.Center,
            ) {
                FilledIconButton(
                    onClick = {
                        val temp = fromToken
                        fromToken = toToken
                        toToken = temp
                        isSwapped = !isSwapped
                    },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = TranzoColors.PrimaryBlack,
                        contentColor = TranzoColors.White,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SwapVert,
                        contentDescription = "Swap direction",
                        modifier = Modifier.rotate(if (isSwapped) 180f else 0f),
                    )
                }
            }

            // ── To Card ──────────────────────────────────────────
            SwapTokenCard(
                label = "To (estimated)",
                token = toToken,
                amount = estimatedReceive,
                balance = "245.80",
                isReadOnly = true,
                onAmountChange = {},
                onTokenClick = { /* token picker */ },
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Rate & Slippage ──────────────────────────────────
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.LightGray,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Exchange Rate",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                        )
                        Text(
                            text = exchangeRate,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Slippage",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                        )
                        Text(
                            text = "0.5%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                            color = TranzoColors.PrimaryBlack,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }

        // ── CTA ──────────────────────────────────────────────────
        TranzoButton(
            text = "Swap",
            onClick = onSwap,
            enabled = fromAmount.isNotEmpty() && fromAmount != "0",
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
private fun SwapTokenCard(
    label: String,
    token: String,
    amount: String,
    balance: String,
    isReadOnly: Boolean = false,
    onAmountChange: (String) -> Unit,
    onTokenClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = TranzoColors.CardSurface,
        tonalElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.TextSecondary,
                )
                Text(
                    text = "Balance: $balance",
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.TextTertiary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Token selector
                Surface(
                    onClick = onTokenClick,
                    shape = RoundedCornerShape(12.dp),
                    color = TranzoColors.LightGray,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(TranzoColors.PaleTeal),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = token.take(2),
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.PrimaryBlack,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = token,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = TranzoColors.TextSecondary,
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Amount
                if (isReadOnly) {
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                    )
                } else {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { newVal ->
                            if (newVal.all { it.isDigit() || it == '.' }) {
                                onAmountChange(newVal)
                            }
                        },
                        placeholder = {
                            Text("0.00", style = MaterialTheme.typography.headlineSmall)
                        },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.width(150.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TranzoColors.PrimaryBlack,
                            unfocusedBorderColor = TranzoColors.BorderGray.copy(alpha = 0f),
                        ),
                    )
                }
            }
        }
    }
}

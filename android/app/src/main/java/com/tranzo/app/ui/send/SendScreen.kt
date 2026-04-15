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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Send screen — CheQ-inspired.
 *
 * Layout:
 * - Top bar with back arrow + "Send"
 * - Token selector dropdown
 * - Recipient address field (with QR scan + paste icons)
 * - Amount input (large centered number)
 * - Available balance display
 * - Network fee estimate
 * - "Review Transfer" green pill CTA
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendScreen(
    onBack: () -> Unit = {},
    onReview: (to: String, token: String, amount: String) -> Unit = { _, _, _ -> },
) {
    var recipientAddress by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }
    var showTokenPicker by remember { mutableStateOf(false) }

    val tokens = listOf(
        Triple("USDC", "USD Coin", "$1,234.56"),
        Triple("USDT", "Tether", "$500.00"),
        Triple("POL", "Polygon", "245.8 POL"),
        Triple("WETH", "Wrapped ETH", "0.15 WETH"),
    )

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
                    tint = TranzoColors.TextPrimary,
                )
            }
            Text(
                text = "Send",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ── Token Selector ───────────────────────────────────
            Text(
                text = "Token",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                onClick = { showTokenPicker = true },
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.CardSurface,
                tonalElevation = 1.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(TranzoColors.PaleTeal),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = selectedToken.take(2),
                            style = MaterialTheme.typography.labelMedium,
                            color = TranzoColors.PrimaryBlack,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = selectedToken,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                        val balance = tokens.find { it.first == selectedToken }?.third ?: "0"
                        Text(
                            text = "Balance: $balance",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Select token",
                        tint = TranzoColors.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Recipient Address ────────────────────────────────
            TranzoTextField(
                value = recipientAddress,
                onValueChange = { recipientAddress = it },
                label = "Recipient Address",
                placeholder = "0x... or ENS name",
                trailingIcon = {
                    Row {
                        IconButton(onClick = { /* paste from clipboard */ }) {
                            Icon(
                                imageVector = Icons.Outlined.ContentPaste,
                                contentDescription = "Paste",
                                tint = TranzoColors.PrimaryBlack,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                        IconButton(onClick = { /* scan QR */ }) {
                            Icon(
                                imageVector = Icons.Outlined.QrCodeScanner,
                                contentDescription = "Scan QR",
                                tint = TranzoColors.PrimaryBlack,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Amount Input ─────────────────────────────────────
            Text(
                text = "Amount",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.CardSurface,
                tonalElevation = 1.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Large amount display
                    TranzoTextField(
                        value = amount,
                        onValueChange = { newVal ->
                            if (newVal.all { it.isDigit() || it == '.' }) {
                                amount = newVal
                            }
                        },
                        label = "0.00",
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Max button
                    TextButton(onClick = {
                        amount = tokens.find { it.first == selectedToken }?.third
                            ?.replace("$", "")?.replace(",", "")
                            ?.split(" ")?.firstOrNull() ?: "0"
                    }) {
                        Text(
                            text = "Use Max",
                            style = MaterialTheme.typography.labelMedium,
                            color = TranzoColors.PrimaryBlack,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Fee Estimate ─────────────────────────────────────
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.PaleTeal.copy(alpha = 0.5f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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

        // ── CTA Button ───────────────────────────────────────────
        TranzoButton(
            text = "Review Transfer",
            onClick = { onReview(recipientAddress, selectedToken, amount) },
            enabled = recipientAddress.startsWith("0x") && amount.isNotEmpty(),
            modifier = Modifier.padding(24.dp),
        )
    }

    // ── Token Picker Bottom Sheet ─────────────────────────────
    if (showTokenPicker) {
        ModalBottomSheet(
            onDismissRequest = { showTokenPicker = false },
            containerColor = TranzoColors.CardSurface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Select Token",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                tokens.forEach { (symbol, name, balance) ->
                    Surface(
                        onClick = {
                            selectedToken = symbol
                            showTokenPicker = false
                        },
                        color = if (selectedToken == symbol)
                            TranzoColors.PaleTeal
                        else TranzoColors.CardSurface,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(TranzoColors.PaleTeal),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = symbol.take(2),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TranzoColors.PrimaryBlack,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = name, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = balance,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TranzoColors.TextSecondary,
                                )
                            }
                            if (selectedToken == symbol) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = null,
                                    tint = TranzoColors.PrimaryBlack,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

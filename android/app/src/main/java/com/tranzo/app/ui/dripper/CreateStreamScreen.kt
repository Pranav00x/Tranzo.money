package com.tranzo.app.ui.dripper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Create stream screen — form to set up a new salary stream.
 *
 * Layout:
 * - Recipient address
 * - Token selector
 * - Total amount
 * - Duration (days)
 * - Rate calc display (auto-computed)
 * - Review & Create CTA
 */
@Composable
fun CreateStreamScreen(
    onBack: () -> Unit = {},
    onCreate: () -> Unit = {},
) {
    var recipientAddress by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var durationDays by remember { mutableStateOf("") }
    var selectedToken by remember { mutableStateOf("USDC") }

    // Computed rate
    val ratePerDay = if (totalAmount.isNotEmpty() && durationDays.isNotEmpty()) {
        try {
            val total = totalAmount.toDouble()
            val days = durationDays.toInt()
            if (days > 0) String.format("%.2f", total / days) else "—"
        } catch (e: Exception) {
            "—"
        }
    } else "—"

    val ratePerSecond = if (totalAmount.isNotEmpty() && durationDays.isNotEmpty()) {
        try {
            val total = totalAmount.toDouble()
            val seconds = durationDays.toInt() * 86400
            if (seconds > 0) String.format("%.8f", total / seconds) else "—"
        } catch (e: Exception) {
            "—"
        }
    } else "—"

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
                text = "Create Stream",
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

            // Info card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.PaleTeal.copy(alpha = 0.5f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = TranzoColors.PrimaryGreen,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Create a salary stream to send tokens continuously. " +
                                "The recipient can withdraw anytime.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recipient
            TranzoTextField(
                value = recipientAddress,
                onValueChange = { recipientAddress = it },
                label = "Recipient Address",
                placeholder = "0x... smart account address",
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.ContentPaste,
                            contentDescription = "Paste",
                            tint = TranzoColors.PrimaryGreen,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Token selector
            Text(
                text = "Token",
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("USDC", "USDT").forEach { token ->
                    FilterChip(
                        selected = selectedToken == token,
                        onClick = { selectedToken = token },
                        label = { Text(token) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TranzoColors.PrimaryGreen,
                            selectedLabelColor = TranzoColors.TextOnGreen,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Total amount
            TranzoTextField(
                value = totalAmount,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) totalAmount = it },
                label = "Total Amount ($selectedToken)",
                placeholder = "e.g. 12000",
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Duration
            TranzoTextField(
                value = durationDays,
                onValueChange = { if (it.all { c -> c.isDigit() }) durationDays = it },
                label = "Duration (days)",
                placeholder = "e.g. 60",
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Rate preview
            if (ratePerDay != "—") {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = TranzoColors.CardSurface,
                    tonalElevation = 1.dp,
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Stream Preview",
                            style = MaterialTheme.typography.headlineSmall,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PreviewRow("Rate per day", "$ratePerDay $selectedToken")
                        Spacer(modifier = Modifier.height(8.dp))
                        PreviewRow("Rate per second", "$ratePerSecond $selectedToken")
                        Spacer(modifier = Modifier.height(8.dp))
                        PreviewRow("Duration", "$durationDays days")
                        Spacer(modifier = Modifier.height(8.dp))
                        PreviewRow("Total", "$totalAmount $selectedToken")

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = TranzoColors.DividerGray,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Gas Fee",
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
            }
        }

        // ── CTA ──────────────────────────────────────────────────
        TranzoButton(
            text = "Create Stream",
            onClick = onCreate,
            enabled = recipientAddress.startsWith("0x") &&
                    totalAmount.isNotEmpty() &&
                    durationDays.isNotEmpty(),
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
private fun PreviewRow(label: String, value: String) {
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

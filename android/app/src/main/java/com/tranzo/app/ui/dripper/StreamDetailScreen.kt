package com.tranzo.app.ui.dripper

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlinx.coroutines.delay

/**
 * Stream detail screen — animated live counter showing real-time accrual.
 *
 * Layout:
 * - Gradient header with live counter
 * - Stream details card (from, token, rate, start/end)
 * - Progress visualization
 * - Withdraw CTA + Cancel option
 */
@Composable
fun StreamDetailScreen(
    streamId: String = "1",
    fromName: String = "Tranzo Labs",
    tokenSymbol: String = "USDC",
    initialEarned: Double = 4562.78,
    totalAmount: Double = 12000.0,
    ratePerDay: Double = 200.0,
    startDate: String = "Jan 15, 2026",
    endDate: String = "May 15, 2026",
    status: String = "active",
    onBack: () -> Unit = {},
    onWithdraw: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    val ratePerSecond = ratePerDay / 86400.0

    // Live counter
    var earned by remember { mutableStateOf(initialEarned) }
    LaunchedEffect(Unit) {
        if (status == "active") {
            while (true) {
                delay(100) // Update 10x per second for smooth animation
                earned += ratePerSecond / 10
            }
        }
    }

    val progress = (earned / totalAmount).coerceIn(0.0, 1.0).toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        // ── Gradient Header ──────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            TranzoColors.Navy,
                            TranzoColors.DarkTeal,
                        ),
                    ),
                )
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 32.dp),
        ) {
            Column {
                // Top bar
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
                        text = "Stream #$streamId",
                        style = MaterialTheme.typography.headlineMedium,
                        color = TranzoColors.TextOnDark,
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // From label
                Text(
                    text = "From $fromName",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextOnDarkMuted,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Live counter — the hero!
                Text(
                    text = "$${String.format("%,.6f", earned)}",
                    style = MaterialTheme.typography.displayLarge,
                    color = TranzoColors.TextOnDark,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$tokenSymbol · $${String.format("%.2f", ratePerDay)}/day",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.LightTeal,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        }

        // ── White Content ────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(TranzoColors.Background)
                .padding(24.dp),
        ) {
            // Progress bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.PrimaryBlack,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = TranzoColors.PrimaryBlack,
                    trackColor = TranzoColors.PaleTeal,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stream details card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = TranzoColors.CardSurface,
                tonalElevation = 1.dp,
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    DetailRow("Sender", fromName)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    DetailRow("Token", tokenSymbol)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    DetailRow("Rate", "$${String.format("%.2f", ratePerDay)} / day")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    DetailRow("Earned", "$${String.format("%.2f", earned)}")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    DetailRow("Total", "$${String.format("%,.2f", totalAmount)}")
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    DetailRow("Start", startDate)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = TranzoColors.DividerGray,
                    )
                    DetailRow("End", endDate)
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
                            color = TranzoColors.PrimaryBlack,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Actions ──────────────────────────────────────────
            if (status == "active") {
                TranzoButton(
                    text = "Withdraw $${String.format("%.2f", earned)} $tokenSymbol",
                    onClick = onWithdraw,
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onCancel,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Cancel Stream",
                        color = TranzoColors.Error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
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

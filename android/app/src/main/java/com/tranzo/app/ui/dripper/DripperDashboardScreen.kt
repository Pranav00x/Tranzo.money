package com.tranzo.app.ui.dripper

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.tranzo.app.ui.components.StatusBadge
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.delay

data class StreamUiItem(
    val id: String,
    val fromName: String,
    val tokenSymbol: String,
    val earnedSoFar: String,
    val totalAmount: String,
    val ratePerDay: String,
    val progressPercent: Float,
    val status: String,       // "active", "completed", "cancelled"
    val endDate: String,
)

/**
 * Dripper Dashboard — salary streaming.
 *
 * Layout:
 * - Dark gradient header with total earned counter (real-time updating)
 * - "Active Streams" label with count
 * - Stream cards with progress bars
 * - FAB to create new stream
 */
@Composable
fun DripperDashboardScreen(
    onCreateStream: () -> Unit = {},
    onStreamClick: (String) -> Unit = {},
) {
    // Real-time counter animation
    var totalEarned by remember { mutableStateOf(4562.78) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            totalEarned += 0.0023 // ~$0.0023/second = ~$200/day
        }
    }

    val streams = listOf(
        StreamUiItem(
            id = "1",
            fromName = "Tranzo Labs",
            tokenSymbol = "USDC",
            earnedSoFar = "$4,562.78",
            totalAmount = "$12,000.00",
            ratePerDay = "$200.00",
            progressPercent = 0.38f,
            status = "active",
            endDate = "May 15, 2026",
        ),
        StreamUiItem(
            id = "2",
            fromName = "DeFi Protocol X",
            tokenSymbol = "USDC",
            earnedSoFar = "$850.00",
            totalAmount = "$3,000.00",
            ratePerDay = "$50.00",
            progressPercent = 0.28f,
            status = "active",
            endDate = "Jun 1, 2026",
        ),
        StreamUiItem(
            id = "3",
            fromName = "Freelance Client",
            tokenSymbol = "USDT",
            earnedSoFar = "$2,000.00",
            totalAmount = "$2,000.00",
            ratePerDay = "—",
            progressPercent = 1.0f,
            status = "completed",
            endDate = "Mar 30, 2026",
        ),
    )

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(TranzoColors.Background),
        ) {
            // ── Gradient Header ──────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    TranzoColors.Navy,
                                    TranzoColors.GradientMid,
                                    TranzoColors.DarkTeal,
                                ),
                            ),
                        )
                        .statusBarsPadding()
                        .padding(24.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Dripper",
                                style = MaterialTheme.typography.headlineMedium,
                                color = TranzoColors.TextOnDark,
                                fontWeight = FontWeight.Bold,
                            )
                            Icon(
                                imageVector = Icons.Outlined.WaterDrop,
                                contentDescription = null,
                                tint = TranzoColors.LightTeal,
                                modifier = Modifier.size(28.dp),
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Total Earned",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextOnDarkMuted,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Real-time counter
                        Text(
                            text = "$${String.format("%,.4f", totalEarned)}",
                            style = MaterialTheme.typography.displayLarge,
                            color = TranzoColors.TextOnDark,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Streaming every second 💧",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.LightTeal,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Withdraw all button
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TranzoColors.PrimaryGreen,
                            ),
                            modifier = Modifier.fillMaxWidth(0.6f),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccountBalanceWallet,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Withdraw All")
                        }
                    }
                }
            }

            // ── White Content ────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp)
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(TranzoColors.Background)
                        .padding(horizontal = 20.dp),
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "Your Streams",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            StatusBadge(
                                text = "${streams.count { it.status == "active" }} Active",
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // ── Stream Cards ─────────────────────────────────────
            items(streams) { stream ->
                StreamCard(
                    stream = stream,
                    onClick = { onStreamClick(stream.id) },
                )
            }

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // ── FAB ──────────────────────────────────────────────────
        FloatingActionButton(
            onClick = onCreateStream,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 100.dp),
            containerColor = TranzoColors.PrimaryGreen,
            contentColor = TranzoColors.TextOnGreen,
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Create Stream")
        }
    }
}

@Composable
private fun StreamCard(
    stream: StreamUiItem,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TranzoColors.CardSurface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                when (stream.status) {
                                    "active" -> TranzoColors.PaleTeal
                                    "completed" -> TranzoColors.LightGray
                                    else -> TranzoColors.ErrorLight
                                }
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.WaterDrop,
                            contentDescription = null,
                            tint = when (stream.status) {
                                "active" -> TranzoColors.PrimaryGreen
                                "completed" -> TranzoColors.TextSecondary
                                else -> TranzoColors.Error
                            },
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = stream.fromName,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "${stream.ratePerDay}/day in ${stream.tokenSymbol}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TranzoColors.TextSecondary,
                        )
                    }
                }

                StatusBadge(
                    text = stream.status.replaceFirstChar { it.uppercase() },
                    isError = stream.status == "cancelled",
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stream.earnedSoFar,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.PrimaryGreen,
                    )
                    Text(
                        text = stream.totalAmount,
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextTertiary,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { stream.progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = TranzoColors.PrimaryGreen,
                    trackColor = TranzoColors.PaleTeal,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (stream.status == "active") "Ends ${stream.endDate}" else "Ended ${stream.endDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )
            }
        }
    }
}

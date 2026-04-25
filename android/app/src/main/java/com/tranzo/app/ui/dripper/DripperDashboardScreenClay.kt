package com.tranzo.app.ui.dripper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayStatCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Dripper Dashboard Screen
 * View and manage payment streams
 */
@Composable
fun DripperDashboardScreenClay(
    viewModel: DripperViewModel = hiltViewModel(),
    onCreateStream: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = TranzoColors.ClayBackground
            )
    ) {
        if (uiState.isLoading && uiState.streams.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TranzoColors.PrimaryBlue)
            }
        } else if (uiState.error != null && uiState.streams.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Error loading streams",
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
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Streams",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                        fontSize = 28.sp
                    )
                    Text(
                        "Manage your payment streams",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Stats
                    Text(
                        "Overview",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextTertiary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ClayStatCard(
                            label = "Active Streams",
                            value = uiState.activeCount.toString(),
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            gradientStart = TranzoColors.PrimaryBlue,
                            gradientEnd = TranzoColors.BlueLight,
                        )

                        ClayStatCard(
                            label = "Monthly Flow",
                            value = calculateMonthlyFlow(uiState.streams),
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            gradientStart = TranzoColors.PrimaryGreen,
                            gradientEnd = TranzoColors.AccentEmerald,
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Active streams
                    Text(
                        "Active Streams",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextTertiary
                    )

                    if (uiState.streams.isEmpty()) {
                        Text(
                            "No active streams",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(uiState.streams) { stream ->
                                StreamItemClay(
                                    recipient = stream.employeeAddress.take(10) + "...",
                                    amount = formatStreamAmount(stream.amountPerSecond),
                                    status = stream.status
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Create stream button
                    ClayButton(
                        text = "Create New Stream",
                        onClick = onCreateStream,
                        gradientStart = TranzoColors.PrimaryPurple,
                        gradientEnd = TranzoColors.PinkLight,
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

private fun calculateMonthlyFlow(streams: List<com.tranzo.app.data.model.StreamDetail>): String {
    val activeStreams = streams.filter { it.status.equals("ACTIVE", true) }
    val monthlyTotal = activeStreams.map { stream ->
        try {
            stream.amountPerSecond.toBigDecimal() * 86400.toBigDecimal() * 30.toBigDecimal()
        } catch (e: Exception) {
            java.math.BigDecimal.ZERO
        }
    }.fold(java.math.BigDecimal.ZERO) { acc, value -> acc + value }

    return "$" + String.format("%.0f", monthlyTotal)
}

private fun formatStreamAmount(amountPerSecond: String): String {
    return try {
        val monthlyAmount = amountPerSecond.toBigDecimal() * 86400.toBigDecimal() * 30.toBigDecimal()
        "$" + String.format("%.0f", monthlyAmount) + "/mo"
    } catch (e: Exception) {
        "$0/mo"
    }
}

@Composable
private fun StreamItemClay(
    recipient: String,
    amount: String,
    status: String,
) {
    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        backgroundGradient = listOf(Color.White, TranzoColors.BackgroundLight.copy(alpha = 0.7f))
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .background(
                            color = TranzoColors.PrimaryPurple.copy(alpha = 0.12f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Timer,
                        contentDescription = "Stream",
                        tint = TranzoColors.PrimaryPurple,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        "Pay $recipient",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary
                    )
                    Text(
                        amount,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }
            }

            Text(
                status,
                style = MaterialTheme.typography.labelSmall,
                color = TranzoColors.Success,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}



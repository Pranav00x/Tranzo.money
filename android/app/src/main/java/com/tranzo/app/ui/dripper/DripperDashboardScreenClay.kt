package com.tranzo.app.ui.dripper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun DripperDashboardScreenClay(
    viewModel: DripperViewModel = hiltViewModel(),
    onCreateStream: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        if (uiState.isLoading && uiState.streams.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.ClayPurple)
            }
        } else if (uiState.error != null && uiState.streams.isEmpty()) {
            Column(Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(8.dp))
                Text(uiState.error ?: "", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, textAlign = TextAlign.Center)
                Spacer(Modifier.height(24.dp))
                ClayButton(text = "Try Again", onClick = { }, modifier = Modifier.width(200.dp), containerColor = TranzoColors.ClayPurple)
            }
        } else {
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(24.dp))
                
                // ── Header ──
                Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Streams", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                        Text("Manage your payments", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                    }
                    ClayIconPill(color = TranzoColors.ClayPurple, size = 48.dp, cornerRadius = 16.dp) {
                        Icon(Icons.Outlined.WaterDrop, null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(Modifier.height(32.dp))

                // ── Metrics ──
                Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ClayCard(modifier = Modifier.weight(1f), cornerRadius = 20.dp) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Active", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text(uiState.activeCount.toString(), style = MaterialTheme.typography.headlineMedium, color = TranzoColors.TextPrimary, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                    ClayCard(modifier = Modifier.weight(1f), cornerRadius = 20.dp) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Monthly", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text(calculateMonthlyFlow(uiState.streams), style = MaterialTheme.typography.headlineMedium, color = TranzoColors.ClayGreen, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }

                Spacer(Modifier.height(32.dp))

                // ── Active Streams (Structured List) ──
                Text("ACTIVE STREAMS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))

                if (uiState.streams.isEmpty()) {
                    ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                        Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            ClayIconPill(color = TranzoColors.ClayBackgroundAlt, size = 56.dp, cornerRadius = 18.dp) {
                                Icon(Icons.Outlined.Timer, null, tint = TranzoColors.TextSecondary, modifier = Modifier.size(28.dp))
                            }
                            Text("No active streams", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                            Text("Create a stream to automate payments", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary)
                        }
                    }
                } else {
                    ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                        Column(Modifier.fillMaxWidth()) {
                            uiState.streams.forEachIndexed { index, stream ->
                                StreamRow(
                                    recipient = stream.employeeAddress.take(10) + "...",
                                    amount = formatStreamAmount(stream.amountPerSecond),
                                    status = stream.status,
                                    showDivider = index < uiState.streams.size - 1
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                ClayButton(text = "Create Stream", onClick = onCreateStream, containerColor = TranzoColors.ClayPurple, modifier = Modifier.padding(horizontal = 24.dp))

                Spacer(Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun StreamRow(recipient: String, amount: String, status: String, showDivider: Boolean) {
    Column {
        Row(Modifier.fillMaxWidth().clickable {}.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                ClayIconPill(color = TranzoColors.ClayPurple, size = 44.dp, cornerRadius = 15.dp) {
                    Icon(Icons.Outlined.Timer, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("To $recipient", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                    Text(amount, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                }
            }
            Box(Modifier.clip(RoundedCornerShape(8.dp)).background(TranzoColors.ClayGreenSoft).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(status, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.ClayGreen, fontSize = 9.sp)
            }
        }
        if (showDivider) {
            HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

private fun calculateMonthlyFlow(streams: List<com.tranzo.app.data.model.StreamDetail>): String {
    val activeStreams = streams.filter { it.status.equals("ACTIVE", true) }
    val monthlyTotal = activeStreams.map { stream ->
        try { stream.amountPerSecond.toBigDecimal() * 86400.toBigDecimal() * 30.toBigDecimal() }
        catch (e: Exception) { java.math.BigDecimal.ZERO }
    }.fold(java.math.BigDecimal.ZERO) { acc, value -> acc + value }
    return "$" + String.format("%.0f", monthlyTotal)
}

private fun formatStreamAmount(amountPerSecond: String): String {
    return try {
        val monthlyAmount = amountPerSecond.toBigDecimal() * 86400.toBigDecimal() * 30.toBigDecimal()
        "$" + String.format("%.0f", monthlyAmount) + "/mo"
    } catch (e: Exception) { "$0/mo" }
}

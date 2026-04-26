package com.tranzo.app.ui.dripper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.06f), 240f, Offset(size.width * 0.85f, size.height * 0.1f))
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.05f), 200f, Offset(size.width * 0.1f, size.height * 0.4f))
            drawCircle(TranzoColors.ClayGreen.copy(alpha = 0.04f), 150f, Offset(size.width * 0.7f, size.height * 0.8f))
        }

        if (uiState.isLoading && uiState.streams.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ClayIconPill(color = TranzoColors.ClayPurple, size = 56.dp, cornerRadius = 18.dp) {
                        CircularProgressIndicator(Modifier.size(24.dp), color = Color.White, strokeWidth = 2.5.dp)
                    }
                    Text("Loading streams...", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                }
            }
        } else if (uiState.error != null && uiState.streams.isEmpty()) {
            Column(Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                ClayIconPill(color = TranzoColors.ClayCoral, size = 64.dp, cornerRadius = 22.dp) {
                    Icon(Icons.Outlined.ErrorOutline, null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Spacer(Modifier.height(20.dp))
                Text("Error loading streams", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(8.dp))
                Text(uiState.error ?: "", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, textAlign = TextAlign.Center)
            }
        } else {
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                // Header
                Row(Modifier.fillMaxWidth().padding(24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Streams", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text("Manage your payment streams", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                    }
                    ClayIconPill(color = TranzoColors.ClayPurple, size = 44.dp) {
                        Icon(Icons.Outlined.WaterDrop, null, tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                }

                Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Stats
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ClayStatCard("Active Streams", uiState.activeCount.toString(),
                            modifier = Modifier.weight(1f).height(100.dp), gradientStart = TranzoColors.ClayPurple, gradientEnd = Color(0xFFB08BE8))
                        ClayStatCard("Monthly Flow", calculateMonthlyFlow(uiState.streams),
                            modifier = Modifier.weight(1f).height(100.dp), gradientStart = TranzoColors.ClayGreen, gradientEnd = Color(0xFF5AE8A8))
                    }

                    Spacer(Modifier.height(8.dp))

                    Text("ACTIVE STREAMS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp)

                    if (uiState.streams.isEmpty()) {
                        // Empty state illustration
                        ClayCard(Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                            Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                ClayIconPill(color = TranzoColors.ClayPurple.copy(alpha = 0.5f), size = 64.dp, cornerRadius = 22.dp) {
                                    Icon(Icons.Outlined.WaterDrop, null, tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                                Text("No active streams yet", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextSecondary)
                                Text("Create your first payment stream", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                            }
                        }
                    } else {
                        uiState.streams.forEach { stream ->
                            StreamCard(
                                recipient = stream.employeeAddress.take(10) + "...",
                                amount = formatStreamAmount(stream.amountPerSecond),
                                status = stream.status,
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    ClayButton(text = "Create New Stream", onClick = onCreateStream, containerColor = TranzoColors.ClayPurple)

                    Spacer(Modifier.height(100.dp))
                }
            }
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

@Composable
private fun StreamCard(recipient: String, amount: String, status: String) {
    ClayCard(Modifier.fillMaxWidth().height(76.dp), shadowElevation = 6.dp, cornerRadius = 20.dp) {
        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                ClayIconPill(color = TranzoColors.ClayPurple, size = 44.dp, cornerRadius = 15.dp) {
                    Icon(Icons.Outlined.Timer, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Pay $recipient", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                    Text(amount, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                }
            }
            Box(Modifier.clip(RoundedCornerShape(8.dp)).background(TranzoColors.ClayGreenSoft).padding(horizontal = 10.dp, vertical = 4.dp)) {
                Text(status, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.ClayGreen, fontSize = 10.sp)
            }
        }
    }
}

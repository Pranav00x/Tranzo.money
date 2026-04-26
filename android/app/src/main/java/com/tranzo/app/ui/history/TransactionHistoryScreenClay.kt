package com.tranzo.app.ui.history

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun TransactionHistoryScreenClay(viewModel: HistoryViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(TranzoColors.ClayBlue.copy(alpha = 0.05f), 220f, Offset(size.width * 0.85f, size.height * 0.1f))
            drawCircle(TranzoColors.ClayPurple.copy(alpha = 0.04f), 180f, Offset(size.width * 0.1f, size.height * 0.4f))
        }

        if (uiState.isLoading && uiState.transactions.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ClayIconPill(color = TranzoColors.ClayBlue, size = 56.dp, cornerRadius = 18.dp) {
                        CircularProgressIndicator(Modifier.size(24.dp), color = Color.White, strokeWidth = 2.5.dp)
                    }
                    Text("Loading history...", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                }
            }
        } else if (uiState.error != null && uiState.transactions.isEmpty()) {
            Column(Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                ClayIconPill(color = TranzoColors.ClayCoral, size = 64.dp, cornerRadius = 22.dp) {
                    Icon(Icons.Outlined.ErrorOutline, null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
                Spacer(Modifier.height(20.dp))
                Text("Error loading history", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(8.dp))
                Text(uiState.error ?: "", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, textAlign = TextAlign.Center)
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                // Header
                Row(Modifier.fillMaxWidth().padding(24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("History", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text("Your recent transactions", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                    }
                    ClayIconPill(color = TranzoColors.ClayBlue, size = 44.dp) {
                        Icon(Icons.Outlined.Receipt, null, tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                }

                LazyColumn(
                    Modifier.fillMaxSize().padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 100.dp),
                ) {
                    items(uiState.transactions) { tx ->
                        val isSent = "Sent" in (tx.type ?: "")
                        val iconColor = if (isSent) TranzoColors.ClayCoral else TranzoColors.ClayGreen
                        val icon = if (isSent) Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward

                        ClayCard(Modifier.fillMaxWidth().height(76.dp), shadowElevation = 6.dp, cornerRadius = 20.dp) {
                            Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
                                        Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
                                    }
                                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Text(tx.type ?: "Transaction", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                                        Text(
                                            java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.US).format(java.util.Date(tx.createdAt * 1000)),
                                            style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary
                                        )
                                    }
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(if (isSent) "-" else "+", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold,
                                        color = if (isSent) TranzoColors.ClayCoral else TranzoColors.ClayGreen)
                                    Box(Modifier.clip(RoundedCornerShape(6.dp)).background(if (isSent) TranzoColors.ClayCoralSoft else TranzoColors.ClayGreenSoft).padding(horizontal = 8.dp, vertical = 2.dp)) {
                                        Text(if (isSent) "Sent" else "Received", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, fontSize = 9.sp,
                                            color = if (isSent) TranzoColors.ClayCoral else TranzoColors.ClayGreen)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

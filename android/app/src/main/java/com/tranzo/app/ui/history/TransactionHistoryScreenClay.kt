package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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

    Box(Modifier.fillMaxSize().background(Color.White)) {
        if (uiState.isLoading && uiState.transactions.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.TextPrimary)
            }
        } else if (uiState.error != null && uiState.transactions.isEmpty()) {
            Column(Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Error", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(8.dp))
                Text(uiState.error ?: "", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, textAlign = TextAlign.Center)
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                Spacer(Modifier.height(24.dp))

                // ── Header (Minimal) ──
                Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).clickable {}.background(TranzoColors.ClayBackgroundAlt), contentAlignment = Alignment.Center) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                    }
                    Text("Activity", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    Box(modifier = Modifier.size(40.dp))
                }

                Spacer(Modifier.height(32.dp))

                if (uiState.transactions.isEmpty()) {
                    Box(Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(Modifier.size(64.dp).clip(CircleShape).background(TranzoColors.ClayBackgroundAlt), contentAlignment = Alignment.Center) {
                                Icon(Icons.Outlined.ReceiptLong, null, tint = TranzoColors.TextSecondary, modifier = Modifier.size(32.dp))
                            }
                            Text("No activity yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary)
                            Text("Your transactions will appear here", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(bottom = 100.dp)) {
                        itemsIndexed(uiState.transactions) { index, tx ->
                            val isSent = "Sent" in (tx.type ?: "")
                            val isSwap = "Swap" in (tx.type ?: "")
                            val icon = when {
                                isSent -> Icons.Outlined.ArrowUpward
                                isSwap -> Icons.Outlined.SwapVert
                                else -> Icons.Outlined.ArrowDownward
                            }

                            Column {
                                Row(Modifier.fillMaxWidth().clickable {}.padding(horizontal = 24.dp, vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        Box(Modifier.size(48.dp).clip(CircleShape).background(TranzoColors.ClayBackgroundAlt), contentAlignment = Alignment.Center) {
                                            Icon(icon, null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                                        }
                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text(tx.type ?: "Transaction", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary)
                                            Text(
                                                java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.US).format(java.util.Date(tx.createdAt * 1000)),
                                                style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary
                                            )
                                        }
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(if (isSent) "-" else "+", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = if (isSent) TranzoColors.TextPrimary else TranzoColors.ClayGreen)
                                        Spacer(Modifier.height(4.dp))
                                        Box(Modifier.clip(RoundedCornerShape(4.dp)).background(if (isSent) TranzoColors.ClayBackgroundAlt else TranzoColors.ClayGreenSoft).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                            Text(if (isSent) "Completed" else "Received", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, fontSize = 10.sp, color = if (isSent) TranzoColors.TextSecondary else TranzoColors.ClayGreen)
                                        }
                                    }
                                }
                                if (index < uiState.transactions.size - 1) {
                                    HorizontalDivider(color = TranzoColors.ClayBackgroundAlt, modifier = Modifier.padding(horizontal = 24.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

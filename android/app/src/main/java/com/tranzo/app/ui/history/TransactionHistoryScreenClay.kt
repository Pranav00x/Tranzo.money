package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

    Box(Modifier.fillMaxSize().background(TranzoColors.ClayBackground)) {
        if (uiState.isLoading && uiState.transactions.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.ClayBlue)
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

                // ── Header ──
                Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Activity", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = TranzoColors.TextPrimary)
                        Text("Your transaction history", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                    }
                    ClayIconPill(color = TranzoColors.ClayBlue, size = 48.dp, cornerRadius = 16.dp) {
                        Icon(Icons.Outlined.Receipt, null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(Modifier.height(32.dp))

                Text("ALL TRANSACTIONS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextTertiary, letterSpacing = 1.5.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp))

                if (uiState.transactions.isEmpty()) {
                    ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                        Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            ClayIconPill(color = TranzoColors.ClayBackgroundAlt, size = 56.dp, cornerRadius = 18.dp) {
                                Icon(Icons.Outlined.ReceiptLong, null, tint = TranzoColors.TextSecondary, modifier = Modifier.size(28.dp))
                            }
                            Text("No transactions yet", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                            Text("Your activity will appear here", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary)
                        }
                    }
                } else {
                    ClayCard(Modifier.fillMaxWidth().padding(horizontal = 24.dp), cornerRadius = 22.dp) {
                        LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(bottom = 8.dp)) {
                            itemsIndexed(uiState.transactions) { index, tx ->
                                val isSent = "Sent" in (tx.type ?: "")
                                val iconColor = if (isSent) TranzoColors.ClayCoral else TranzoColors.ClayGreen
                                val icon = if (isSent) Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward

                                Column {
                                    Row(Modifier.fillMaxWidth().clickable {}.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                            ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
                                                Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
                                            }
                                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                                Text(tx.type ?: "Transaction", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                                                Text(
                                                    java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.US).format(java.util.Date(tx.createdAt * 1000)),
                                                    style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary
                                                )
                                            }
                                        }
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(if (isSent) "-" else "+", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = if (isSent) TranzoColors.TextPrimary else TranzoColors.ClayGreen)
                                            Spacer(Modifier.height(4.dp))
                                            Box(Modifier.clip(RoundedCornerShape(6.dp)).background(if (isSent) TranzoColors.ClayBackgroundAlt else TranzoColors.ClayGreenSoft).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                                Text(if (isSent) "Completed" else "Received", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, fontSize = 9.sp, color = if (isSent) TranzoColors.TextSecondary else TranzoColors.ClayGreen)
                                            }
                                        }
                                    }
                                    if (index < uiState.transactions.size - 1) {
                                        HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(100.dp))
            }
        }
    }
}

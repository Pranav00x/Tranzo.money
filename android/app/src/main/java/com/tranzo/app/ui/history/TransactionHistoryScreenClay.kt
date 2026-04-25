package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CallReceived
import androidx.compose.material.icons.outlined.Send
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
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Transaction History — Baby blue bg, white transaction cards, solid icons.
 */
@Composable
fun TransactionHistoryScreenClay(
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        if (uiState.isLoading && uiState.transactions.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.ClayBlue)
            }
        } else if (uiState.error != null && uiState.transactions.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Error loading history", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Spacer(Modifier.height(8.dp))
                Text(uiState.error ?: "", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, textAlign = TextAlign.Center)
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                ) {
                    Text(
                        "History",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Your recent transactions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 100.dp),
                ) {
                    items(uiState.transactions) { tx ->
                        val isSent = "Sent" in (tx.type ?: "")
                        ClayCard(
                            modifier = Modifier.fillMaxWidth().height(72.dp),
                            shadowElevation = 6.dp,
                            cornerRadius = 20.dp,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (isSent) TranzoColors.ClayBlue else TranzoColors.ClayGreen),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Icon(
                                            if (isSent) Icons.Outlined.Send else Icons.Outlined.CallReceived,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp),
                                        )
                                    }
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            tx.type ?: "Transaction",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = TranzoColors.TextPrimary,
                                        )
                                        Text(
                                            java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.US)
                                                .format(java.util.Date(tx.createdAt * 1000)),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TranzoColors.TextTertiary,
                                        )
                                    }
                                }
                                Text(
                                    if (isSent) "-" else "+",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSent) TranzoColors.Error else TranzoColors.ClayGreen,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

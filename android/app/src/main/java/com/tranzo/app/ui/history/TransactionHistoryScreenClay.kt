package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CallReceived
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Transaction History Screen
 */
@Composable
fun TransactionHistoryScreenClay(
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = TranzoColors.ClayBackground
            )
    ) {
        if (uiState.isLoading && uiState.transactions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TranzoColors.PrimaryBlue)
            }
        } else if (uiState.error != null && uiState.transactions.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Error loading history",
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
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "History",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                        fontSize = 28.sp
                    )
                    Text(
                        "Your recent transactions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary
                    )
                }

                // Transaction list from API
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    items(uiState.transactions) { tx ->
                        TransactionItemClay(
                            description = tx.type ?: "Transaction",
                            time = java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.US)
                                .format(java.util.Date(tx.createdAt * 1000)),
                            type = if ("Sent" in (tx.type ?: "")) "sent" else "received"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItemClay(
    description: String,
    time: String,
    type: String,
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
                // Icon box
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .background(
                            color = if (type == "sent")
                                TranzoColors.PrimaryBlue.copy(alpha = 0.12f)
                            else
                                TranzoColors.PrimaryGreen.copy(alpha = 0.12f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (type == "sent")
                            Icons.Outlined.Send else Icons.Outlined.CallReceived,
                        contentDescription = type,
                        tint = if (type == "sent")
                            TranzoColors.PrimaryBlue else TranzoColors.PrimaryGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Text info
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        description,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary
                    )
                    Text(
                        time,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }
            }

            // Status
            Text(
                if ("Sent" in description) "-" else "+",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = if (type == "sent") TranzoColors.Error else TranzoColors.Success
            )
        }
    }
}



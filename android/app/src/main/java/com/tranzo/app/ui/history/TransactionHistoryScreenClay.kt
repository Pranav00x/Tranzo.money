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
    val transactions = listOf(
        "Sent $500 USDC" to "2 hours ago",
        "Received $1200 USDC" to "5 hours ago",
        "Swapped 2 ETH for $3400 USDC" to "1 day ago",
        "Sent $250 USDC" to "2 days ago",
        "Received $800 USDC" to "3 days ago",
        "Card Purchase $45.99" to "3 days ago",
        "Sent $1000 USDC" to "4 days ago",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
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

            // Transaction list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(transactions) { (description, time) ->
                    TransactionItemClay(
                        description = description,
                        time = time,
                        type = if ("Sent" in description) "sent" else "received"
                    )
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

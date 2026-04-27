package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TransactionHistoryScreenModern(
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Activity",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        } else if (state.transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    "No transactions yet",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
            ) {
                item {
                    Text(
                        "Recent Transactions",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(state.transactions.size) { index ->
                    val tx = state.transactions[index]
                    val icon = if (tx.type == "sent") Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward
                    val label = "${tx.type?.capitalize() ?: "Transaction"} - ${tx.status?.capitalize() ?: "Pending"}"
                    val timestamp = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault()).format(java.util.Date(tx.createdAt))
                    TransactionCard(
                        icon = icon,
                        label = label,
                        amount = tx.transactionHash?.take(8) ?: "---",
                        timestamp = timestamp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun TransactionCard(
    icon: ImageVector,
    label: String,
    amount: String,
    timestamp: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        icon,
                        contentDescription = label,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        label,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Text(
                        timestamp,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                    )
                }
            }

            Text(
                amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
            )
        }
    }
}

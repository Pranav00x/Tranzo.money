package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors

data class TransactionUiItem(
    val type: String,          // "sent", "received", "swap", "dripper"
    val title: String,
    val subtitle: String,
    val amount: String,
    val isPositive: Boolean,
    val timestamp: String,
    val status: String,        // "confirmed", "pending", "failed"
)

/**
 * Transaction history screen — CheQ-style list.
 *
 * Layout:
 * - "Activity" title
 * - Filter tabs (All, Sent, Received, Swap, Dripper)
 * - Grouped transaction list by date
 */
@Composable
fun TransactionHistoryScreen(
    onBack: () -> Unit = {},
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Sent", "Received", "Swap", "Dripper")

    // Mock data
    val transactions = listOf(
        TransactionUiItem("sent", "Sent USDC", "To 0x7a3b...f4c2", "-100.00 USDC", false, "2 min ago", "confirmed"),
        TransactionUiItem("received", "Received USDC", "From 0xd2e1...a8b3", "+250.00 USDC", true, "1 hour ago", "confirmed"),
        TransactionUiItem("swap", "Swapped USDC → POL", "100 USDC → 245 POL", "245.00 POL", true, "3 hours ago", "confirmed"),
        TransactionUiItem("dripper", "Salary Withdrawal", "Stream #12", "+1,200.00 USDC", true, "Yesterday", "confirmed"),
        TransactionUiItem("sent", "Sent WETH", "To 0x4b2c...e9d1", "-0.05 WETH", false, "2 days ago", "confirmed"),
        TransactionUiItem("received", "Received POL", "From 0xa1b2...c3d4", "+50.00 POL", true, "3 days ago", "pending"),
    )

    val filteredTx = if (selectedFilter == "All") transactions
    else transactions.filter {
        it.type.equals(selectedFilter, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        // ── Title ────────────────────────────────────────────────
        Text(
            text = "Activity",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        )

        // ── Filter Tabs ──────────────────────────────────────────
        ScrollableTabRow(
            selectedTabIndex = filters.indexOf(selectedFilter),
            containerColor = TranzoColors.Background,
            contentColor = TranzoColors.PrimaryGreen,
            edgePadding = 24.dp,
            divider = {},
            indicator = {},
        ) {
            filters.forEach { filter ->
                val isSelected = selectedFilter == filter
                Tab(
                    selected = isSelected,
                    onClick = { selectedFilter = filter },
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) TranzoColors.PrimaryGreen else TranzoColors.LightGray,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) TranzoColors.TextOnGreen else TranzoColors.TextSecondary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Transaction List ─────────────────────────────────────
        if (filteredTx.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.Receipt,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = TranzoColors.TextTertiary,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No transactions yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 100.dp),
            ) {
                items(filteredTx) { tx ->
                    TransactionRow(tx)
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(tx: TransactionUiItem) {
    val icon: ImageVector = when (tx.type) {
        "sent" -> Icons.Outlined.ArrowOutward
        "received" -> Icons.Outlined.ArrowDownward
        "swap" -> Icons.Outlined.SwapHoriz
        "dripper" -> Icons.Outlined.WaterDrop
        else -> Icons.Outlined.Receipt
    }

    val iconColor = when (tx.type) {
        "sent" -> TranzoColors.Error
        "received" -> TranzoColors.PrimaryGreen
        "swap" -> TranzoColors.Info
        "dripper" -> TranzoColors.LightTeal
        else -> TranzoColors.TextSecondary
    }

    val iconBg = when (tx.type) {
        "sent" -> TranzoColors.ErrorLight
        "received" -> TranzoColors.PaleTeal
        "swap" -> TranzoColors.Info.copy(alpha = 0.1f)
        "dripper" -> TranzoColors.PaleTeal
        else -> TranzoColors.LightGray
    }

    Surface(
        onClick = {},
        color = TranzoColors.Background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Title + subtitle
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = tx.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
                    if (tx.status == "pending") {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = TranzoColors.WarningLight,
                        ) {
                            Text(
                                text = "Pending",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.Warning,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            )
                        }
                    }
                }
                Text(
                    text = tx.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }

            // Amount + time
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = tx.amount,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (tx.isPositive) TranzoColors.PrimaryGreen else TranzoColors.TextPrimary,
                )
                Text(
                    text = tx.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )
            }
        }
    }
}

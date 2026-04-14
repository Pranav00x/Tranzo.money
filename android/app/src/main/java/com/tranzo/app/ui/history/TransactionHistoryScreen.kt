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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tranzo.app.ui.theme.TranzoColors

/**
 * A single transaction row model.
 *
 * [dateGroup] — "Today", "Yesterday", "April 2026", etc.
 * [type]      — "sent" | "received" | "swap" | "dripper" | "card"
 * [status]    — "confirmed" | "pending" | "failed"
 */
data class TransactionUiItem(
    val id: String,
    val type: String,
    val title: String,
    val subtitle: String,
    val amount: String,
    val isPositive: Boolean,
    val timestamp: String,
    val status: String,
    val dateGroup: String,
)

// ── List item sealed type for LazyColumn ────────────────────────────────────

private sealed interface ListItem {
    data class Header(val label: String) : ListItem
    data class Row(val tx: TransactionUiItem) : ListItem
}

/**
 * Transaction history screen — CheQ-style.
 *
 * Layout:
 * - "Activity" headline
 * - Filter pill tabs: All / Sent / Received / Swap / Dripper / Card
 * - Pull-to-refresh LazyColumn
 * - Date group headers ("Today", "Yesterday", "March 2026")
 * - Transaction rows with icon, description, amount, status badge
 * - Empty state with illustration when no transactions match filter
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    onBack: () -> Unit = {},
    // When wired to HistoryViewModel, pass isLoading + transactions from state
    isLoading: Boolean = false,
    onRefresh: () -> Unit = {},
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Sent", "Received", "Swap", "Dripper", "Card")

    // Static demo data — replace with ViewModel state in production
    val transactions = remember { sampleTransactions() }

    val filteredTx = remember(selectedFilter, transactions) {
        if (selectedFilter == "All") transactions
        else transactions.filter {
            it.type.equals(selectedFilter, ignoreCase = true)
        }
    }

    // Build flat list with group header separators
    val listItems = remember(filteredTx) {
        val result = mutableListOf<ListItem>()
        var lastGroup = ""
        filteredTx.forEach { tx ->
            if (tx.dateGroup != lastGroup) {
                result.add(ListItem.Header(tx.dateGroup))
                lastGroup = tx.dateGroup
            }
            result.add(ListItem.Row(tx))
        }
        result.toList()
    }

    val pullState = rememberPullToRefreshState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background)
            .systemBarsPadding(),
    ) {
        // ── Title ────────────────────────────────────────────────────
        Text(
            text = "Activity",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        )

        // ── Filter Tabs ──────────────────────────────────────────────
        ScrollableTabRow(
            selectedTabIndex = filters.indexOf(selectedFilter).coerceAtLeast(0),
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

        // ── Pull-to-Refresh + List ───────────────────────────────────
        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = onRefresh,
            state = pullState,
            modifier = Modifier.weight(1f),
        ) {
            if (filteredTx.isEmpty()) {
                EmptyState(filterName = selectedFilter)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp),
                ) {
                    items(
                        items = listItems,
                        key = { item ->
                            when (item) {
                                is ListItem.Header -> "header_${item.label}"
                                is ListItem.Row    -> item.tx.id
                            }
                        },
                    ) { item ->
                        when (item) {
                            is ListItem.Header -> DateGroupHeader(label = item.label)
                            is ListItem.Row    -> TransactionRow(tx = item.tx)
                        }
                    }
                }
            }
        }
    }
}

// ── Date group header ────────────────────────────────────────────────────────

@Composable
private fun DateGroupHeader(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = TranzoColors.TextTertiary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .padding(top = 8.dp),
    )
}

// ── Transaction row ──────────────────────────────────────────────────────────

@Composable
private fun TransactionRow(tx: TransactionUiItem) {
    val icon: ImageVector = when (tx.type) {
        "sent"     -> Icons.Outlined.ArrowOutward
        "received" -> Icons.Outlined.ArrowDownward
        "swap"     -> Icons.Outlined.SwapHoriz
        "dripper"  -> Icons.Outlined.WaterDrop
        "card"     -> Icons.Outlined.CreditCard
        else       -> Icons.Outlined.Receipt
    }

    val iconColor = when (tx.type) {
        "sent"     -> TranzoColors.Error
        "received" -> TranzoColors.PrimaryGreen
        "swap"     -> TranzoColors.Info
        "dripper"  -> TranzoColors.LightTeal
        "card"     -> TranzoColors.TextSecondary
        else       -> TranzoColors.TextSecondary
    }

    val iconBg = when (tx.type) {
        "sent"     -> TranzoColors.ErrorLight
        "received" -> TranzoColors.PaleTeal
        "swap"     -> TranzoColors.Info.copy(alpha = 0.10f)
        "dripper"  -> TranzoColors.PaleTeal
        "card"     -> TranzoColors.LightGray
        else       -> TranzoColors.LightGray
    }

    Surface(
        onClick = { /* navigate to detail */ },
        color = TranzoColors.Background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(46.dp)
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
                        color = TranzoColors.TextPrimary,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    when (tx.status) {
                        "pending" -> StatusPill(
                            text = "Pending",
                            containerColor = TranzoColors.WarningLight,
                            textColor = TranzoColors.Warning,
                        )
                        "failed"  -> StatusPill(
                            text = "Failed",
                            containerColor = TranzoColors.ErrorLight,
                            textColor = TranzoColors.Error,
                        )
                        else      -> Unit
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = tx.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Amount + timestamp
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = tx.amount,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (tx.isPositive) TranzoColors.PrimaryGreen else TranzoColors.TextPrimary,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = tx.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.TextTertiary,
                )
            }
        }
    }
}

// ── Status pill badge ────────────────────────────────────────────────────────

@Composable
private fun StatusPill(
    text: String,
    containerColor: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color,
) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = containerColor,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}

// ── Empty state ──────────────────────────────────────────────────────────────

@Composable
private fun EmptyState(filterName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Illustration placeholder — green circle with receipt icon
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(TranzoColors.PaleTeal),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Receipt,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = TranzoColors.PrimaryGreen,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No transactions yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = TranzoColors.TextPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val message = if (filterName == "All") {
            "Your transaction history will appear here once you start sending or receiving crypto."
        } else {
            "No \"$filterName\" transactions found."
        }
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

// ── Sample data ──────────────────────────────────────────────────────────────

private fun sampleTransactions(): List<TransactionUiItem> = listOf(
    TransactionUiItem(
        id = "tx_1",
        type = "sent",
        title = "Sent USDC",
        subtitle = "To 0x7a3b...f4c2",
        amount = "-100.00 USDC",
        isPositive = false,
        timestamp = "2:14 PM",
        status = "confirmed",
        dateGroup = "Today",
    ),
    TransactionUiItem(
        id = "tx_2",
        type = "received",
        title = "Received USDC",
        subtitle = "From 0xd2e1...a8b3",
        amount = "+250.00 USDC",
        isPositive = true,
        timestamp = "10:05 AM",
        status = "confirmed",
        dateGroup = "Today",
    ),
    TransactionUiItem(
        id = "tx_3",
        type = "swap",
        title = "Swapped",
        subtitle = "100 USDC → 245 POL",
        amount = "+245.00 POL",
        isPositive = true,
        timestamp = "8:30 AM",
        status = "pending",
        dateGroup = "Today",
    ),
    TransactionUiItem(
        id = "tx_4",
        type = "dripper",
        title = "Salary Withdrawal",
        subtitle = "Stream #12",
        amount = "+1,200.00 USDC",
        isPositive = true,
        timestamp = "9:00 AM",
        status = "confirmed",
        dateGroup = "Yesterday",
    ),
    TransactionUiItem(
        id = "tx_5",
        type = "card",
        title = "Card Purchase",
        subtitle = "Amazon.com",
        amount = "-42.99 USDC",
        isPositive = false,
        timestamp = "3:45 PM",
        status = "confirmed",
        dateGroup = "Yesterday",
    ),
    TransactionUiItem(
        id = "tx_6",
        type = "sent",
        title = "Sent WETH",
        subtitle = "To 0x4b2c...e9d1",
        amount = "-0.05 WETH",
        isPositive = false,
        timestamp = "11:22 AM",
        status = "confirmed",
        dateGroup = "April 13, 2026",
    ),
    TransactionUiItem(
        id = "tx_7",
        type = "received",
        title = "Received POL",
        subtitle = "From 0xa1b2...c3d4",
        amount = "+50.00 POL",
        isPositive = true,
        timestamp = "6:10 PM",
        status = "failed",
        dateGroup = "April 13, 2026",
    ),
    TransactionUiItem(
        id = "tx_8",
        type = "swap",
        title = "Swapped",
        subtitle = "50 POL → 20.50 USDC",
        amount = "+20.50 USDC",
        isPositive = true,
        timestamp = "2:00 PM",
        status = "confirmed",
        dateGroup = "March 2026",
    ),
)

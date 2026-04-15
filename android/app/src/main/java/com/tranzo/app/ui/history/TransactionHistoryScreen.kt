package com.tranzo.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.data.model.TransactionItem
import com.tranzo.app.ui.theme.TranzoColors
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

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

private sealed interface ListItem {
    data class Header(val label: String) : ListItem
    data class Row(val tx: TransactionUiItem) : ListItem
}

@Composable
fun TransactionHistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Sent", "Received", "Swap")
    val transactions = state.transactions.map { it.toUi() }

    val filteredTx = remember(selectedFilter, transactions) {
        if (selectedFilter == "All") {
            transactions
        } else {
            transactions.filter { it.type.equals(selectedFilter, ignoreCase = true) }
        }
    }

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
        Text(
            text = "Activity",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        )

        ScrollableTabRow(
            selectedTabIndex = filters.indexOf(selectedFilter).coerceAtLeast(0),
            containerColor = TranzoColors.Background,
            contentColor = TranzoColors.PrimaryBlack,
            edgePadding = 24.dp,
            divider = {},
            indicator = {},
        ) {
            filters.forEach { filter ->
                val isSelected = selectedFilter == filter
                Tab(selected = isSelected, onClick = { selectedFilter = filter }) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) TranzoColors.PrimaryBlack else TranzoColors.LightGray,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) TranzoColors.White else TranzoColors.TextSecondary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
            }
        }

        state.error?.let {
            Text(
                text = it,
                color = TranzoColors.Error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.refresh() },
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
                    items(listItems, key = { item ->
                        when (item) {
                            is ListItem.Header -> "header_${item.label}"
                            is ListItem.Row -> item.tx.id
                        }
                    }) { item ->
                        when (item) {
                            is ListItem.Header -> DateGroupHeader(item.label)
                            is ListItem.Row -> TransactionRow(item.tx)
                        }
                    }
                }
            }
        }
    }
}

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

@Composable
private fun TransactionRow(tx: TransactionUiItem) {
    val icon: ImageVector = when (tx.type.lowercase()) {
        "sent" -> Icons.Outlined.ArrowOutward
        "received" -> Icons.Outlined.ArrowDownward
        "swap" -> Icons.Outlined.SwapHoriz
        else -> Icons.Outlined.Receipt
    }

    Surface(color = TranzoColors.Background) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(TranzoColors.LightGray),
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.material3.Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = TranzoColors.PrimaryBlack,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tx.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = TranzoColors.TextPrimary,
                )
                Text(
                    text = tx.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = tx.amount,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary,
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

@Composable
private fun EmptyState(filterName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(TranzoColors.LightGray),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Outlined.Receipt,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = TranzoColors.TextTertiary,
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
            "Your on-chain transaction history will appear here."
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

private fun TransactionItem.toUi(): TransactionUiItem {
    val txType = when (type?.lowercase()) {
        "send", "sent", "transfer" -> "Sent"
        "receive", "received" -> "Received"
        "swap" -> "Swap"
        else -> "Other"
    }
    val instant = if (createdAt > 9_999_999_999L) {
        Instant.ofEpochMilli(createdAt)
    } else {
        Instant.ofEpochSecond(createdAt)
    }
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.US)
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
    val zoned = instant.atZone(ZoneId.systemDefault())
    val hash = transactionHash ?: id
    return TransactionUiItem(
        id = id,
        type = txType,
        title = txType,
        subtitle = hash.take(10) + "..." + hash.takeLast(6),
        amount = if (txType == "Received") "+ Crypto" else "- Crypto",
        isPositive = txType == "Received",
        timestamp = timeFormatter.format(zoned),
        status = status ?: "pending",
        dateGroup = dateFormatter.format(zoned),
    )
}

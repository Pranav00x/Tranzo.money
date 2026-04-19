package com.tranzo.app.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.data.model.TokenBalance
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Minimal monochrome home — CheQ-inspired black/white Web3 wallet.
 * All data is live from the backend via HomeViewModel.
 */
@Composable
fun HomeScreenProMax(
    viewModel: HomeViewModel = hiltViewModel(),
    onSend: () -> Unit = {},
    onReceive: () -> Unit = {},
    onSwap: () -> Unit = {},
    onDripper: () -> Unit = {},
    onCard: () -> Unit = {},
    onSettings: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Top bar ──────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar circle
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1A1A1A)),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials = state.user?.let {
                            val first = it.firstName?.firstOrNull() ?: it.email?.firstOrNull() ?: 'T'
                            first.uppercaseChar().toString()
                        } ?: "T"
                        Text(
                            initials,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            state.user?.displayName ?: state.user?.firstName ?: "Tranzo Wallet",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                        // Show truncated wallet address
                        val addr = state.user?.smartAccount ?: ""
                        if (addr.isNotEmpty()) {
                            Text(
                                "${addr.take(6)}...${addr.takeLast(4)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF999999)
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = { viewModel.refresh() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Refresh,
                            contentDescription = "Refresh",
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    IconButton(
                        onClick = onSettings,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // ── Balance card ─────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        "Total Balance",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF999999)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (state.isLoading) {
                        // Loading shimmer placeholder
                        Text(
                            "Loading...",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF666666)
                        )
                    } else {
                        Text(
                            formatBalance(state.totalUsdBalance),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = Color(0xFF333333), thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Token rows — from real API
                    if (state.balances.isNotEmpty()) {
                        state.balances.forEach { token ->
                            TokenRow(token)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    } else if (!state.isLoading) {
                        Text(
                            "No tokens found",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Quick Actions ────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionCircle(Icons.Outlined.CallMade, "Send", onClick = onSend)
                ActionCircle(Icons.Outlined.CallReceived, "Receive", onClick = onReceive)
                ActionCircle(Icons.Default.SwapVert, "Swap", onClick = onSwap)
                ActionCircle(Icons.Outlined.CreditCard, "Card", onClick = onCard)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Services Grid ────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ServiceCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.Water,
                        title = "Dripper",
                        subtitle = "Salary streaming",
                        onClick = onDripper
                    )
                    ServiceCard(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Outlined.CreditCard,
                        title = "Virtual Card",
                        subtitle = "Spend crypto",
                        onClick = onCard
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Recent Activity ──────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Recent Activity",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    "View All",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF666666)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Empty state
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF5F5F5)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8E8E8)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Receipt,
                            contentDescription = null,
                            tint = Color(0xFF999999),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No transactions yet",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF666666)
                    )
                    Text(
                        "Your on-chain activity will appear here",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF999999)
                    )
                }
            }

            // Error banner
            state.error?.let { errorMsg ->
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF0F0)
                ) {
                    Text(
                        errorMsg,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFCC0000),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // Bottom spacing for nav bar
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// ── Token row inside balance card ────────────────────────────────
@Composable
private fun TokenRow(token: TokenBalance) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF333333)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    token.symbol.take(1),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                token.symbol,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Text(
            formatTokenAmount(token.formatted, token.symbol),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFCCCCCC)
        )
    }
}

// ── Circular action button (Send, Receive, Swap, Card) ──────────
@Composable
private fun ActionCircle(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1A1A1A)
        )
    }
}

// ── Service card (Dripper, Virtual Card) ─────────────────────────
@Composable
private fun ServiceCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier
            .height(110.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF1A1A1A)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF999999)
                )
            }
        }
    }
}

// ── Formatting helpers ───────────────────────────────────────────

private fun formatBalance(usdValue: Double): String {
    return if (usdValue == 0.0) {
        "$0.00"
    } else {
        "$${String.format("%.2f", usdValue)}"
    }
}

private fun formatTokenAmount(formatted: String, symbol: String): String {
    val amount = formatted.toDoubleOrNull() ?: 0.0
    return if (amount == 0.0) {
        "0 $symbol"
    } else if (amount < 0.0001) {
        "<0.0001 $symbol"
    } else {
        "${String.format("%.4f", amount)} $symbol"
    }
}
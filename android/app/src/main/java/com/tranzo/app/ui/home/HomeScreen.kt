package com.tranzo.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.components.StatusBadge
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoCard
import com.tranzo.app.ui.theme.TranzoColors

data class QuickAction(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
)

data class TokenItem(
    val symbol: String,
    val name: String,
    val balance: String,
    val usdValue: String,
    val change: String,
    val isPositive: Boolean,
)

/**
 * Home Dashboard — CheQ-inspired.
 *
 * Layout:
 * 1. Dark navy-to-teal gradient header (~40%)
 *    - Logo + notification bell
 *    - Greeting
 *    - Total balance (large)
 *    - Quick action row (Send, Receive, Swap, Buy)
 * 2. White section with rounded top corners pulls up over gradient
 *    - Feature card grid (like CheQ's Education Fee, Utilities, etc.)
 *    - Token balances
 *    - Recent activity
 */
@Composable
fun HomeScreen(
    userName: String = "Pranav",
    totalBalance: String = "$2,450.00",
    balanceChange: String = "+$45.20 (1.88%)",
    onSend: () -> Unit = {},
    onReceive: () -> Unit = {},
    onSwap: () -> Unit = {},
    onDripper: () -> Unit = {},
) {
    val quickActions = listOf(
        QuickAction("Send", Icons.Outlined.ArrowOutward, onSend),
        QuickAction("Receive", Icons.Outlined.ArrowDownward, onReceive),
        QuickAction("Swap", Icons.Outlined.SwapHoriz, onSwap),
        QuickAction("Dripper", Icons.Outlined.WaterDrop, onDripper),
    )

    val tokens = listOf(
        TokenItem("USDC", "USD Coin", "1,234.56", "$1,234.56", "+0.01%", true),
        TokenItem("USDT", "Tether", "500.00", "$500.00", "+0.00%", true),
        TokenItem("POL", "Polygon", "245.8", "$165.44", "-2.4%", false),
        TokenItem("WETH", "Wrapped ETH", "0.15", "$550.00", "+3.2%", true),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        // ── Gradient Header ──────────────────────────────────────
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                TranzoColors.Navy,
                                TranzoColors.GradientMid,
                                TranzoColors.DarkTeal,
                            ),
                        ),
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp),
            ) {
                Column {
                    // Top bar: logo + notification
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "tranzo",
                            style = MaterialTheme.typography.headlineSmall,
                            color = TranzoColors.TextOnDark,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = TranzoColors.TextOnDark,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Greeting
                    Text(
                        text = "Hello, $userName",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TranzoColors.TextOnDarkMuted,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Total Balance — hero number
                    Text(
                        text = totalBalance,
                        style = MaterialTheme.typography.displayLarge,
                        color = TranzoColors.TextOnDark,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Balance change
                    Text(
                        text = "$balanceChange today",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.LightTeal,
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Quick Actions — 4 circular buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        quickActions.forEach { action ->
                            QuickActionButton(
                                label = action.label,
                                icon = action.icon,
                                onClick = action.onClick,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }

        // ── White Content Section (pulls up with rounded corners) ──
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(TranzoColors.Background)
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp),
            ) {
                Column {
                    // ── Feature Grid (CheQ-style) ────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Dripper card
                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onDripper,
                        ) {
                            Text(
                                text = "Salary Streaming",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Earn in ",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.TextSecondary,
                            )
                            Text(
                                text = "real-time",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.PrimaryGreen,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Icon(
                                imageVector = Icons.Outlined.WaterDrop,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(32.dp),
                            )
                        }

                        // Swap card
                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onSwap,
                        ) {
                            Text(
                                text = "Swap",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Best rates",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.PrimaryGreen,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Icon(
                                imageVector = Icons.Outlined.SwapHoriz,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(32.dp),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Your Assets ──────────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Your Assets",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        TextButton(onClick = {}) {
                            Text(
                                text = "View All",
                                color = TranzoColors.PrimaryGreen,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }

        // ── Token Balance List ────────────────────────────────────
        items(tokens) { token ->
            TokenBalanceRow(token = token)
        }

        // Bottom spacing for nav bar
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun QuickActionButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp),
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = TranzoColors.TextOnDark,
            ),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TranzoColors.TextOnDarkMuted,
        )
    }
}

@Composable
private fun TokenBalanceRow(token: TokenItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TranzoColors.CardSurface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Token icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(TranzoColors.PaleTeal),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = token.symbol.take(2),
                    style = MaterialTheme.typography.labelMedium,
                    color = TranzoColors.PrimaryGreen,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Token name + symbol
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = token.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = "${token.balance} ${token.symbol}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TranzoColors.TextSecondary,
                )
            }

            // USD value + change
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = token.usdValue,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = token.change,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (token.isPositive) TranzoColors.Success else TranzoColors.Error,
                )
            }
        }
    }
}

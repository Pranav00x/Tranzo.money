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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tranzo.app.ui.components.StatusBadge
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.components.TranzoCard
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.data.model.TokenBalance
import com.tranzo.app.ui.home.HomeViewModel
import java.util.Locale
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
 * Home Dashboard — Card-first, CheQ-inspired.
 *
 * Layout:
 * 1. Dark navy-to-teal gradient header
 *    - Tranzo logo + notification bell
 *    - Mini card visual (hero, like CheQ's credit card section)
 *    - "+ Add New Card" green pill CTA (if no card)
 * 2. White section with rounded top corners
 *    - Quick actions row (Send, Receive, Swap, Dripper)
 *    - Feature grid cards
 *    - Token balances
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSend: () -> Unit = {},
    onReceive: () -> Unit = {},
    onSwap: () -> Unit = {},
    onDripper: () -> Unit = {},
    onCard: () -> Unit = {},
    onOrderCard: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    
    val quickActions = listOf(
        QuickAction("Send", Icons.Outlined.ArrowOutward, onSend),
        QuickAction("Receive", Icons.Outlined.ArrowDownward, onReceive),
        QuickAction("Swap", Icons.Outlined.SwapHoriz, onSwap),
        QuickAction("Dripper", Icons.Outlined.WaterDrop, onDripper),
    )

    val tokens = state.balances.map { balance ->
        TokenItem(
            symbol = balance.symbol,
            name = balance.symbol, // Use symbol as name if full name not in TokenBalance
            balance = balance.formatted,
            price = "$${balance.formatted}", // Placeholder for price
            change = "0.00%", // Placeholder
            isPositive = true
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.Background),
    ) {
        // ── Gradient Header with Card Hero ───────────────────────
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
                            .padding(top = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = state.user?.displayName ?: "User",
                            style = MaterialTheme.typography.headlineSmall,
                            color = TranzoColors.TextOnDark,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = TranzoColors.TextOnDark,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ── Card Hero Section ────────────────────────
                    if (hasCard) {
                        // Mini card visual — the main product
                        MiniCardHero(onClick = onCard)
                    } else {
                        // No card — CTA to get one (like CheQ's "Pay Your Credit Card Bills")
                        NoCardHero(onOrderCard = onOrderCard)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ── Balance Section ──────────────────────────
                    Text(
                        text = "Hello, ${state.user?.displayName ?: "User"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextOnDarkMuted,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$${String.format(Locale.US, "%,.2f", state.totalUsdBalance)}",
                        style = MaterialTheme.typography.displayLarge,
                        color = TranzoColors.TextOnDark,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "+$0.00 (0.00%) today",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.LightTeal,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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

        // ── White Content Section ────────────────────────────────
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
                    // ── Feature Grid (CheQ-style) ───────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // Card feature (primary)
                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onCard,
                        ) {
                            Text(
                                text = "Crypto Card",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Spend anywhere",
                                style = MaterialTheme.typography.bodySmall,
                                color = TranzoColors.PrimaryGreen,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Icon(
                                imageVector = Icons.Outlined.CreditCard,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(32.dp),
                            )
                        }

                        // Dripper card
                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onDripper,
                        ) {
                            Text(
                                text = "Dripper",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Earn in real-time",
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
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Second row: Wallet + Swap + more
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onSwap,
                        ) {
                            Text(
                                text = "Swap",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "Best rates",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.PrimaryGreen,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.SwapHoriz,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(24.dp),
                            )
                        }

                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onSend,
                        ) {
                            Text(
                                text = "Send",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "Zero gas",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.PrimaryGreen,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.ArrowOutward,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(24.dp),
                            )
                        }

                        TranzoCard(
                            modifier = Modifier.weight(1f),
                            onClick = onReceive,
                        ) {
                            Text(
                                text = "Receive",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "QR code",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.PrimaryGreen,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.ArrowDownward,
                                contentDescription = null,
                                tint = TranzoColors.PrimaryGreen,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Your Assets ─────────────────────────────
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

        // ── Token Balance List ───────────────────────────────────
        items(tokens) { token ->
            TokenBalanceRow(token = token)
        }

        // Bottom spacing for nav bar
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

/**
 * Mini card hero — shows the user's virtual card in the gradient header.
 * Tapping opens the full card screen.
 */
@Composable
private fun MiniCardHero(onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            TranzoColors.Navy,
                            TranzoColors.GradientMid,
                            TranzoColors.DarkTeal,
                        ),
                    ),
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(20.dp),
        ) {
            // Top: TRANZO branding + contactless
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text(
                        text = "TRANZO",
                        style = MaterialTheme.typography.labelLarge,
                        color = TranzoColors.LightTeal,
                        letterSpacing = 3.sp,
                    )
                    Text(
                        text = "Self-Custody Card",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextOnDarkMuted,
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.Contactless,
                    contentDescription = null,
                    tint = TranzoColors.TextOnDarkMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(90f),
                )
            }

            // Bottom: card number + VISA
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = "**** **** **** 4291",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    letterSpacing = 2.sp,
                )
                Text(
                    text = "VISA",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

/**
 * No card hero — prompts user to get their Tranzo card.
 * Like CheQ's "Pay Your Credit Card Bills" hero section.
 */
@Composable
private fun NoCardHero(onOrderCard: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.08f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Outlined.CreditCard,
                contentDescription = null,
                tint = TranzoColors.LightTeal,
                modifier = Modifier.size(48.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Spend Crypto Anywhere",
                style = MaterialTheme.typography.bodyLarge,
                color = TranzoColors.TextOnDark,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Get a Tranzo Visa card — self-custody spending",
                style = MaterialTheme.typography.bodySmall,
                color = TranzoColors.TextOnDarkMuted,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onOrderCard,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TranzoColors.White,
                    contentColor = TranzoColors.PrimaryBlack,
                ),
                modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Get Your Card — Free",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
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

package com.tranzo.app.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Home Screen — Baby blue background, white puffy cards,
 * solid-color action buttons, generous spacing.
 * Matches the reference image aesthetic.
 */
@Composable
fun HomeScreenProClay(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToTransfer: () -> Unit = {},
    onNavigateToSwap: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    var showContent by remember { mutableStateOf(false) }
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { showContent = true }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        if (uiState.isLoading && uiState.user == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TranzoColors.ClayBlue)
            }
        } else if (uiState.error != null && uiState.user == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Something went wrong",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    uiState.error ?: "Unknown error",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TranzoColors.TextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                ClayButton(
                    text = "Try Again",
                    onClick = { viewModel.refresh() },
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .alpha(contentAlpha)
            ) {
                // ── Header ──────────────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                ) {
                    val userName = uiState.user?.firstName?.takeIf { it.isNotBlank() } ?: "User"
                    Text(
                        "Hello, $userName",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Manage your crypto assets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary,
                    )
                }

                // ── Balance Card ────────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // Main balance — gradient accent card
                    ClayStatCard(
                        label = "Total Balance",
                        value = "$" + String.format("%.2f", uiState.totalUsdBalance),
                        unit = "USD",
                        modifier = Modifier.height(140.dp),
                        gradientStart = TranzoColors.ClayBlue,
                        gradientEnd = Color(0xFF6C8DFF),
                    )

                    // Token balances
                    if (uiState.balances.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            uiState.balances.take(2).forEachIndexed { index, balance ->
                                ClayStatCard(
                                    label = balance.symbol ?: "Token",
                                    value = balance.formatted ?: "0.00",
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    gradientStart = if (index == 0)
                                        TranzoColors.ClayBlue else TranzoColors.PrimaryPurple,
                                    gradientEnd = if (index == 0)
                                        Color(0xFF6C8DFF) else TranzoColors.PinkLight,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Quick Actions ───────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        "Quick Actions",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                    )

                    // Action buttons — SOLID color icons like reference
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        ClayActionButton(
                            label = "Send",
                            onClick = onNavigateToTransfer,
                            backgroundColor = TranzoColors.ClayBlue,
                            icon = {
                                Icon(
                                    Icons.Outlined.Send,
                                    contentDescription = "Send",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                        )

                        ClayActionButton(
                            label = "Swap",
                            onClick = onNavigateToSwap,
                            backgroundColor = TranzoColors.ClayBlue,
                            icon = {
                                Icon(
                                    Icons.Outlined.SwapVert,
                                    contentDescription = "Swap",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                        )

                        ClayActionButton(
                            label = "Card",
                            onClick = onNavigateToCard,
                            backgroundColor = TranzoColors.ClayGreen,
                            icon = {
                                Icon(
                                    Icons.Outlined.CreditCard,
                                    contentDescription = "Card",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp),
                                )
                            },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // ── Recent Activity ─────────────────────────────
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Recent Activity",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary,
                        )
                        Text(
                            "View all",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.ClayBlue,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    if (uiState.isLoading && uiState.balances.isEmpty()) {
                        repeat(2) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White.copy(alpha = 0.5f)),
                            )
                        }
                    } else {
                        repeat(3) { index ->
                            ClayTransactionCard(
                                type = if (index % 2 == 0) "Sent" else "Received",
                                amount = if (index % 2 == 0) "-$500 USDC" else "+$1200 USDC",
                                timestamp = "2 hours ago",
                                status = "Completed",
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

/**
 * Transaction card — White puffy card with colored icon
 */
@Composable
private fun ClayTransactionCard(
    type: String,
    amount: String,
    timestamp: String,
    status: String,
) {
    ClayCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shadowElevation = 6.dp,
        cornerRadius = 20.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f),
            ) {
                // Solid colored icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (type == "Sent") TranzoColors.ClayBlue
                            else TranzoColors.ClayGreen
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (type == "Sent")
                            Icons.Outlined.Send else Icons.Outlined.CallReceived,
                        contentDescription = type,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp),
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        type,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary,
                    )
                    Text(
                        timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary,
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    amount,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (type == "Sent")
                        TranzoColors.TextPrimary else TranzoColors.ClayGreen,
                )
                Text(
                    status,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.ClayGreen,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}



package com.tranzo.app.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Home Screen - Dashboard with stats and quick actions
 * Vibrant gradients, soft shadows, premium aesthetic
 * Integrated with HomeViewModel to fetch balance and transaction data from API
 */
@Composable
fun HomeScreenProClay(
    viewModel: HomeViewModel = hiltViewModel(),
    themeManager: com.tranzo.app.util.ThemeManager = hiltViewModel(),
    onNavigateToTransfer: () -> Unit = {},
    onNavigateToSwap: () -> Unit = {},
    onNavigateToCard: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    var showContent by remember { mutableStateOf(false) }
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        showContent = true
    }

    val contentAlpha by animateFloatAsState(
        targetValue = if (showContent) 1f else 0f,
        animationSpec = tween(800),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        TranzoColors.BackgroundLight,
                        TranzoColors.SurfaceLight.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        if (uiState.isLoading && uiState.user == null) {
            // Loading state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TranzoColors.PrimaryBlue)
            }
        } else if (uiState.error != null && uiState.user == null) {
            // Error state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Error loading dashboard",
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
                Spacer(modifier = Modifier.height(24.dp))
                ClayButton(
                    text = "Retry",
                    onClick = { viewModel.refresh() },
                    gradientStart = TranzoColors.PrimaryBlue,
                    gradientEnd = TranzoColors.BlueLight
                )
            }
        } else {
            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .alpha(contentAlpha)
            ) {
                // Header with greeting
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val userName = uiState.user?.firstName?.takeIf { it.isNotBlank() } ?: "User"
                    Text(
                        "Welcome back, $userName",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                        fontSize = 28.sp
                    )
                    Text(
                        "Manage your crypto assets with ease",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TranzoColors.TextSecondary
                    )
                }

                // Balance Cards - Claymorphism gradients
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Your Assets",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextTertiary
                    )

                    // Main balance card with actual data
                    ClayStatCard(
                        label = "Total Balance",
                        value = "$" + String.format("%.2f", uiState.totalUsdBalance),
                        unit = "USD",
                        modifier = Modifier.height(140.dp),
                        gradientStart = TranzoColors.PrimaryBlue,
                        gradientEnd = TranzoColors.PrimaryPurple,
                    )

                    // Display individual balances from API
                    if (uiState.balances.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            uiState.balances.take(2).forEachIndexed { index, balance ->
                                ClayStatCard(
                                    label = balance.symbol ?: "Token $index",
                                    value = balance.formatted ?: "0.00",
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(100.dp),
                                    gradientStart = if (index == 0) TranzoColors.PrimaryBlue else TranzoColors.PrimaryPurple,
                                    gradientEnd = if (index == 0) TranzoColors.BlueLight else TranzoColors.PinkLight,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

            // Quick Actions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Quick Actions",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextTertiary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ClayActionButton(
                        label = "Send",
                        onClick = onNavigateToTransfer,
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                Icons.Outlined.Send,
                                contentDescription = "Send",
                                tint = TranzoColors.PrimaryBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        backgroundColor = TranzoColors.PrimaryBlue.copy(alpha = 0.12f)
                    )

                    ClayActionButton(
                        label = "Swap",
                        onClick = onNavigateToSwap,
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                Icons.Outlined.SwapVert,
                                contentDescription = "Swap",
                                tint = TranzoColors.PrimaryPurple,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        backgroundColor = TranzoColors.PrimaryPurple.copy(alpha = 0.12f)
                    )

                    ClayActionButton(
                        label = "Card",
                        onClick = onNavigateToCard,
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                Icons.Outlined.CreditCard,
                                contentDescription = "Card",
                                tint = TranzoColors.PrimaryPink,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        backgroundColor = TranzoColors.PrimaryPink.copy(alpha = 0.12f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Recent Transactions - Will be populated from API when added to HomeViewModel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Recent Activity",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextTertiary
                    )
                    Text(
                        "View all",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.PrimaryBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Show loading or transactions
                if (uiState.isLoading && uiState.balances.isEmpty()) {
                    // Loading state
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(TranzoColors.SurfaceLight)
                        )
                    }
                } else {
                    // Display sample transactions for now
                    // TODO: Add transactions list to HomeViewModel and display real data
                    repeat(3) { index ->
                        ClayTransactionCard(
                            type = if (index % 2 == 0) "Sent" else "Received",
                            amount = if (index % 2 == 0) "-$500 USDC" else "+$1200 USDC",
                            timestamp = "2 hours ago",
                            status = "Completed"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

}

/**
 * Clay Transaction Card - Shows transaction details
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
            .height(80.dp),
        backgroundGradient = listOf(
            TranzoColors.White,
            TranzoColors.BackgroundLight
        )
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
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            color = if (type == "Sent")
                                TranzoColors.PrimaryBlue.copy(alpha = 0.12f)
                            else
                                TranzoColors.PrimaryGreen.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = if (type == "Sent")
                            Icons.Outlined.Send else Icons.Outlined.CallReceived,
                        contentDescription = type,
                        tint = if (type == "Sent")
                            TranzoColors.PrimaryBlue else TranzoColors.PrimaryGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Text info
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        type,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary
                    )
                    Text(
                        timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }
            }

            // Amount
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    amount,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (type == "Sent")
                        TranzoColors.TextPrimary else TranzoColors.PrimaryGreen
                )
                Text(
                    status,
                    style = MaterialTheme.typography.labelSmall,
                    color = TranzoColors.PrimaryGreen,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


package com.tranzo.app.ui.home

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors
import kotlinx.coroutines.launch

/**
 * Ultra-bold home screen - Maximum visual impact and interactivity
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
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshRotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "refresh rotation"
    )

    val coroutineScope = rememberCoroutineScope()
    var showBalanceDetails by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header with settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Tranzo Wallet",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = TranzoColors.TextPrimary
                    )
                    Text(
                        "Ready to transact",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }

                IconButton(
                    onClick = onSettings,
                    modifier = Modifier
                        .background(
                            TranzoColors.SurfaceLight,
                            shape = CircleShape
                        )
                        .size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = TranzoColors.TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Premium balance card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
                    .clickable { showBalanceDetails = !showBalanceDetails }
                    .animateContentSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TranzoColors.PrimaryBlue
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TranzoColors.PrimaryBlue)
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Top row with label and refresh
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    "Total Balance",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    "$4,250.50",
                                    style = MaterialTheme.typography.displayLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }

                            IconButton(
                                onClick = {
                                    isRefreshing = true
                                    coroutineScope.launch {
                                        kotlinx.coroutines.delay(1500)
                                        isRefreshing = false
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        Color.White.copy(alpha = 0.2f),
                                        shape = CircleShape
                                    ),
                                enabled = !isRefreshing
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Refresh,
                                    contentDescription = "Refresh",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .rotate(refreshRotation)
                                )
                            }
                        }

                        // Divider
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        )

                        // Balance details (expanded)
                        if (showBalanceDetails) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                BalanceDetailRow("USDC", "$2,500.00")
                                BalanceDetailRow("ETH", "$1,750.50")
                            }
                        } else {
                            // Compact view
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(24.dp)
                            ) {
                                BalanceDetailCompact("USDC", "$2,500")
                                BalanceDetailCompact("ETH", "$1,750")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quick actions header
            Text(
                "Quick Actions",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action grid - 2 columns
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButtonMax(
                        icon = Icons.Outlined.CallMade,
                        label = "Send",
                        onClick = onSend,
                        modifier = Modifier.weight(1f),
                        accentColor = TranzoColors.PrimaryBlue
                    )
                    QuickActionButtonMax(
                        icon = Icons.Outlined.CallReceived,
                        label = "Receive",
                        onClick = onReceive,
                        modifier = Modifier.weight(1f),
                        accentColor = TranzoColors.PrimaryGreen
                    )
                }

                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButtonMax(
                        icon = Icons.Default.SwapVert,
                        label = "Swap",
                        onClick = onSwap,
                        modifier = Modifier.weight(1f),
                        accentColor = TranzoColors.PrimaryPurple
                    )
                    QuickActionButtonMax(
                        icon = Icons.Outlined.Water,
                        label = "Dripper",
                        onClick = onDripper,
                        modifier = Modifier.weight(1f),
                        accentColor = TranzoColors.AccentCyan
                    )
                }

                // Row 3
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionButtonMax(
                        icon = Icons.Outlined.CreditCard,
                        label = "Card",
                        onClick = onCard,
                        modifier = Modifier.weight(1f),
                        accentColor = TranzoColors.PrimaryOrange
                    )
                    Box(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Recent activity section
            Text(
                "Recent Activity",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Empty state for recent activity
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TranzoColors.SurfaceLight
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                TranzoColors.TextTertiary.copy(alpha = 0.1f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = null,
                            tint = TranzoColors.TextTertiary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        "No activity yet",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextSecondary
                    )
                    Text(
                        "Your transactions will appear here",
                        style = MaterialTheme.typography.labelSmall,
                        color = TranzoColors.TextTertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun BalanceDetailRow(
    label: String,
    amount: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight.SemiBold
        )
        Text(
            amount,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BalanceDetailCompact(
    label: String,
    amount: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            amount,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun QuickActionButtonMax(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = TranzoColors.PrimaryBlue,
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f)
    )

    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(
                onClickLabel = label,
                onClick = {
                    isPressed = true
                    onClick()
                },
                role = null
            )
            .graphicsLayer(scaleX = scale, scaleY = scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            accentColor.copy(alpha = 0.12f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    label,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary
                )
            }
        }
    }
}
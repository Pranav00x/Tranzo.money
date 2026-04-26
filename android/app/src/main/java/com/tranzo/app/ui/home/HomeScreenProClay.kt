package com.tranzo.app.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

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
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "content fade in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.ClayBackground)
    ) {
        if (uiState.isLoading && uiState.user == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.ClayBlue)
            }
        } else if (uiState.error != null && uiState.user == null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Error", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(uiState.error ?: "Unknown error", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                Spacer(modifier = Modifier.height(24.dp))
                ClayButton(text = "Try Again", onClick = { viewModel.refresh() }, modifier = Modifier.width(200.dp))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .alpha(contentAlpha)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // ── Header ──
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        val userName = uiState.user?.firstName?.takeIf { it.isNotBlank() } ?: "User"
                        Text(
                            "Hello, $userName",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = TranzoColors.TextPrimary,
                        )
                        Text(
                            "Welcome back",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TranzoColors.TextSecondary,
                        )
                    }

                    ClayIconPill(color = TranzoColors.ClayBlue, size = 48.dp, cornerRadius = 16.dp) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ── Balance Card (Clean White Style like Settings) ──
                ClayCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    cornerRadius = 22.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                ClayIconPill(color = TranzoColors.ClayPurple, size = 32.dp, cornerRadius = 10.dp) {
                                    Icon(Icons.Outlined.AccountBalanceWallet, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                                Text(
                                    "Total Balance",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TranzoColors.TextSecondary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(TranzoColors.ClayGreenSoft)
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                            ) {
                                Text("Smart Wallet", style = MaterialTheme.typography.labelSmall, color = TranzoColors.ClayGreen, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("$", style = MaterialTheme.typography.headlineMedium, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))
                            Text(
                                String.format("%.2f", uiState.totalUsdBalance),
                                style = MaterialTheme.typography.displayMedium,
                                color = TranzoColors.TextPrimary,
                                fontWeight = FontWeight.ExtraBold,
                            )
                            Text("USD", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ── Quick Actions (Structured like Settings Items) ──
                Text(
                    "QUICK ACTIONS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextTertiary,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                
                ClayCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    cornerRadius = 22.dp
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ActionItemClay(Icons.Outlined.ArrowUpward, "Send Crypto", "Transfer to any wallet", onNavigateToTransfer, TranzoColors.ClayBlue)
                        HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                        ActionItemClay(Icons.Outlined.SwapVert, "Swap Tokens", "Exchange assets instantly", onNavigateToSwap, TranzoColors.ClayPurple)
                        HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                        ActionItemClay(Icons.Outlined.CreditCard, "Virtual Card", "Manage your debit card", onNavigateToCard, TranzoColors.ClayGreen, showDivider = false)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ── Recent Activity (Clean List like Settings) ──
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "RECENT ACTIVITY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextTertiary,
                        letterSpacing = 1.5.sp,
                    )
                    Text(
                        "View All",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.ClayBlue,
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                ClayCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    cornerRadius = 22.dp
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        val txData = listOf(
                            Triple("Sent USDC", "-$500", "2 hours ago"),
                            Triple("Received ETH", "+$1,200", "5 hours ago"),
                            Triple("Swapped to Base", "Completed", "Yesterday"),
                        )
                        
                        txData.forEachIndexed { index, (type, amount, time) ->
                            val isSent = type.startsWith("Sent")
                            val isSwap = type.startsWith("Swap")
                            val iconColor = when {
                                isSent -> TranzoColors.ClayCoral
                                isSwap -> TranzoColors.ClayPurple
                                else -> TranzoColors.ClayGreen
                            }
                            val icon = when {
                                isSent -> Icons.Outlined.ArrowUpward
                                isSwap -> Icons.Outlined.SwapVert
                                else -> Icons.Outlined.ArrowDownward
                            }
                            
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    ClayIconPill(color = iconColor, size = 40.dp, cornerRadius = 12.dp) {
                                        Icon(icon, null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(type, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                                        Text(time, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                                    }
                                }
                                Text(
                                    amount,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSent) TranzoColors.TextPrimary else TranzoColors.ClayGreen
                                )
                            }
                            
                            if (index < txData.size - 1) {
                                HorizontalDivider(color = TranzoColors.DividerGray, modifier = Modifier.padding(horizontal = 16.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun ActionItemClay(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    iconColor: Color,
    showDivider: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            ClayIconPill(color = iconColor, size = 44.dp, cornerRadius = 15.dp) {
                Icon(icon, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
            }
        }
        Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = TranzoColors.TextTertiary, modifier = Modifier.size(18.dp))
    }
}

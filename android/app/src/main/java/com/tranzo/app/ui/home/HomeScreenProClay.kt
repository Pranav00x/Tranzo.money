package com.tranzo.app.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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

    // True minimal uses pure white backgrounds
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (uiState.isLoading && uiState.user == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TranzoColors.TextPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .alpha(contentAlpha)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // ── Header (Clean, minimal typography) ──
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val userName = uiState.user?.firstName?.takeIf { it.isNotBlank() } ?: "User"
                    Text(
                        "Good morning, $userName.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = TranzoColors.TextSecondary,
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .minimalEffect(cornerRadius = 20.dp, borderColor = TranzoColors.DividerGray)
                            .clickable { onNavigateToSettings() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Person, contentDescription = null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // ── Balance (Huge, striking minimal typography without card containers) ──
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Total Balance",
                        style = MaterialTheme.typography.labelMedium,
                        color = TranzoColors.TextSecondary,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("$", style = MaterialTheme.typography.headlineMedium, color = TranzoColors.TextTertiary, fontWeight = FontWeight.Medium)
                        Text(
                            String.format("%.2f", uiState.totalUsdBalance),
                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 56.sp),
                            color = TranzoColors.TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = (-1.5).sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(TranzoColors.ClayBackgroundAlt)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                    ) {
                        Text("Base Smart Account", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // ── Quick Actions (Minimal Grid without borders) ──
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MinimalAction(Icons.Outlined.ArrowUpward, "Send", onNavigateToTransfer)
                    MinimalAction(Icons.Outlined.ArrowDownward, "Receive", {})
                    MinimalAction(Icons.Outlined.SwapVert, "Swap", onNavigateToSwap)
                    MinimalAction(Icons.Outlined.CreditCard, "Card", onNavigateToCard)
                }

                Spacer(modifier = Modifier.height(48.dp))

                // ── Recent Activity (Direct lists, no cards) ──
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Recent",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TranzoColors.TextPrimary,
                    )
                    Text(
                        "See all",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = TranzoColors.TextSecondary,
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                val txData = listOf(
                    Triple("Sent USDC", "-$500", "Today, 10:24 AM"),
                    Triple("Received ETH", "+$1,200", "Yesterday"),
                    Triple("Swapped to Base", "Completed", "Oct 12"),
                )
                
                txData.forEachIndexed { index, (type, amount, time) ->
                    val isSent = type.startsWith("Sent")
                    val isSwap = type.startsWith("Swap")
                    
                    val icon = when {
                        isSent -> Icons.Outlined.ArrowUpward
                        isSwap -> Icons.Outlined.SwapVert
                        else -> Icons.Outlined.ArrowDownward
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable {}.padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(48.dp).clip(CircleShape).background(TranzoColors.ClayBackgroundAlt),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(icon, null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(type, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary)
                                Text(time, style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                            }
                        }
                        Text(
                            amount,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = if (isSent) TranzoColors.TextPrimary else TranzoColors.ClayGreen
                        )
                    }
                    
                    if (index < txData.size - 1) {
                        HorizontalDivider(color = TranzoColors.ClayBackgroundAlt, modifier = Modifier.padding(horizontal = 24.dp))
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun MinimalAction(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(TranzoColors.ClayBackgroundAlt)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(24.dp))
        }
        Text(label, style = MaterialTheme.typography.labelMedium, color = TranzoColors.TextSecondary, fontWeight = FontWeight.Medium)
    }
}

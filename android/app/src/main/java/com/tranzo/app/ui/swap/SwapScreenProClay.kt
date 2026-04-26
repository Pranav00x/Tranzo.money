package com.tranzo.app.ui.swap

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SwapVert
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
fun SwapScreenProClay(
    viewModel: SwapViewModel = hiltViewModel(),
    onSwapInitiated: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()
    val fromToken = uiState.fromToken
    val toToken = uiState.toToken
    val fromAmount = uiState.fromAmount

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { showContent = true }

    LaunchedEffect(uiState.isSwapped) {
        if (uiState.isSwapped) onSwapInitiated()
    }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // ── Header ──
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                ClayIconPill(
                    color = TranzoColors.ClayPurple,
                    size = 48.dp,
                    cornerRadius = 16.dp,
                ) {
                    Icon(
                        Icons.Outlined.SwapVert,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Column {
                    Text(
                        "Swap Tokens",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = TranzoColors.TextPrimary,
                    )
                    Text(
                        "Instant gasless exchange",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Swap Interface (Single Card Container) ──
            ClayCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), 
                cornerRadius = 22.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // FROM Section
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "PAY WITH",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextTertiary,
                            letterSpacing = 1.5.sp,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                ClayIconPill(color = TranzoColors.ClayBlue, size = 40.dp, cornerRadius = 12.dp) {
                                    Text(fromToken.first().toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Column {
                                    Text(fromToken, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                                    Text("Balance: $8,950", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                                }
                            }
                            
                            TextField(
                                value = fromAmount,
                                onValueChange = { viewModel.onFromAmountChanged(it) },
                                modifier = Modifier.width(120.dp),
                                placeholder = { Text("0.00", color = TranzoColors.TextDisabled, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, fontSize = 28.sp)) },
                                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = TranzoColors.TextPrimary),
                                colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
                                singleLine = true,
                            )
                        }
                    }

                    // Divider with Swap Icon
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        HorizontalDivider(color = TranzoColors.DividerGray)
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(TranzoColors.ClayBackgroundAlt),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.SwapVert, contentDescription = "Swap", tint = TranzoColors.ClayPurple, modifier = Modifier.size(24.dp))
                        }
                    }

                    // TO Section
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "RECEIVE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextTertiary,
                            letterSpacing = 1.5.sp,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                ClayIconPill(color = TranzoColors.ClayGreen, size = 40.dp, cornerRadius = 12.dp) {
                                    Text(toToken.first().toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Column {
                                    Text(toToken, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                                    Text("Balance: 1.2", style = MaterialTheme.typography.bodySmall, color = TranzoColors.TextSecondary)
                                }
                            }
                            
                            TextField(
                                value = uiState.quote?.toAmount ?: "",
                                onValueChange = {},
                                modifier = Modifier.width(120.dp),
                                placeholder = { Text("0.00", color = TranzoColors.TextDisabled, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, fontSize = 28.sp)) },
                                enabled = false,
                                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = TranzoColors.TextPrimary),
                                colors = TextFieldDefaults.colors(disabledContainerColor = Color.Transparent, disabledIndicatorColor = Color.Transparent, disabledTextColor = TranzoColors.TextPrimary),
                                singleLine = true,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Rate Info (Clean List) ──
            Text(
                "TRANSACTION DETAILS",
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
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Rate", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                        Text("1 USDC = 0.00062 ETH", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.TextPrimary)
                    }
                    HorizontalDivider(color = TranzoColors.DividerGray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Network Fee", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(TranzoColors.ClayGreenSoft)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                            ) {
                                Text("GASLESS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TranzoColors.ClayGreen, fontSize = 9.sp, letterSpacing = 0.5.sp)
                            }
                            Text("~$0.10", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = TranzoColors.ClayGreen)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ClayButton(
                text = "Review Swap",
                onClick = {
                    viewModel.executeSwap()
                    onSwapInitiated()
                },
                enabled = fromAmount.isNotBlank(),
                containerColor = TranzoColors.ClayPurple,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

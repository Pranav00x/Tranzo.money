package com.tranzo.app.ui.swap

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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

            // ── Header (Minimal) ──
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).clickable {}.background(TranzoColors.ClayBackgroundAlt),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null, tint = TranzoColors.TextPrimary, modifier = Modifier.size(20.dp))
                }
                Text(
                    "Swap",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TranzoColors.TextPrimary,
                )
                Box(modifier = Modifier.size(40.dp))
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ── Swap Interface (Raw minimal inputs, no card container) ──
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                // FROM Section
                Column {
                    Text("Pay", style = MaterialTheme.typography.labelLarge, color = TranzoColors.TextSecondary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            value = fromAmount,
                            onValueChange = { viewModel.onFromAmountChanged(it) },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("0", color = TranzoColors.TextDisabled, style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Medium)) },
                            textStyle = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary),
                            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
                            singleLine = true,
                        )
                        
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(TranzoColors.ClayBackgroundAlt).padding(horizontal = 16.dp, vertical = 10.dp),
                        ) {
                            Text(fromToken, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                        }
                    }
                    Text("Balance: $8,950", style = MaterialTheme.typography.bodyMedium, color = TranzoColors.TextSecondary, modifier = Modifier.padding(top = 8.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(color = TranzoColors.ClayBackgroundAlt)
                Spacer(modifier = Modifier.height(32.dp))

                // TO Section
                Column {
                    Text("Receive", style = MaterialTheme.typography.labelLarge, color = TranzoColors.TextSecondary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            value = uiState.quote?.toAmount ?: "",
                            onValueChange = {},
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("0", color = TranzoColors.TextDisabled, style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Medium)) },
                            enabled = false,
                            textStyle = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary),
                            colors = TextFieldDefaults.colors(disabledContainerColor = Color.Transparent, disabledIndicatorColor = Color.Transparent, disabledTextColor = TranzoColors.TextPrimary),
                            singleLine = true,
                        )
                        
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(TranzoColors.ClayBackgroundAlt).padding(horizontal = 16.dp, vertical = 10.dp),
                        ) {
                            Text(toToken, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

            // ── Rate Info (Clean text rows) ──
            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Exchange Rate", style = MaterialTheme.typography.bodyLarge, color = TranzoColors.TextSecondary)
                    Text("1 USDC = 0.00062 ETH", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = TranzoColors.TextPrimary)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Network Fee", style = MaterialTheme.typography.bodyLarge, color = TranzoColors.TextSecondary)
                    Text("Free (Gasless)", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = TranzoColors.ClayGreen)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            ClayButton(
                text = "Swap",
                onClick = {
                    viewModel.executeSwap()
                    onSwapInitiated()
                },
                enabled = fromAmount.isNotBlank(),
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

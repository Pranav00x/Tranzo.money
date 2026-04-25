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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.components.ClayTextField
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Swap Screen
 */
@Composable
fun SwapScreenProClay(
    viewModel: SwapViewModel = hiltViewModel(),
    onSwapInitiated: () -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()
    val fromToken = uiState.fromToken
    val toToken = uiState.toToken
    val fromAmount = uiState.fromAmount
    val quote = uiState.quote

    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    // Handle swap completion
    LaunchedEffect(uiState.isSwapped) {
        if (uiState.isSwapped) {
            onSwapInitiated()
        }
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
                        TranzoColors.SurfaceLight.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Text(
                "Swap Tokens",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextPrimary,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // From section
            Text(
                "From",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(
                    Color.White,
                    TranzoColors.BackgroundLight.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            fromToken,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TranzoColors.TextPrimary
                        )
                        Text(
                            "Bal: $8,950",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary
                        )
                    }

                    TextField(
                        value = fromAmount,
                        onValueChange = { amount ->
                            viewModel.onFromAmountChanged(amount)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00") },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }

            // Swap button
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(44.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                TranzoColors.PrimaryPurple,
                                TranzoColors.PrimaryPink
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.SwapVert,
                    contentDescription = "Swap",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // To section
            Text(
                "To",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextTertiary
            )

            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(
                    Color.White,
                    TranzoColors.BackgroundLight.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            toToken,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TranzoColors.TextPrimary
                        )
                        Text(
                            "Bal: 1.2",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary
                        )
                    }

                    TextField(
                        value = uiState.quote?.toAmount ?: "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00") },
                        enabled = false,
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Info
            ClayCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundGradient = listOf(
                    TranzoColors.Info.copy(alpha = 0.08f),
                    TranzoColors.BlueLight.copy(alpha = 0.05f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Rate", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("1 USDC = 0.00062 ETH", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Fee", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("~$5.00 (0.5%)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Swap button
            ClayButton(
                text = "Review Swap",
                onClick = {
                    viewModel.executeSwap()
                    onSwapInitiated()
                },
                enabled = fromAmount.isNotBlank(),
                gradientStart = TranzoColors.PrimaryPurple,
                gradientEnd = TranzoColors.PrimaryPink,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

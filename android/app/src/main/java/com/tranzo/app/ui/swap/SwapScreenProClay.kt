package com.tranzo.app.ui.swap

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.*
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Swap Screen — Rich token swap interface with 3D puffy elements.
 * Features token selector cards, animated swap button, rate info card.
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
        // Background blobs
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = TranzoColors.ClayPurple.copy(alpha = 0.06f),
                radius = 240f,
                center = Offset(size.width * 0.85f, size.height * 0.12f),
            )
            drawCircle(
                color = TranzoColors.ClayBlue.copy(alpha = 0.05f),
                radius = 200f,
                center = Offset(size.width * 0.15f, size.height * 0.5f),
            )
            drawCircle(
                color = TranzoColors.ClayGreen.copy(alpha = 0.04f),
                radius = 150f,
                center = Offset(size.width * 0.7f, size.height * 0.75f),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .alpha(contentAlpha)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                ClayIconPill(
                    color = TranzoColors.ClayPurple,
                    size = 52.dp,
                    cornerRadius = 18.dp,
                ) {
                    Icon(
                        Icons.Outlined.SwapVert,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(26.dp),
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
                        "Instant token exchange",
                        style = MaterialTheme.typography.bodySmall,
                        color = TranzoColors.TextSecondary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── FROM Card ────────────────────────────────
            Text(
                "FROM",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextTertiary,
                letterSpacing = 1.5.sp,
            )

            ClayCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ClayIconPill(
                                color = TranzoColors.ClayBlue,
                                size = 40.dp,
                                cornerRadius = 13.dp,
                            ) {
                                Text(
                                    fromToken.first().toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                )
                            }
                            Text(
                                fromToken,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(TranzoColors.ClayBlueSoft)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                "Bal: $8,950",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.ClayBlue,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                            )
                        }
                    }

                    TextField(
                        value = fromAmount,
                        onValueChange = { viewModel.onFromAmountChanged(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00", color = TranzoColors.TextDisabled) },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 32.sp,
                            color = TranzoColors.TextPrimary,
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

            // ── Swap Direction Button ────────────────────
            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                ClayIconPill(
                    color = TranzoColors.ClayBlue,
                    size = 48.dp,
                    cornerRadius = 16.dp,
                ) {
                    Icon(
                        Icons.Outlined.SwapVert,
                        contentDescription = "Swap direction",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp),
                    )
                }
            }

            // ── TO Card ──────────────────────────────────
            Text(
                "TO",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = TranzoColors.TextTertiary,
                letterSpacing = 1.5.sp,
            )

            ClayCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 22.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ClayIconPill(
                                color = TranzoColors.ClayGreen,
                                size = 40.dp,
                                cornerRadius = 13.dp,
                            ) {
                                Text(
                                    toToken.first().toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                )
                            }
                            Text(
                                toToken,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(TranzoColors.ClayGreenSoft)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                "Bal: 1.2",
                                style = MaterialTheme.typography.labelSmall,
                                color = TranzoColors.ClayGreen,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp,
                            )
                        }
                    }

                    TextField(
                        value = uiState.quote?.toAmount ?: "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00", color = TranzoColors.TextDisabled) },
                        enabled = false,
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 32.sp,
                            color = TranzoColors.TextPrimary,
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            disabledTextColor = TranzoColors.TextPrimary,
                        ),
                    )
                }
            }

            // ── Rate Info ────────────────────────────────
            ClayCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 18.dp, shadowElevation = 6.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Rate", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text(
                            "1 USDC = 0.00062 ETH",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = TranzoColors.TextPrimary,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Fee", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(TranzoColors.ClayGreenSoft)
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                            ) {
                                Text(
                                    "GASLESS",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = TranzoColors.ClayGreen,
                                    fontSize = 8.sp,
                                    letterSpacing = 0.5.sp,
                                )
                            }
                            Text(
                                "~$0.10",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.ClayGreen,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ClayButton(
                text = "Review Swap",
                onClick = {
                    viewModel.executeSwap()
                    onSwapInitiated()
                },
                enabled = fromAmount.isNotBlank(),
                containerColor = TranzoColors.ClayPurple,
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

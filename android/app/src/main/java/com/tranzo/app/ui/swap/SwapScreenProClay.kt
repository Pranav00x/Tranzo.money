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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.ClayButton
import com.tranzo.app.ui.components.ClayCard
import com.tranzo.app.ui.theme.TranzoColors

/**
 * Claymorphism Swap Screen — Baby blue bg, white token cards, swap arrow, solid blue CTA.
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
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(16.dp),
                            ambientColor = TranzoColors.PrimaryPurple.copy(alpha = 0.3f),
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(TranzoColors.PrimaryPurple),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.SwapVert,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Text(
                    "Swap Tokens",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TranzoColors.TextPrimary,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // From card
            Text(
                "From",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextSecondary,
            )

            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TranzoColors.ClayBlue),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    fromToken.first().toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                fromToken,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary,
                            )
                        }
                        Text(
                            "Bal: $8,950",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary,
                        )
                    }

                    TextField(
                        value = fromAmount,
                        onValueChange = { viewModel.onFromAmountChanged(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("0.00") },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
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

            // Swap direction button
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(44.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(14.dp),
                        ambientColor = TranzoColors.ClayBlue.copy(alpha = 0.3f),
                    )
                    .clip(RoundedCornerShape(14.dp))
                    .background(TranzoColors.ClayBlue),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Outlined.SwapVert,
                    contentDescription = "Swap direction",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }

            // To card
            Text(
                "To",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = TranzoColors.TextSecondary,
            )

            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TranzoColors.ClayGreen),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    toToken.first().toString(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                            Text(
                                toToken,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TranzoColors.TextPrimary,
                            )
                        }
                        Text(
                            "Bal: 1.2",
                            style = MaterialTheme.typography.labelSmall,
                            color = TranzoColors.TextTertiary,
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
                            fontSize = 28.sp,
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }

            // Rate info
            ClayCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Rate", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("1 USDC = 0.00062 ETH", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.TextPrimary)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Fee", style = MaterialTheme.typography.labelSmall, color = TranzoColors.TextTertiary)
                        Text("~$0.10 (gasless)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = TranzoColors.ClayGreen)
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
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

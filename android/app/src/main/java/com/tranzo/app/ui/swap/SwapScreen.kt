package com.tranzo.app.ui.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.SwapVert
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tranzo.app.ui.components.TranzoButton
import com.tranzo.app.ui.theme.TranzoColors

@Composable
fun SwapScreen(
    viewModel: SwapViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onSwapComplete: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()
    val supportedTokens = listOf("USDC", "USDT", "ETH")

    if (state.isSwapped) {
        SwapSuccessView(
            txHash = state.txHash,
            onDone = {
                viewModel.reset()
                onSwapComplete()
            },
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
            .systemBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                )
            }
            Text(
                text = "Swap",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SwapTokenCard(
                label = "From",
                token = state.fromToken,
                amount = state.fromAmount,
                isReadOnly = false,
                tokenOptions = supportedTokens,
                onAmountChange = viewModel::onFromAmountChanged,
                onTokenChanged = viewModel::onFromTokenChanged,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-8).dp),
                contentAlignment = Alignment.Center,
            ) {
                FilledIconButton(
                    onClick = { viewModel.swapDirection() },
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = TranzoColors.TextPrimary,
                        contentColor = TranzoColors.White,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SwapVert,
                        contentDescription = "Swap direction",
                    )
                }
            }

            SwapTokenCard(
                label = "To (estimated)",
                token = state.toToken,
                amount = state.quote?.toAmount ?: "0.00",
                isReadOnly = true,
                tokenOptions = supportedTokens,
                onAmountChange = {},
                onTokenChanged = viewModel::onToTokenChanged,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = TranzoColors.SurfaceLight,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    DetailRow(
                        "Exchange Rate",
                        state.quote?.rate ?: "Enter amount for quote",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailRow(
                        "Slippage",
                        state.quote?.slippageBps?.let { "${it / 100.0}%" } ?: "0.5%",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailRow("Network Fee", "Sponsored")
                    state.error?.let {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = it,
                            color = TranzoColors.Error,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }

        TranzoButton(
            text = if (state.isExecuting) "Swapping..." else "Swap",
            onClick = { viewModel.executeSwap() },
            enabled = state.quote != null && !state.isExecuting && !state.isLoadingQuote,
            isLoading = state.isExecuting || state.isLoadingQuote,
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
private fun SwapTokenCard(
    label: String,
    token: String,
    amount: String,
    isReadOnly: Boolean,
    tokenOptions: List<String>,
    onAmountChange: (String) -> Unit,
    onTokenChanged: (String) -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = TranzoColors.SurfaceLight,
        tonalElevation = 1.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = TranzoColors.TextSecondary,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = TranzoColors.SurfaceLight,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = token,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = TranzoColors.TextSecondary,
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isReadOnly) {
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TranzoColors.TextPrimary,
                    )
                } else {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { newVal ->
                            if (newVal.all { it.isDigit() || it == '.' }) {
                                onAmountChange(newVal)
                            }
                        },
                        placeholder = {
                            Text("0.00", style = MaterialTheme.typography.headlineSmall)
                        },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.fillMaxWidth(0.48f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TranzoColors.TextPrimary,
                            unfocusedBorderColor = TranzoColors.DividerGray.copy(alpha = 0f),
                        ),
                    )
                }
            }

            if (!isReadOnly) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tokenOptions.forEach { option ->
                        Surface(
                            onClick = { onTokenChanged(option) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (token == option) TranzoColors.TextPrimary else TranzoColors.SurfaceLight,
                        ) {
                            Text(
                                text = option,
                                color = if (token == option) TranzoColors.White else TranzoColors.TextSecondary,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tokenOptions.forEach { option ->
                        Surface(
                            onClick = { onTokenChanged(option) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (token == option) TranzoColors.TextPrimary else TranzoColors.SurfaceLight,
                        ) {
                            Text(
                                text = option,
                                color = if (token == option) TranzoColors.White else TranzoColors.TextSecondary,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextSecondary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TranzoColors.TextPrimary,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun SwapSuccessView(
    txHash: String?,
    onDone: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TranzoColors.BackgroundLight)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Swap Complete",
            style = MaterialTheme.typography.headlineLarge,
            color = TranzoColors.TextPrimary,
        )
        txHash?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tx: ${it.take(10)}...${it.takeLast(8)}",
                style = MaterialTheme.typography.bodySmall,
                color = TranzoColors.TextSecondary,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        TranzoButton(text = "Done", onClick = onDone)
    }
}
